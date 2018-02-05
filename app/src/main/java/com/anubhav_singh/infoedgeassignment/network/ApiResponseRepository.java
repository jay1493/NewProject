package com.anubhav_singh.infoedgeassignment.network;

import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;

import com.anubhav_singh.infoedgeassignment.models.VenueSearches;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class ApiResponseRepository {

    public static ApiResponseRepository getInstance(){
        return new ApiResponseRepository();
    }

    public MutableLiveData<VenueSearches> getVenuesFromAPI(@NonNull Location location, @NonNull APICallsInterface apiCallsInterface,
                                                           @NonNull String queryParam){
        final MutableLiveData<VenueSearches> venuesList = new MutableLiveData<>();
        Map<String, String> queryParams = new HashMap<>();
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(String.valueOf(location.getLatitude()));
        locationBuilder.append(",");
        locationBuilder.append(String.valueOf(location.getLongitude()));
        queryParams.put("ll", locationBuilder.toString());
        queryParams.put("client_id", ConstantUtill.FOUR_SQUARE_CLIENT_ID);
        queryParams.put("client_secret", ConstantUtill.FOUR_SQUARE_CLIENT_SECRET);
        queryParams.put("v", ConstantUtill.FOURSQUARE_VERSION);
        queryParams.put("query",queryParam);
        Call<VenueSearches> venueSearchesCall = apiCallsInterface.getVenues(queryParams);
        venueSearchesCall.enqueue(new Callback<VenueSearches>() {
            @Override
            public void onResponse(Call<VenueSearches> call, Response<VenueSearches> response) {
                venuesList.setValue(response.body());
                /**
                 * TODO:
                 * Start background thread here to update venue's
                 */
            }

            @Override
            public void onFailure(Call<VenueSearches> call, Throwable t) {
                Log.d("", "onFailure: Retrofit Searches Api Failed");
            }
        });
        return venuesList;
    }

}
