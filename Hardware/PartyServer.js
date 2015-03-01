var PORT = 24537

var fs = require('fs');
// Setup some https server options

var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var util = require('util');
var log_file = fs.createWriteStream(__dirname + '/logs/debug.log', {flags : 'w'});
var log_stdout = process.stdout;
var date = new Date();
console.log = function(d) {
	log_file.write(date.toString() + ": " + util.format(d) + '\n');
	log_stdout.write(util.format(d) + '\n');
};
var DATE = new Date();

var openSockets = [];
var partyMembers = [];

io.on('connection', function (socket) {
	openSockets.push(socket.id);
	console.log("new user connected");

	socket.on('update location', function(msg) {
		console.log("update location");
		partyMembers[socket.id] = msg;
	});
	socket.on('disconnect', function () {
		console.log("user disconnected");
		if (socket.timer != null)
			clearInterval(socket.timer);
		partyMembers[socket.id] = null;
		for (var i = 0; i < openSockets.length; i++) {
			if (openSockets[i] == socket.id) {
				openSockets.splice(i, 1);
			}
		}
	});
	socket.on('start ride', function () {
		socket.timer = setInterval(function () {
			var locations = [];
			for (var i = 0; i < openSockets.length; i++) {
				// TODO: re-enable this check
				// if (openSockets[i] != socket.id)
					locations.push(partyMembers[openSockets[i]]);
			}
			io.emit("receive locations", locations, locations.length);
		})
	});
});



server.listen(PORT, function(){
	console.log('listening on *:' + PORT);
});