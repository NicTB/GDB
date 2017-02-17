package com.example.nicta.gdb;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by nicta on 2017-02-17.
 */

public interface JsonService {
/*
    @GET("/mobile/GetEventsMobile/")
    void listEvents(Callback<List<>> eventsCallback);

    @GET("/mobile/ListeProfs")
    void listProfs(Callback<List<>> callback);

    @GET("/mobile/Listelocaux")
    void listLocaux(Callback<List<>> locauxCallback);

    @GET("/mobile/ListeEtudiantsPublic")
    void listEtudiant(Callback<List<>> etudiantsCallback);

    @GET("/mobile/Listelocaux")
    void listLocalH(Callback<List<>> localHCallback);

    @GET("/mobile/GetEventsLocal")
    void listEventsLocal(Callback<List<>> eventsLocalCallback);

    @GET("/mobile/TriLocaux")
    void triLocaux(Callback<List<>> localHCallback);
*/

    @GET("/mobile/GetCartes")
    void getCartes(Callback<List<Carte>> cartesCallback);

}