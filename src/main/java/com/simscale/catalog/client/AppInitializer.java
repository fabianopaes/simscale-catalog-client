package com.simscale.catalog.client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simscale.catalog.client.circuitbreaker.CircuitBreakerCustom;
import com.simscale.catalog.client.http.WSClientImpl;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class AppInitializer {


    public static void main(String[] args) throws InterruptedException, IOException {

        // Here comes all servers that we have
        List<Server> servers = Arrays.asList(
                new Server("http://localhost", 9000,  "GED"),
                new Server("http://localhost", 9001,  "Patient-Data"),
                new Server("http://localhost",  9002, "Report")
        );

        File file = new File("resources/servers.json");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

/*        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file = new File("resources/servers.json");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        List<Server> servers = Arrays.asList(
                objectMapper.readValue(file, Server[].class)
        );*/


        // Here comes our jobs that we have to balance between our servers
        List<Job> jobs = Arrays.asList(
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null)
        );

        ExecutionManager executionManager = new ExecutionManager(
                new ArrayDeque<>(servers),
                jobs,
                new WSClientImpl()
        );

/*        int index = 1;
        while(index <= 15){
            executionManager.execute();
            index++;
            Thread.sleep(5 * 1000);
        }

        String string_json = FileUtils.readFileToString(new File("resources/insurance.json"));
*/


        executionManager.execute();


        System.exit(NumberUtils.INTEGER_ZERO);

    }



}
