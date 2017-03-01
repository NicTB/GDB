package com.example.nicta.gdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {

    @Expose @SerializedName("id")
    public int id;
    @Expose @SerializedName("nom")
    public String nom;

}
