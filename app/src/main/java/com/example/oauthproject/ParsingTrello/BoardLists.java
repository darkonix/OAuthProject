package com.example.oauthproject.ParsingTrello;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoardLists {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lists")
    @Expose
    private List<ListObj> lists = null;

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

    public List<ListObj> getLists() {
        return lists;
    }

    public void setLists(List<ListObj> lists) {
        this.lists = lists;
    }
}
