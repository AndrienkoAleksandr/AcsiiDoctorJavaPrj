var express = require('express');
var app = express();

app.get('/', function(req, res) {
    res.sendFile(__dirname + '/index.html');
});

app.use('/node_modules/asciidoctor.js/dist', express.static(__dirname + '/../../../../../node_modules/asciidoctor.js/dist'));
app.use('/static', express.static(__dirname + '/../../../../main/resources'));

app.listen(4002, function () {
    console.log('Example app listening on port 4002!')
});