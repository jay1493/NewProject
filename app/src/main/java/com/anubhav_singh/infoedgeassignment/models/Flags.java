
package com.anubhav_singh.infoedgeassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Flags implements Serializable{

    @SerializedName("outsideRadius")
    @Expose
    private Boolean outsideRadius;

    public Boolean getOutsideRadius() {
        return outsideRadius;
    }

    public void setOutsideRadius(Boolean outsideRadius) {
        this.outsideRadius = outsideRadius;
    }

}
