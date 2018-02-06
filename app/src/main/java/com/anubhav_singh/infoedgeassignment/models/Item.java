
package com.anubhav_singh.infoedgeassignment.models;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.io.Serializable;
import java.util.List;

import com.anubhav_singh.infoedgeassignment.database.typeConverters.TipListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@android.arch.persistence.room.Entity(tableName = "items")
public class Item implements Serializable{

    public static DiffCallback<Item> DIFF_CALLBACK = new DiffCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getItemPrimaryKey() == newItem.getItemPrimaryKey();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    @PrimaryKey(autoGenerate = true)
    private long itemPrimaryKey;

    @SerializedName("reasons")
    @Expose
    @Ignore
    private Reasons reasons;
    @SerializedName("venue")
    @Expose
    @Embedded
    private Venue venue;
    @SerializedName("tips")
    @Expose
    @TypeConverters(TipListConverter.class)
    private List<Tip> tips = null;
    @SerializedName("referralId")
    @Expose
    @Ignore
    private String referralId;
    @SerializedName("flags")
    @Expose
    @Ignore
    private Flags flags;

    public Reasons getReasons() {
        return reasons;
    }

    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public long getItemPrimaryKey() {
        return itemPrimaryKey;
    }

    public void setItemPrimaryKey(long itemPrimaryKey) {
        this.itemPrimaryKey = itemPrimaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (venue != null ? !venue.equals(item.venue) : item.venue != null) return false;
        return tips != null ? tips.equals(item.tips) : item.tips == null;
    }

    @Override
    public int hashCode() {
        int result = venue != null ? venue.hashCode() : 0;
        result = 31 * result + (tips != null ? tips.hashCode() : 0);
        return result;
    }
}
