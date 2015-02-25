var PORT = 24537

var fs = require('fs');
// Setup some https server options

var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var util = require('util');
var log_file = fs.createWriteStream(__dirname + '/logs/debug.log', {flags : 'w'});
var log_stdout = process.stdout;
console.log = function(d) {
	log_file.write(util.format(d) + '\n');
	log_stdout.write(util.format(d) + '\n');
};
var DATE = new Date();

// var databaseUrl = "mongodb://localhost:27017/Auctioneer"; // "username:password@example.com/mydb"
// var collections = ["users", "userSettings", "items", "auctions"]
// var db = require("mongojs").connect(databaseUrl, collections);

var partyMembers = [];

io.on('connection', function (socket) {
	console.log("new user connected");

	socket.on('update location', function(msg) {
		console.log("update location");
		partyMembers[socket.id] = msg;
	});
	socket.on('disconnect', function () {
		partyMembers[socket.id] = null;
	});

	setInterval(function () {
		io.emit("receive locations", partyMembers);
	})
});



server.listen(PORT, function(){
	console.log('listening on *:' + PORT);
});