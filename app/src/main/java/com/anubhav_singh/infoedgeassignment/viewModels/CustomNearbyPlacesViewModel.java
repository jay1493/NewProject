package com.anubhav_singh.infoedgeassignment.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.models.VenueSearches;
import com.anubhav_singh.infoedgeassignment.network.APICallsInterface;
import com.anubhav_singh.infoedgeassignment.network.APIClient;
import com.anubhav_singh.infoedgeassignment.network.ApiResponseRepository;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class CustomNearbyPlacesViewModel extends AndroidViewModel {

    private Location location;
    private LiveData<VenueSearches> venueSearchesObservable;
    private APICallsInterface apiCallsInterface;

    public CustomNearbyPlacesViewModel(@NonNull Application application) {
        super(application);
        apiCallsInterface = APIClient.getClient().create(APICallsInterface.class);
    }

    public void setVenuesBasedOnRefereshedLocation(Location location){
        this.location = location;
        venueSearchesObservable = ApiResponseRepository.getInstance().getVenuesFromAPI(location,apiCallsInterface, ConstantUtill.SEARCH_VENUE_QUERY_PARAM);

    }

    public LiveData<VenueSearches> getVenueSearchesObservable(){
        return venueSearchesObservable;
    }

}
