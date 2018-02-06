
package com.anubhav_singh.infoedgeassignment.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.io.Serializable;
import java.util.List;

import com.anubhav_singh.infoedgeassignment.database.typeConverters.CategoryListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "venue")
public class Venue implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long venueUniqueId;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    @Ignore
    private Contact contact;
    @SerializedName("location")
    @Expose
    @Embedded
    private Location location;
    @SerializedName("categories")
    @Expose
    @Nullable
    @TypeConverters(CategoryListConverter.class)
    private List<Category> categories = null;
    @SerializedName("verified")
    @Expose
    @Ignore
    private Boolean verified;
    @SerializedName("stats")
    @Expose
    @Ignore
    private Stats stats;
    @SerializedName("allowMenuUrlEdit")
    @Expose
    @Ignore
    private Boolean allowMenuUrlEdit;
    @SerializedName("beenHere")
    @Expose
    @Ignore
    private BeenHere beenHere;
    @SerializedName("specials")
    @Expose
    @Ignore
    private Specials specials;
    @SerializedName("hereNow")
    @Expose
    @Ignore
    private HereNow hereNow;
    @SerializedName("referralId")
    @Expose
    @Nullable
    private String referralId;
    @SerializedName("venueChains")
    @Expose
    @Ignore
    private List<Object> venueChains = null;
    @SerializedName("hasPerk")
    @Expose
    @Ignore
    private Boolean hasPerk;
    @SerializedName("venueRatingBlacklisted")
    @Expose
    @Ignore
    private Boolean venueRatingBlacklisted;

    @Nullable
    private String userRemarks;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Venue() {
    }

    /**
     * 
     * @param venueChains
     * @param location
     * @param stats
     * @param allowMenuUrlEdit
     * @param hereNow
     * @param contact
     * @param specials
     * @param id
     * @param referralId
     * @param venueRatingBlacklisted
     * @param verified
     * @param name
     * @param categories
     * @param beenHere
     * @param hasPerk
     */
    public Venue(String id, String name, Contact contact, Location location, List<Category> categories, Boolean verified, Stats stats, Boolean allowMenuUrlEdit, BeenHere beenHere, Specials specials, HereNow hereNow, String referralId, List<Object> venueChains, Boolean hasPerk, Boolean venueRatingBlacklisted) {
        super();
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.categories = categories;
        this.verified = verified;
        this.stats = stats;
        this.allowMenuUrlEdit = allowMenuUrlEdit;
        this.beenHere = beenHere;
        this.specials = specials;
        this.hereNow = hereNow;
        this.referralId = referralId;
        this.venueChains = venueChains;
        this.hasPerk = hasPerk;
        this.venueRatingBlacklisted = venueRatingBlacklisted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Nullable
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(@Nullable List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Nullable
    public Stats getStats() {
        return stats;
    }

    public void setStats(@Nullable Stats stats) {
        this.stats = stats;
    }

    public Boolean getAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }

    public void setAllowMenuUrlEdit(Boolean allowMenuUrlEdit) {
        this.allowMenuUrlEdit = allowMenuUrlEdit;
    }

    public BeenHere getBeenHere() {
        return beenHere;
    }

    public void setBeenHere(BeenHere beenHere) {
        this.beenHere = beenHere;
    }

    public Specials getSpecials() {
        return specials;
    }

    public void setSpecials(Specials specials) {
        this.specials = specials;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public void setHereNow(HereNow hereNow) {
        this.hereNow = hereNow;
    }

    @Nullable
    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(@Nullable String referralId) {
        this.referralId = referralId;
    }

    @Nullable
    public List<Object> getVenueChains() {
        return venueChains;
    }

    public void setVenueChains(@Nullable List<Object> venueChains) {
        this.venueChains = venueChains;
    }

    @Nullable
    public Boolean getHasPerk() {
        return hasPerk;
    }

    public void setHasPerk(@Nullable Boolean hasPerk) {
        this.hasPerk = hasPerk;
    }

    @Nullable
    public Boolean getVenueRatingBlacklisted() {
        return venueRatingBlacklisted;
    }

    public void setVenueRatingBlacklisted(@Nullable Boolean venueRatingBlacklisted) {
        this.venueRatingBlacklisted = venueRatingBlacklisted;
    }

    @Nullable
    public String getUserRemarks() {
        return userRemarks;
    }

    public void setUserRemarks(@Nullable String userRemarks) {
        this.userRemarks = userRemarks;
    }

    public long getVenueUniqueId() {
        return venueUniqueId;
    }

    public void setVenueUniqueId(long venueUniqueId) {
        this.venueUniqueId = venueUniqueId;
    }

    public static DiffCallback<Venue> DIFF_CALLBACK = new DiffCallback<Venue>() {
        @Override
        public boolean areItemsTheSame(@NonNull Venue oldItem, @NonNull Venue newItem) {
            return oldItem.getVenueUniqueId() == newItem.getVenueUniqueId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Venue oldItem, @NonNull Venue newItem) {
            return oldItem.checkContents(newItem);
        }
    };

    private boolean checkContents(Venue newItem) {
        return this.equals(newItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        if (id != null ? !id.equals(venue.id) : venue.id != null) return false;
        if (name != null ? !name.equals(venue.name) : venue.name != null) return false;
        if (location != null ? !location.equals(venue.location) : venue.location != null)
            return false;
        if (categories != null ? !categories.equals(venue.categories) : venue.categories != null)
            return false;
        return userRemarks != null ? userRemarks.equals(venue.userRemarks) : venue.userRemarks == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (userRemarks != null ? userRemarks.hashCode() : 0);
        return result;
    }
}
