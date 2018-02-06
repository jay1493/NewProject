
package com.anubhav_singh.infoedgeassignment.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entity implements Serializable{

    @SerializedName("indices")
    @Expose
    private List<Integer> indices = null;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
