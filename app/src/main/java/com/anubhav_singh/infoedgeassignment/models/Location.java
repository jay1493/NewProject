
package com.anubhav_singh.infoedgeassignment.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("labeledLatLngs")
    @Expose
    private List<LabeledLatLng> labeledLatLngs = null;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("crossStreet")
    @Expose
    private String crossStreet;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("formattedAddress")
    @Expose
    private List<String> formattedAddress = null;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Location() {
    }

    /**
     * 
     * @param distance
     * @param postalCode
     * @param address
     * @param labeledLatLngs
     * @param state
     * @param formattedAddress
     * @param lng
     * @param cc
     * @param lat
     * @param city
     * @param crossStreet
     * @param country
     */
    public Location(Double lat, Double lng, List<LabeledLatLng> labeledLatLngs, Integer distance, String cc, String country, String address, String crossStreet, String city, String state, List<String> formattedAddress, String postalCode) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.labeledLatLngs = labeledLatLngs;
        this.distance = distance;
        this.cc = cc;
        this.country = country;
        this.address = address;
        this.crossStreet = crossStreet;
        this.city = city;
        this.state = state;
        this.formattedAddress = formattedAddress;
        this.postalCode = postalCode;
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

    public List<LabeledLatLng> getLabeledLatLngs() {
        return labeledLatLngs;
    }

    public void setLabeledLatLngs(List<LabeledLatLng> labeledLatLngs) {
        this.labeledLatLngs = labeledLatLngs;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCrossStreet() {
        return crossStreet;
    }

    public void setCrossStreet(String crossStreet) {
        this.crossStreet = crossStreet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
