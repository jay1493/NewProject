
package com.anubhav_singh.infoedgeassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LabeledLatLng implements Serializable{

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LabeledLatLng() {
    }

    /**
     * 
     * @param lng
     * @param label
     * @param lat
     */
    public LabeledLatLng(String label, Double lat, Double lng) {
        super();
        this.label = label;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
