package com.anubhav_singh.infoedgeassignment;

import android.Manifest;
import android.annotation.TargetApi;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anubhav_singh.infoedgeassignment.adapters.VenueAdapter;
import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.database.ResturantsDatabase;
import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.database.entities.LocationEntity;
import com.anubhav_singh.infoedgeassignment.listeners.CustomRecycleViewTouchListener;
import com.anubhav_singh.infoedgeassignment.listeners.VenueItemClickListener;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.anubhav_singh.infoedgeassignment.viewModels.CustomNearbyPlacesViewModel;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, VenueItemClickListener {

    @BindView(R.id.main_coordinator_layout)
     CoordinatorLayout coordinatorLayout;

    @BindView(R.id.main_collapsing_toolbar)
     CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.main_search_bar)
     EditText etSearchBar;

    @BindView(R.id.tool_bar)
     Toolbar toolbar;

    @BindView(R.id.main_shimmer_list)
     ShimmerRecyclerView shimmerRecyclerView;

    @BindView(R.id.no_result_found_stub)
     ViewStub noResultFoundViewStub;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private CustomNearbyPlacesViewModel customNearbyPlacesViewModel;
    private VenueAdapter venueAdapter;
    private LovelyTextInputDialog lovelyTextInputDialog;
    private String updatedUserRemark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpResources();
        if (checkPlayServices()) {
            setUpPermissions();
        }else{
            inflateNoPermissionsFoundError();
        }


    }

    private void inflateNoPermissionsFoundError() {
        shimmerRecyclerView.setVisibility(View.GONE);
        noResultFoundViewStub.setLayoutResource(R.layout.no_result_found_stub_inflater);
        noResultFoundViewStub.inflate();
    }

    private void setUpResources() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_title));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.black));
        customNearbyPlacesViewModel = ViewModelProviders.of(this).get(CustomNearbyPlacesViewModel.class);
        venueAdapter = new VenueAdapter(this,shimmerRecyclerView);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        shimmerRecyclerView.setAdapter(venueAdapter);
        shimmerRecyclerView.addOnItemTouchListener(new CustomRecycleViewTouchListener(this,shimmerRecyclerView,this));

    }

    private void setUpMaterialDialog(int pos, View view) {
        lovelyTextInputDialog = new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("User Reviews")
                .setMessage("Enter the review for this venue")
                .setConfirmButton("Save Review", new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        //Update Venue
                       updatedUserRemark = text;
                       EditText editText = (EditText) view.findViewById(R.id.et_user_review_recycler_item);
                       editText.setText(updatedUserRemark);
                       new VenueUpdateAsyncTask().execute(pos);
                    }
                });
        lovelyTextInputDialog.show();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        ConstantUtill.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.play_services_not_installed), Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setUpPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ConstantUtill.LOCATION_PERMISSION_REQUEST_CODE);
        }else{
            setUpLocationRequests();
            shimmerRecyclerView.showShimmerAdapter();
        }
    }


    private void setUpLocationRequests() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(ConstantUtill.UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(ConstantUtill.FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(ConstantUtill.DISPLACEMENT);
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setUpPermissions();
        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setUpPermissions();
        }else {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
            Log.e("", "displayLocation: ");
            if (mLastLocation != null) {
                new CustomAsyncTask().execute();


            }
        }
    }

    /*@OnEditorAction(R.id.main_search_bar)
    public void onSearhDialogClicked(TextView textView, int code, KeyEvent keyEvent){
        if(code == EditorInfo.IME_ACTION_SEARCH){
            String query = textView.getText().toString().trim();
            if(!TextUtils.isEmpty(query)){
                if(customNearbyPlacesViewModel!=null && customNearbyPlacesViewModel.getPagedVenueListLiveData()!=null){
                    PagedList<Venue> copyList = customNearbyPlacesViewModel.getPagedVenueListLiveData().getValue();
                    //TODO: Handle later, due to short time...
                    Toast.makeText(this, "Search is not handled right now,Sorry!", Toast.LENGTH_SHORT).show();
                    etSearchBar.setHint(getResources().getString(R.string.search_hint));

                }else{
                    etSearchBar.setHint(getResources().getString(R.string.search_hint));
                }
            }else{
                etSearchBar.setHint(getResources().getString(R.string.search_hint));
            }
        }

    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportActionBar() != null) {
            setSupportActionBar(toolbar);
        }
        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
        if(customNearbyPlacesViewModel!=null && customNearbyPlacesViewModel.getPagedVenueListLiveData()!=null) {
            customNearbyPlacesViewModel.getPagedVenueListLiveData().observe(this, pagedList -> {
                venueAdapter.setList(pagedList);
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case ConstantUtill.LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   setUpLocationRequests();
                    shimmerRecyclerView.showShimmerAdapter();
                }else{
                    setUpPermissions();
                }
                break;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
        Log.e("", "onConnected: ");
    }


    @Override
    public void onConnectionSuspended(int i) {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, getResources().getString(R.string.location_not_updated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();

    }

    @Override
    public void onItemClick(View view, int position) {
      //Fire Next Activity
        Toast.makeText(this, "Item Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserReviewLongClick(View view, int position) {
       //Fire Dialog, and update venues
       setUpMaterialDialog(position,view);
    }

    class CustomAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            LocationEntity locationEntity = customNearbyPlacesViewModel.getDatabaseRequestDao().getSavedLocation();
            if(locationEntity!=null){
                /**Now check, if we are at same position, if yes, then don't hit service */
                if(locationEntity.getLatitude() == mLastLocation.getLatitude() && locationEntity.getLongitude() == mLastLocation.getLongitude()){
                    //Same Location
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customNearbyPlacesViewModel.setVenuesBasedOnRefereshedLocation(mLastLocation,false);
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customNearbyPlacesViewModel.setVenuesBasedOnRefereshedLocation(mLastLocation,true);
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customNearbyPlacesViewModel.setVenuesBasedOnRefereshedLocation(mLastLocation,true);
                    }
                });
            }
            return null;
        }
    }

    class VenueUpdateAsyncTask extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            int pos = integers[0];
            if(customNearbyPlacesViewModel!=null && customNearbyPlacesViewModel.getPagedVenueListLiveData()!=null){
               Venue venueToUpdate = customNearbyPlacesViewModel.getDatabaseRequestDao().getVenueFromVenueId(customNearbyPlacesViewModel.getPagedVenueListLiveData().getValue().get(pos).getId());
               if(venueToUpdate!=null){
                   venueToUpdate.setUserRemarks(updatedUserRemark);
                   customNearbyPlacesViewModel.getDatabaseRequestDao().updateVenues(venueToUpdate);
               }
            }
            return null;
        }
    }
}
