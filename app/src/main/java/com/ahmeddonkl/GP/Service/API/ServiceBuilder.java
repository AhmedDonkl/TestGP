package com.ahmeddonkl.GP.Service.API;

import com.ahmeddonkl.GP.Constants;

import retrofit.RestAdapter;

/**
 * Created by OmarAli on 16/10/2015.
 */
public class ServiceBuilder {
    private RestAdapter restAdapter;
    public ServiceBuilder(){
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(Constants.BASE_URL).build();
    }
    public API.Routes BuildRoutes(){
        return restAdapter.create(API.Routes.class);
    }

/*    public API.Trailers BuildTrailers(){
        return restAdapter.create(API.Trailers.class);
    }
    public API.Reviews BuildReviews(){
        return restAdapter.create(API.Reviews.class);
    } */
}
