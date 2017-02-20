package com.example.nicta.gdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nicta on 2017-02-20.
 */

public class TagCarte {

    @Expose @SerializedName("id")
    public int id;
    @Expose @SerializedName("idCarte")
    public int idCarte;
    @Expose @SerializedName("idTag")
    public int idTag;
}
