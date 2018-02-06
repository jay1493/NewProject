package com.anubhav_singh.infoedgeassignment.network;

import com.anubhav_singh.infoedgeassignment.models.ExploreVenues;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public interface APICallsInterface {

    @GET("explore")
    Call<ExploreVenues> getVenues(@QueryMap Map<String, String> options);


}
