console.log(__dirname)
var PROTO_PATH = __dirname + '/../proto/hw.proto';

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

function main() {

    console.log('write success');
  var client = new hello_proto.Greeter('localhost:50051',
                                       grpc.credentials.createInsecure());
  var key,value;
  console.log(process.argv.length)
  console.log(process.argv[0])
  console.log(process.argv[1])
  console.log(process.argv[2])
  console.log(process.argv[3])

  if (process.argv.length >= 4) {
    key = process.argv[2];
    value = process.argv[3];
    client.wMsg({key: key,value:value}, function(err, response) {

      if(response.result == 'done'){
        console.log('write success');
      }

      // var call = client.listMsg({key:key});
      // call.on('data', function(feature) {
      //     console.log(feature.result);
      // });
      // call.on('end', ()=>{
      //   console.log('done')
      // });
    });

  } else if (process.argv.length ==3) {
    key = process.argv[2];
    client.rMsg({key: key}, function(err, response) {
      console.log(key +' is:', response.result);
    });
  }

  client.sayHello({name: "bbbbb"}, function(err, response) {
    console.log('Greeting:', response.name);
    console.log('Time is:', response.time);
  });

}

main();
