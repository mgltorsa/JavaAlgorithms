var myIp = 0;
var datas = [];
window.onload = function() {
    getClientIp();
}

function calculateDolar() {
    var a1 = $("#i1").val();
    var a2 = $("#i2").val();
    var a3 = $("#i3").val();
    var p = $("#res");
    var array = [];
    var res = a1;
    array['S'] = 0;
    array['E'] = 0;
    array['B'] = 0;
    if (isNaN(array[a1]) || isNaN(array[a2]) || isNaN(array[a3])) {
        alert("En cada campo de texto ingrese S B o E");
    } else {
        array[a1] = 1 + array[a1];
        array[a2] = 1 + array[a2];
        array[a3] = 1 + array[a3];
        array.sort();
        console.log(array[a1] + " a2 " + array[a2] + " a3 " + array[a3]);
        if (array[a1] >= array[a2] && array[a1] >= array[a3]) {
            res = a1;
        } else if (array[a2] >= array[a1] && array[a2] >= array[a3]) {
            res = a2;
        } else {
            res = a3;
        }
        p.html("El estado del dolar, para mañana sera: " + res);
    }
}

function sendMessage() {
    var name = $("#name").val();
    var pass = $("#pass").val();
    var ip = getServerIp();
    console.log(name);
    var port = getPort();
    var httpClient = getHttpClient();
    var url = ip;
    var client = new httpClient();
    client.get(url, function(response) {
        var res = JSON.parse(response);
        alert(res);
    })
    //For sockets.io js
    // var socket = io('ws://'+ip+':'+port);
    // socket.host
    // socket.on('connect', function(){
    // 	socket.write("name:"+pass+" pass:"+pass);
    // });
    // socket.on('event', function(data){
    // 	console.log("event:" +data);
    //  });
    //  socket.on('disconnect', function(){
    //  	console.log("diss");
    //  });
    //for node.js server
    //var net = require('net');
    //var socket = net.connect(port,ip);
    //socket.setEnconding('utf8');
    //socket.write("name:"+name+" pass:"+pass);
    //client.on('data',function(data){
    //console.log("received:",data);
    //});
    //for webSockets 
    //	var socket = new WebSocket('ws://'+ip+':'+port);
    //	socket.onmessage = function(e){ console.log("mensaje");console.log(e.data); };
    //	socket.onopen = () => socket.send("name: "+name+" pass"+pass);
}

function getHttpClient() {
    this.get = function(url, callback) {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function() {
            if (httpRequest.readystate == 4 && httpRequest.status == 200) {
                callback(httpRequest.responseText);
            }
        }
        httpRequest.open("POST", url, true);
        httpRequest.send(null);
    }
}

function listening(message) {
    var myIp = getClientIp();
    window.alert(message);
}

function getClientIp() {
    $.getJSON('https://api.ipify.org?format=json', function(json) {
        myIp = json.ip;
    });
    console.log(myIp);
}

function getServerIp() {
    var ip = '172.30.176.27';
    ip = '172.30.177.60';
    ip = '192.168.1.50';
    return ip;
}

function getPort() {
    var port = 1025;
    port = 1234;
    return port;
}