package com.temp.component.client;

import com.temp.component.grpc.GrpcRequest;
import com.temp.component.grpc.GrpcResponse;
import com.temp.component.grpc.GrpcServerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private final ManagedChannel channel;
    private final GrpcServerGrpc.GrpcServerBlockingStub blockingStub;


    public GrpcClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();

        blockingStub = GrpcServerGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public  void greet(String name){
        GrpcRequest request = GrpcRequest.newBuilder().setName(name).build();
        GrpcResponse response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient("127.0.0.1",50000);
        for(int i=0;i<5;i++){
            client.greet("world:"+i);
        }
       HelloWorldClient client1 = new HelloWorldClient("127.0.0.1",50051);
        for(int i=0;i<5;i++){
            client.greet("helloworld:"+i);
        }


    }

}
