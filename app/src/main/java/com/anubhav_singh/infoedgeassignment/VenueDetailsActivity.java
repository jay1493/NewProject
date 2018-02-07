package com.anubhav_singh.infoedgeassignment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.database.ResturantsDatabase;
import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.database.entities.UserReviewEntity;
import com.anubhav_singh.infoedgeassignment.models.Item;
import com.anubhav_singh.infoedgeassignment.models.Photo;
import com.anubhav_singh.infoedgeassignment.models.Venue;
import com.bumptech.glide.Glide;
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
import butterknife.OnClick;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class VenueDetailsActivity  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    @BindView(R.id.toolbar_venue_info)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.iv_venue_details)
    ImageView ivVenuImage;
    @BindView(R.id.tv_avg_cost_venue_info)
    TextView tvAvgVenueCost;
    @BindView(R.id.tv_time_venue_info)
    TextView tvTimeOfVenue;
    @BindView(R.id.tv_user_review_venue_info)
    TextView tvUserVenueReview;
    @BindView(R.id.ll_personalized_review_for_venue)
    LinearLayout llPersonalizedReview;
    @BindView(R.id.tv_personal_review_venue_info)
    TextView tvPersonalizedReview;


    private SupportMapFragment mapFragment;
    private Item itemModel;
    private GoogleMap mMap;
    private LovelyTextInputDialog lovelyTextInputDialog;
    private StringBuilder venuePicUrl;
    private DatabaseRequestDao databaseRequestDao;
    private AsyncTask<Void, Void, String> refereshPersonalizedReviewAsyncTask;
    private String venueUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details_layout);
        ButterKnife.bind(this);
        setUpResources();
        fetchParentIntent();

    }
    private void setUpMaterialDialog(View view) {
        lovelyTextInputDialog = new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Personalized User Review")
                .setMessage("Enter the review for this venue")
                .setConfirmButton("Save Review", new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        //Update Venue
                        tvPersonalizedReview.setText(text);

                        new CustomAysncTask().setInputReviewText(text).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,AsyncTaskKeysEnum.SET_REVIEW_ASYNC.getKeyCode());

                    }
                });
        lovelyTextInputDialog.show();
    }

    private void fetchParentIntent() {
        if(getIntent()!=null && getIntent().getExtras()!=null){
            itemModel = (Item) getIntent().getSerializableExtra(ConstantUtill.PARENT_INTENT_BUNDLE_KEY);
        }
        if(itemModel!=null){
            if(itemModel.getTips()!=null && itemModel.getTips().size()>0 && itemModel.getTips().get(0).getUser()!=null && itemModel.getTips().get(0).getUser().getPhoto()!=null){
                if(!TextUtils.isEmpty(itemModel.getTips().get(0).getText())){
                    tvUserVenueReview.setText(itemModel.getTips().get(0).getText());
                }else{
                    tvUserVenueReview.setText(R.string.user_review_not_avaiable);
                }
                if(!TextUtils.isEmpty(itemModel.getTips().get(0).getCanonicalUrl())){
                    venueUrl = itemModel.getTips().get(0).getCanonicalUrl();
                }
                Photo photo = itemModel.getTips().get(0).getUser().getPhoto();
                venuePicUrl = new StringBuilder();
                if(!TextUtils.isEmpty(photo.getPrefix()) && !TextUtils.isEmpty(photo.getSuffix())){
                    venuePicUrl.append(photo.getPrefix()).append("100x100").append(photo.getSuffix());
                }
            }
            if(!TextUtils.isEmpty(venuePicUrl)) {
                Glide.with(this).load(venuePicUrl).into(ivVenuImage);
            }
            if(itemModel.getVenue()!=null && itemModel.getVenue().getPrice()!=null){
                if(!TextUtils.isEmpty(itemModel.getVenue().getPrice().getMessage())){
                    tvAvgVenueCost.setText(itemModel.getVenue().getPrice().getMessage());
                }else{
                    tvAvgVenueCost.setText(R.string.not_available);
                }
            }else{
                tvAvgVenueCost.setText(R.string.not_available);
            }

            if(itemModel.getVenue()!=null && itemModel.getVenue().getHours()!=null){
                if(!TextUtils.isEmpty(itemModel.getVenue().getHours().getStatus())){
                    tvTimeOfVenue.setText(itemModel.getVenue().getHours().getStatus());
                }else{
                    tvTimeOfVenue.setText(R.string.timings_n_a);
                }
            }else{
                tvTimeOfVenue.setText(R.string.timings_n_a);
            }
        }
    }

    @OnClick(R.id.ll_personalized_review_for_venue)
    public void setUpClickListenerOnPersonalzedUserReview(View view){
         setUpMaterialDialog(view);
    }

    private void setUpResources() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        databaseRequestDao = ResturantsDatabase.create(this).getDatabaseRequestDao();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_venue_info);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
       new CustomAysncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AsyncTaskKeysEnum.REFRESH_REVIEWS_ASYNC.getKeyCode());

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
        if(!TextUtils.isEmpty(venueUrl)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ConstantUtill.DEFAULT_VENUE_INTENT_ACTION + venueUrl));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(itemModel !=null && itemModel.getVenue()!=null && itemModel.getVenue().getLocation()!=null) {
            Venue venueModel = itemModel.getVenue();
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


    class CustomAysncTask extends AsyncTask<Integer,Void,String>{

        private String inputReviewText;

        public CustomAysncTask setInputReviewText(String inputReviewText) {
            this.inputReviewText = inputReviewText;
            return this;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            if(integers[0] == AsyncTaskKeysEnum.SET_REVIEW_ASYNC.getKeyCode()){
                String updatedText = inputReviewText;
                if(itemModel!=null && itemModel.getVenue()!=null && !TextUtils.isEmpty(updatedText)){
                    UserReviewEntity userReviewEntity = databaseRequestDao.getUserReviewFromVenueId(itemModel.getVenue().getId());
                    if(userReviewEntity!=null){
                        userReviewEntity.setUserReview(updatedText);
                        databaseRequestDao.updateUserReview(userReviewEntity);
                    }else{
                        databaseRequestDao.insertUserReview(new UserReviewEntity(itemModel.getVenue().getId(),updatedText));
                    }

                }
            }else if(integers[0] == AsyncTaskKeysEnum.REFRESH_REVIEWS_ASYNC.getKeyCode()){
                UserReviewEntity userReviewEntity = databaseRequestDao.getUserReviewFromVenueId(itemModel.getVenue().getId());
                if(userReviewEntity!=null){
                    return userReviewEntity.getUserReview();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!TextUtils.isEmpty(s)){
                tvPersonalizedReview.setText(s);
            }
        }
    }

  enum AsyncTaskKeysEnum{
        REFRESH_REVIEWS_ASYNC(1),SET_REVIEW_ASYNC(2);

      private int keyCode;

      AsyncTaskKeysEnum(int i) {
          keyCode = i;
      }

      public int getKeyCode() {
          return keyCode;
      }
  }
}
