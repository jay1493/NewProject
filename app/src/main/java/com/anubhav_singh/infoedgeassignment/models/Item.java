
package com.anubhav_singh.infoedgeassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable{

    @SerializedName("unreadCount")
    @Expose
    private Integer unreadCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param unreadCount
     */
    public Item(Integer unreadCount) {
        super();
        this.unreadCount = unreadCount;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

}
