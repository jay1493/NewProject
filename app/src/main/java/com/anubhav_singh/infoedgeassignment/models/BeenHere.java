
package com.anubhav_singh.infoedgeassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BeenHere implements Serializable{

    @SerializedName("lastCheckinExpiredAt")
    @Expose
    private Integer lastCheckinExpiredAt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BeenHere() {
    }

    /**
     * 
     * @param lastCheckinExpiredAt
     */
    public BeenHere(Integer lastCheckinExpiredAt) {
        super();
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

    public Integer getLastCheckinExpiredAt() {
        return lastCheckinExpiredAt;
    }

    public void setLastCheckinExpiredAt(Integer lastCheckinExpiredAt) {
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

}
