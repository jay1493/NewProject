package com.anubhav_singh.infoedgeassignment.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

@Entity(tableName = "current_active_location")
public class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    private long primaryKey;

    private Double latitude;
    private Double longitude;

    public long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
