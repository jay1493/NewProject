package com.anubhav_singh.infoedgeassignment.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.database.typeConverters.CategoryListConverter;
import com.anubhav_singh.infoedgeassignment.models.Venue;

/**
 * Created by Anubhav-Singh on 05-02-2018.
 */

@Database(entities = {Venue.class}, version = 1)
@TypeConverters({CategoryListConverter.class})
public abstract class ResturantsDatabase extends RoomDatabase{

      public abstract DatabaseRequestDao getDatabaseRequestDao();

}
