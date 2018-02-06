
package com.anubhav_singh.infoedgeassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Filter implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("key")
    @Expose
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
