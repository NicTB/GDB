package com.example.nicta.gdb;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface JsonService {

    @GET("/mobile/GetCartes")
    void getCartes(Callback<List<Carte>> cartesCallback);

    @GET("/mobile/GetTags")
    void getTags(Callback<List<Tags>> tagsCallback);

    @GET("/mobile/GetTagsCartes")
    void getTagCarte(Callback<List<TagCarte>> tagCarteCallback);

    @POST("/mobile/PartagerDeck")
    void partagerDeck(@Body PartageDeck deck, Callback<Boolean> cb);

}