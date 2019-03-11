package com.temp.component.client;


import com.temp.component.grpc.GreeterGrpc;
import com.temp.component.grpc.HelloReply;
import com.temp.component.grpc.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;


public class HelloWorldClientWeb {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;


    public HelloWorldClientWeb(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true) 
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel); 
    }


    public void shutdown() throws InterruptedException { 
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS); 
    }

    public  void greet(String name){ 
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getName()+response.getTime());

    }

    public static void main(String[] args) throws InterruptedException { 
        HelloWorldClientWeb client = new HelloWorldClientWeb("127.0.0.1",50051);
        for(int i=0;i<5;i++){
            client.greet("helloworld:"+i);
        }


    } 
}