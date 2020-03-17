package com.hammadltd.configservice;

import com.hammadltd.configservice.controllers.ConfigController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class App {

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
        try{
            Server server = ServerBuilder.forPort(7789)
                    .addService(new ConfigController()).build();
            server.start();
            System.out.println("RPC server listening on port " + server.getPort());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
