package com.anubhav_singh.infoedgeassignment.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.anubhav_singh.infoedgeassignment.database.entities.LocationEntity;
import com.anubhav_singh.infoedgeassignment.models.Item;
import com.anubhav_singh.infoedgeassignment.models.Venue;

import java.util.List;

import javax.sql.DataSource;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

@Dao
public interface DatabaseRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(List<Item> venues);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentLocation(LocationEntity locationEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItems(Item... items);

    @Query("SELECT * FROM items")
    android.arch.paging.DataSource.Factory<Integer,Item> getItems();
    @Query("SELECT * FROM current_active_location")
    LocationEntity getSavedLocation();
    @Query("SELECT * FROM items WHERE venueId LIKE :venueId")
    Item getVenueFromVenueId(String venueId);

    @Query("DELETE FROM items")
    void deleteItems();
    @Query("DELETE FROM current_active_location")
    void deleteCurrentLocation();

}
