package com.simscale.catalog.client;

import com.simscale.catalog.client.domain.User;
import com.simscale.catalog.client.http.HttpMethod;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class JobRequestGenerator {

    public static List<JobRequest> build(JobRequestGeneratorConfig params){

        List<JobRequest> jobRequests = new ArrayList<>();
        if(params.getPostRequests() > 0){
            jobRequests.addAll(JobRequestGenerator.buildPost(params.getPostRequests(), params.getPostEndpoint()));
        }

        if(params.getGetRequests() > 0){
            jobRequests.addAll(JobRequestGenerator.buildGet(params.getGetRequests(), params.getGetEndpoint()));
        }

        if(params.getPutRequests() > 0){
            jobRequests.addAll(JobRequestGenerator.buildPut(params.getPutRequests(), params.getPutEndpoint()));
        }

        if(params.getDeleteRequests() > 0){
            jobRequests.addAll(JobRequestGenerator.buildDelete(params.getDeleteRequests(), params.getDeleteEndpoint()));
        }
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
            jobRequests.add(
                    new JobRequest(endpoint, method, JobRequestGenerator.getUserInstance(method, i))
            );
        }

        return jobRequests;

    }

    private static User userBuildWithId(int index){
        return new User(new Long(index), "username-" + index, "firstname-" + index, "lastname-" + index );
    }

    private static User userBuildWithNoId(int index){
        return new User("username-" + index, "firstname-" + index, "lastname-" + index );
    }

    private static User getUserInstance(HttpMethod method, int id){
        if(ObjectUtils.equals(method, HttpMethod.POST)){
            return userBuildWithNoId(id);
        }

        if(ObjectUtils.equals(method, HttpMethod.GET)){
            //return User.empty();
            return null;
        }

        return JobRequestGenerator.userBuildWithId(id);

    }




}
