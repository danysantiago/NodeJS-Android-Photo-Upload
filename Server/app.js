var app = require('express').createServer();
var express = require('express');
var fs = require('fs');
var im = require('imagemagick');

var Db = require('mongodb').Db;
var dbServer = require('mongodb').Server;
var dbConnection = require('mongodb').Connection;

var db = new Db('test', new dbServer('localhost', dbConnection.DEFAULT_PORT, {}));
db.open(function(err, db){});

app.use(express.bodyParser())

app.get('/', function(req, res){
    res.send(
        '<form action="/upload" method="post" enctype="multipart/form-data">'+
        '<input type="file" name="source">'+
        '<input type="submit" value="Upload">'+
        '</form>'
    );
});

app.post('/upload', function(req, res){
	console.log("Received file:\n" + JSON.stringify(req.files));
	
	var photoDir = __dirname+"/photos/";
	var thumbnailsDir = __dirname+"/photos/thumbnails/";
	var photoName = req.files.source.name;

	fs.rename(
		req.files.source.path,
		photoDir+photoName,
		function(err){
			if(err != null){
				res.send({error:"Server Writting No Good"});
			} else {
				im.resize(
					{
						srcData:fs.readFileSync(photoDir+photoName, 'binary'),
						width:256
					}, 
					function(err, stdout, stderr){
						if(err != null){
							res.send({error:"Resizeing No Good"});
						} else {
							fs.writeFileSync(thumbnailsDir+"thumb_"+photoName, stdout, 'binary');
							res.send("Ok");
						}
					}
				);
			}
		}
	);
});

app.get('/info', function(req, res){
	console.log(__dirname);
	res.send("ok");
});

app.listen(1337);