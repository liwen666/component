var PROTO_PATH = __dirname + '/hw.proto';

var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });
var hello_proto = grpc.loadPackageDefinition(packageDefinition).helloworld;

var cache = {}
/**
 * Implements the SayHello RPC method.
 */
function sayHello(call, callback) {
  callback(null, {name: call.request.name,time:new Date().getTime()});
}

function writeMsg(call, callback) {
  cache[call.request.key] = call.request.value;
  console.log(JSON.stringify(cache))
  callback(null, {result:'done'});
}

function readMsg(call, callback) {
  let value = cache[call.request.key]
  callback(null, {result:value});
}

function listMessage(call) {
  // For each feature, check if it is in the given bounding box
  for(var i=0;i<5;i++){
    let value = cache[call.request.key]
    call.write({result:value});
  }
  call.end();
}
/**
 * Starts an RPC server that receives requests for the Greeter service at the
 * sample server port
 */
function main() {
  var server = new grpc.Server();
  server.addService(hello_proto.Greeter.service, {sayHello: sayHello,wMsg:writeMsg,rMsg:readMsg,listMsg:listMessage});
  server.bind('0.0.0.0:50051', grpc.ServerCredentials.createInsecure());
  server.start();
}

main();
