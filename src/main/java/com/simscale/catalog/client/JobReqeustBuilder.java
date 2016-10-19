package com.simscale.catalog.client;

import com.simscale.catalog.client.domain.User;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class JobReqeustBuilder {

    public static List<JobRequest> build(JobBuilderConfig params){

        List<JobRequest> jobRequests = new ArrayList<>();
        jobRequests.addAll(JobReqeustBuilder.buildPost(params.getPostRequests(), params.getPostEndpoint()));
        jobRequests.addAll(JobReqeustBuilder.buildGet(params.getGetRequests(), params.getGetEndpoint()));
        jobRequests.addAll(JobReqeustBuilder.buildPut(params.getPutRequests(), params.getPutEndpoint()));
        jobRequests.addAll(JobReqeustBuilder.buildDelete(params.getDeleleteRequests(), params.getDeleteEndpoint()));
        return jobRequests;
    }

    public static List<JobRequest> buildPost(int index, String endpoint){
        return buildRequest(index, HttpMethod.POST, endpoint);
    }

    public static List<JobRequest> buildGet(int index, String endpoint){
        return buildRequest(index, HttpMethod.GET, endpoint);
    }

    public static List<JobRequest> buildPut(int index, String endpoint){
        return buildRequest(index, HttpMethod.PUT, endpoint);
    }

    public static List<JobRequest> buildDelete(int index, String endpoint){
        return buildRequest(index, HttpMethod.DELETE, endpoint);
    }

    private static List<JobRequest> buildRequest(int index, HttpMethod method, String endpoint){

        List<JobRequest> jobRequests = new ArrayList<>();
        for(int i = 1; i <= index; i++){

            User user = ObjectUtils.equals(method, HttpMethod.POST) || ObjectUtils.equals(method, HttpMethod.PUT) ?
                    JobReqeustBuilder.userBuild(i) : null;
            jobRequests.add(new JobRequest(endpoint, method, user));
        }

        return jobRequests;

    }

    private static User userBuild(int index){
        return new User(new Long(index), "username-" + index, "firstname-" + index, "lastname-" + index );
    }




}
