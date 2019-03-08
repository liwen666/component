package com.temp.component.service;

import com.temp.component.grpc.GrpcRequest;
import com.temp.component.grpc.GrpcResponse;
import com.temp.component.grpc.GrpcServerGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GrpcService {
    private int port = 50000;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GrpcService.GrpcServerImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GrpcService.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        final GrpcService server = new GrpcService();
        server.start();
        server.blockUntilShutdown();
    }

    // 实现 定义一个实现服务接口的类
    private class GrpcServerImpl extends GrpcServerGrpc.GrpcServerImplBase{


        public void sayHello(GrpcRequest req, StreamObserver<GrpcResponse> responseObserver) {
            System.out.println("service:"+req.getName());
            GrpcResponse build = GrpcResponse.newBuilder().setMessage(("Hello: " + req.getName())).build();
            responseObserver.onNext(build);
            responseObserver.onCompleted();
        }



    }




}
