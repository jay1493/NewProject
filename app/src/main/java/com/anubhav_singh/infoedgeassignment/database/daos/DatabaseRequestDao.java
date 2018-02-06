package com.anubhav_singh.infoedgeassignment.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.anubhav_singh.infoedgeassignment.database.entities.LocationEntity;
import com.anubhav_singh.infoedgeassignment.models.Venue;

import java.util.List;

import javax.sql.DataSource;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

@Dao
public interface DatabaseRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVenues(List<Venue> venues);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentLocation(LocationEntity locationEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateVenues(Venue... venues);

    @Query("SELECT * FROM venue WHERE lat = :latitude AND lng = :longitude")
    android.arch.paging.DataSource.Factory<Integer,Venue> getVenue(double latitude, double longitude);
    @Query("SELECT * FROM venue")
    android.arch.paging.DataSource.Factory<Integer,Venue> getVenues();
    @Query("SELECT * FROM current_active_location")
    LocationEntity getSavedLocation();
    @Query("SELECT * FROM venue WHERE id LIKE :venueId")
    Venue getVenueFromVenueId(String venueId);

    @Query("DELETE FROM venue")
    void deleteVenues();
    @Query("DELETE FROM current_active_location")
    void deleteCurrentLocation();

}
