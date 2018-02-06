package com.anubhav_singh.infoedgeassignment;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.anubhav_singh.infoedgeassignment.viewModels.CustomNearbyPlacesViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class VenueDetailsActivity  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    @BindView(R.id.toolbar_venue_info)
    android.support.v7.widget.Toolbar toolbar;
     Fragment fragment;
    private String venueID;
    private SupportMapFragment mapFragment;
    private Venue venueModel;
    private GoogleMap mMap;
    private CustomNearbyPlacesViewModel customNearbyPlacesViewModel;
    private int pos;
    private String updatedUserRemark;
    private LovelyTextInputDialog lovelyTextInputDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details_layout);
        ButterKnife.bind(this);
        setUpResources();
        fetchParentIntent();

    }
    private void setUpMaterialDialog(int pos, View view) {
        /**
         * Commented
         */
        lovelyTextInputDialog = new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("User Reviews")
                .setMessage("Enter the review for this venue")
                .setConfirmButton("Save Review", new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        //Update Venue
                        updatedUserRemark = text;
                       /* EditText editText = (EditText) view.findViewById(R.id.et_user_review_recycler_item);
                        editText.setText(updatedUserRemark);
                        String hiddenId = ((TextView) view.findViewById(R.id.hiddenVenueIdField)).getText().toString().trim();
                        new VenueUpdateAsyncTask().execute(hiddenId);*/

                        //Below code showing disrepancies in few areas...!!
                      /* PagedList<Venue> venuePagedList =  customNearbyPlacesViewModel.getPagedVenueListLiveData().getValue();
                       if(venuePagedList!=null && venuePagedList.get(pos)!=null) {
                           new VenueUpdateAsyncTask().execute(venuePagedList.get(pos).getId());
                       }else{
                           Toast.makeText(MainActivity.this, "There seems a problem while loading paginated data", Toast.LENGTH_SHORT).show();
                       }*/
                    }
                });
        lovelyTextInputDialog.show();
    }

    private void fetchParentIntent() {
        if(getIntent()!=null && getIntent().getExtras()!=null){
            venueModel = (Venue) getIntent().getSerializableExtra(ConstantUtill.PARENT_INTENT_BUNDLE_KEY);
        }
    }

    private void setUpResources() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_venue_info);
        customNearbyPlacesViewModel = ViewModelProviders.of(this).get(CustomNearbyPlacesViewModel.class);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*customNearbyPlacesViewModel.getPagedVenueListLiveData().observe(this, pagedList -> {
            if(pagedList!=null && pagedList.get(pos)!=null) {
                venueModel = pagedList.get(pos);
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ConstantUtill.DEFAULT_VENUE_INTENT_ACTION + venueID));
        startActivity(browserIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(venueModel!=null && venueModel.getLocation()!=null) {
            LatLng venue = new LatLng(venueModel.getLocation().getLat(), venueModel.getLocation().getLng());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 16));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(venue)
                    .title(venueModel.getName())
                    .snippet("View Venue"));
            marker.showInfoWindow();
            mMap.setOnInfoWindowClickListener(this);
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Shows the user's current location
                mMap.setMyLocationEnabled(true);
            }
        }
    }
    //    class VenueUpdateAsyncTask extends AsyncTask<String,Void,Void>{
//
//        @Override
//        protected Void doInBackground(String... integers) {
//            String pos = integers[0];
//            if(customNearbyPlacesViewModel!=null && customNearbyPlacesViewModel.getPagedVenueListLiveData()!=null){
//               Venue venueToUpdate = customNearbyPlacesViewModel.getDatabaseRequestDao().getVenueFromVenueId(pos);
//               if(venueToUpdate!=null){
//                   venueToUpdate.setUserRemarks(updatedUserRemark);
//                   customNearbyPlacesViewModel.getDatabaseRequestDao().updateVenues(venueToUpdate);
//                   customNearbyPlacesViewModel.init();
//               }
//            }
//            return null;
//        }
//    }
}
