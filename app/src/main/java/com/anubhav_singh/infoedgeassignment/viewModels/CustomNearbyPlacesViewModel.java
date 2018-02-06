package com.anubhav_singh.infoedgeassignment.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.location.Location;
import android.support.annotation.NonNull;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.database.ResturantsDatabase;
import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.anubhav_singh.infoedgeassignment.models.VenueSearches;
import com.anubhav_singh.infoedgeassignment.network.APICallsInterface;
import com.anubhav_singh.infoedgeassignment.network.APIClient;
import com.anubhav_singh.infoedgeassignment.network.ApiResponseRepository;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class CustomNearbyPlacesViewModel extends AndroidViewModel {

    private final PagedList.Config pagedListConfig;
    private DatabaseRequestDao databaseRequestDao;
    private Location location;
    private LiveData<VenueSearches> venueSearchesObservable;
    private LiveData<PagedList<Venue>> pagedVenueListLiveData;
    private APICallsInterface apiCallsInterface;

    public CustomNearbyPlacesViewModel(@NonNull Application application) {
        super(application);
        apiCallsInterface = APIClient.getClient().create(APICallsInterface.class);
        databaseRequestDao = ResturantsDatabase.create(application).getDatabaseRequestDao();
        pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(ConstantUtill.PAGED_LIST_FETCH_DIST)
                        .setPageSize(ConstantUtill.PAGED_LIST_DEF_PAGE_SIZE).build();
    }

    public void init(){
        pagedVenueListLiveData = (new LivePagedListBuilder<>(databaseRequestDao.getVenues(),pagedListConfig)).build();
    }

    public void setVenuesBasedOnRefereshedLocation(Location location){
        this.location = location;
        venueSearchesObservable = ApiResponseRepository.getInstance().getVenuesFromAPI(location,apiCallsInterface, ConstantUtill.SEARCH_VENUE_QUERY_PARAM,databaseRequestDao);
        init();
    }

    public LiveData<PagedList<Venue>> getPagedVenueListLiveData(){
        return pagedVenueListLiveData;
    }

    public LiveData<VenueSearches> getVenueSearchesObservable(){
        return venueSearchesObservable;
    }

}
