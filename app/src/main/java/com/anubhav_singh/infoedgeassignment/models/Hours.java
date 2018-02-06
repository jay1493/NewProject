
package com.anubhav_singh.infoedgeassignment.models;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hours implements Serializable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("richStatus")
    @Expose
    @Ignore
    private RichStatus richStatus;
    @SerializedName("isOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("isLocalHoliday")
    @Expose
    private Boolean isLocalHoliday;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RichStatus getRichStatus() {
        return richStatus;
    }

    public void setRichStatus(RichStatus richStatus) {
        this.richStatus = richStatus;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getIsLocalHoliday() {
        return isLocalHoliday;
    }

    public void setIsLocalHoliday(Boolean isLocalHoliday) {
        this.isLocalHoliday = isLocalHoliday;
    }

}
