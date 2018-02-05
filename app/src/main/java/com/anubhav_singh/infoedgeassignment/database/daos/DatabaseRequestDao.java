package com.anubhav_singh.infoedgeassignment.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.anubhav_singh.infoedgeassignment.models.Venue;

import java.util.List;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

@Dao
public interface DatabaseRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVenues(List<Venue> venues);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateVenues(Venue... venues);

    @Query("SELECT * FROM venue WHERE lat = :latitude AND lng = :longitude")
    List<Venue> getVenues(double latitude, double longitude);

    @Query("DELETE FROM venue")
    void deleteVenues();

}
