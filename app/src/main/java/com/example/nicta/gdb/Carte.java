package com.example.nicta.gdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by Nicolas on 16/02/2017.
 */

public class Carte{

    @Expose @SerializedName("id")
    public int id;
    @Expose @SerializedName("nom")
    public String nom;
    @Expose @SerializedName("type")
    public int type;
    @Expose @SerializedName("faction")
    public int faction;
    @Expose @SerializedName("position")
    public int position;
    @Expose @SerializedName("loyaute")
    public int loyaute;
    @Expose @SerializedName("capacite")
    public String capacite ;
    @Expose @SerializedName("image")
    public String image ;
    @Expose @SerializedName("creeCarte")
    public boolean creeCarte ;
    @Expose @SerializedName("jouable")
    public boolean jouable;
    @Expose @SerializedName("coutCreation")
    public int coutCreation;


    public boolean isEqual(int carteId){
        return carteId == id;
    }



}
