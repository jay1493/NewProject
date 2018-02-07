package com.anubhav_singh.infoedgeassignment.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Anubhav-Singh on 07-02-2018.
 */

@Entity(tableName = "user_reviews")
public class UserReviewEntity implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long reviewPrimaryKey;

    private String venueId;

    private String userReview;

    public UserReviewEntity(String venueId, String userReview) {
        this.venueId = venueId;
        this.userReview = userReview;
    }

    public long getReviewPrimaryKey() {
        return reviewPrimaryKey;
    }

    public void setReviewPrimaryKey(long reviewPrimaryKey) {
        this.reviewPrimaryKey = reviewPrimaryKey;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
}
