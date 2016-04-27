package com.ahmeddonkl.GP.Service.API;

import com.ahmeddonkl.GP.Service.Model.RouteObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by OmarAli on 15/10/2015.
 */
public interface API {

    public interface Routes{
        @GET("/maps/api/directions/json")
        void getRoutes(@Query("origin") String orgin, @Query("destination") String destination, @Query("waypoints") String waypoints, @Query("key") String key, Callback<RouteObject> routeObject);
    }
}
