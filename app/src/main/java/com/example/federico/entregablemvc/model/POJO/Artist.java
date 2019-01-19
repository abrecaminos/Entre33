package com.example.federico.entregablemvc.model.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("artistId")
    @Expose
    private String artistId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("Influenced_by")
    @Expose
    private String influencedBy;



    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getInfluencedBy() {
        return influencedBy;
    }

    public void setInfluencedBy(String influencedBy) {
        this.influencedBy = influencedBy;
    }



}
