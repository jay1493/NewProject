package com.anubhav_singh.infoedgeassignment.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.location.Location;
import android.support.annotation.NonNull;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.database.ResturantsDatabase;
import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.models.ExploreVenues;
import com.anubhav_singh.infoedgeassignment.models.Item;
import com.anubhav_singh.infoedgeassignment.network.APICallsInterface;
import com.anubhav_singh.infoedgeassignment.network.APIClient;
import com.anubhav_singh.infoedgeassignment.network.ApiResponseRepository;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class CustomNearbyPlacesViewModel extends AndroidViewModel {

    private PagedList.Config pagedListConfig;
    private DatabaseRequestDao databaseRequestDao;
    private Location location;
    private LiveData<ExploreVenues> venueSearchesObservable;
    private LiveData<PagedList<Item>> pagedVenueListLiveData;
    private APICallsInterface apiCallsInterface;

    public CustomNearbyPlacesViewModel(@NonNull Application application) {
        super(application);
        apiCallsInterface = APIClient.getClient().create(APICallsInterface.class);
        databaseRequestDao = ResturantsDatabase.create(application).getDatabaseRequestDao();

        init();
    }

    public void init(){
        pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(ConstantUtill.PAGED_LIST_FETCH_DIST)
                        .setPageSize(ConstantUtill.PAGED_LIST_DEF_PAGE_SIZE).build();
        pagedVenueListLiveData = (new LivePagedListBuilder<>(databaseRequestDao.getItems(),pagedListConfig)).build();

    }

    public void setVenuesBasedOnRefereshedLocation(Location location,boolean isServiceHitRequired){
        this.location = location;
        if(!isServiceHitRequired){
            //Location is same, now check if we have data
            if(pagedVenueListLiveData == null){
                //Feed Data from Db, if exists
                init();
            }
        }else{
            venueSearchesObservable = ApiResponseRepository.getInstance().getVenuesFromAPI(location,apiCallsInterface, ConstantUtill.SEARCH_VENUE_QUERY_PARAM,databaseRequestDao);
//            init();
        }

    }

    public LiveData<PagedList<Item>> getPagedVenueListLiveData(){
        return pagedVenueListLiveData;
    }

    public LiveData<ExploreVenues> getVenueSearchesObservable(){
        return venueSearchesObservable;
    }

    public DatabaseRequestDao getDatabaseRequestDao(){
        return databaseRequestDao;
    }

}
