package com.anubhav_singh.infoedgeassignment.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.anubhav_singh.infoedgeassignment.constants.ConstantUtill;
import com.anubhav_singh.infoedgeassignment.database.daos.DatabaseRequestDao;
import com.anubhav_singh.infoedgeassignment.database.entities.LocationEntity;
import com.anubhav_singh.infoedgeassignment.database.typeConverters.CategoryListConverter;
import com.anubhav_singh.infoedgeassignment.models.Item;
import com.anubhav_singh.infoedgeassignment.models.Venue;

/**
 * Created by Anubhav-Singh on 05-02-2018.
 */

@Database(entities = {Item.class, LocationEntity.class}, version = 1)
@TypeConverters({CategoryListConverter.class})
public abstract class ResturantsDatabase extends RoomDatabase{

      public abstract DatabaseRequestDao getDatabaseRequestDao();
      public static ResturantsDatabase sInstance;

    public static synchronized ResturantsDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    public static ResturantsDatabase create(Context context) {
        RoomDatabase.Builder<ResturantsDatabase> builder =    Room.databaseBuilder(context.getApplicationContext(),
                ResturantsDatabase.class,
                ConstantUtill.VENUE_TABLE_NAME).allowMainThreadQueries();
        return builder.build();
    }

}
