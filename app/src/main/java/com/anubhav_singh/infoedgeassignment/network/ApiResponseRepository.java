package com.anubhav_singh.infoedgeassignment.network;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;

import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.database.entities.LocationEntity;
import com.anubhav_singh.infoedgeassignment.models.Venue;
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

    private DatabaseRequestDao databaseRequestDao;

    private Location location;

    private MutableLiveData<PagedList<Venue>> pagedListMutableLiveData;

    public static ApiResponseRepository getInstance(){
        return new ApiResponseRepository();
    }

    public MutableLiveData<VenueSearches> getVenuesFromAPI(@NonNull Location location, @NonNull APICallsInterface apiCallsInterface,
                                                           @NonNull String queryParam, @NonNull DatabaseRequestDao databaseRequestDao){
        this.databaseRequestDao = databaseRequestDao;
        this.location = location;
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
                 *
                 * Start background thread here to update venue's
                 */
                Log.e("", "onResponse: " );
                new CustomAsyncTask().execute(response.body());
            }

            @Override
            public void onFailure(Call<VenueSearches> call, Throwable t) {
                Log.d("", "onFailure: Retrofit Searches Api Failed");
            }
        });
        return venuesList;
    }

    class CustomAsyncTask extends AsyncTask<VenueSearches,Void,Void>{

        @Override
        protected Void doInBackground(VenueSearches... venueSearches) {
            //Deleting old venue's, and will feed new location based venue's
            if(venueSearches[0]!=null) {
                //Also update current location table
                databaseRequestDao.deleteCurrentLocation();
                LocationEntity locationEntity = new LocationEntity();
                locationEntity.setLatitude(location.getLatitude());
                locationEntity.setLongitude(location.getLongitude());
                databaseRequestDao.insertCurrentLocation(locationEntity);
                databaseRequestDao.deleteVenues();
                List<Venue> venueList = venueSearches[0].getResponse().getVenues();
                databaseRequestDao.insertVenues(venueList);
            }
            return null;
        }
    }

}
