package com.hammadltd.configservice.controllers;

import com.hammadltd.configservice.grpc.ConfigServiceGrpc;
import com.hammadltd.configservice.grpc.Configs;
import com.hammadltd.configservice.interfaces.IConfigFetcher;
import com.hammadltd.configservice.interfaces.ISecretValidator;
import com.hammadltd.configservice.services.ConfigsFetcher;
import com.hammadltd.configservice.services.SecretValidator;
import io.grpc.stub.StreamObserver;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class ConfigController extends ConfigServiceGrpc.ConfigServiceImplBase {
    private ISecretValidator secretValidator = new SecretValidator();
    private IConfigFetcher configFetcher = new ConfigsFetcher();


    @Override
    public void getConfigs(Configs.configRequest request, StreamObserver<Configs.configResponse> responseObserver) {
        try {
            if (secretValidator.validate(request.getSecretKey())) {
                String[] parsedConfigs = this.parseNeededConfigs(request);

                HashMap<String, String> neededConfigs = configFetcher.fetch(parsedConfigs);

                responseObserver.onNext(
                        Configs.configResponse.newBuilder()
                        .setResponseCode(200)
                        .setMessage(new JSONObject(neededConfigs)
                        .toString()).build()
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        responseObserver.onNext(
                Configs.configResponse.newBuilder()
                .setResponseCode(401)
                .setMessage(new JSONObject("Failed")
                .toString()).build()
        );
    }

    private String[] parseNeededConfigs(Configs.configRequest request) {
        int neededConfigsCount = request.getNeededConfigsCount();
        String[] result = new String[neededConfigsCount];

        for (int i = 0; i < neededConfigsCount; i++) {
            result[i] = request.getNeededConfigs(i);
        }

        return result;
    }
}
