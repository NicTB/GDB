package com.example.nicta.gdb;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by nicta on 2017-02-17.
 */

public interface JsonService {

    @GET("/mobile/GetCartes")
    void getCartes(Callback<List<Carte>> cartesCallback);

    @GET("/mobile/GetTags")
    void getTags(Callback<List<Tags>> tagsCallback);

    @GET("/mobile/GetTagsCartes")
    void getTagCarte(Callback<List<TagCarte>> tagCarteCallback);


}