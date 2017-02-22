package com.example.nicta.gdb;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by nicta on 2017-02-20.
 */

public class FournisseurCartes {

    private FournisseurCartes(){}
    private static FournisseurCartes FC;
    public static FournisseurCartes getInstance(){
        if(FC == null)
            FC = new FournisseurCartes();
        return FC;
    }
    private Context context;
    private ArrayList<Carte> cartes = new ArrayList<Carte>();
    private ArrayList<Tags> tags = new ArrayList<Tags>();
    private ArrayList<TagCarte> tagCarte = new ArrayList<TagCarte>();

    public void chercherCartes() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                .setClient(new OkClient())
                .build();

        JsonService jsonService = restAdapter.create(JsonService.class);

        Callback<List<Carte>> callbackCarte = new Callback<List<Carte>>() {
            @Override
            public void success(List<Carte> liste, Response response) {
                cartes.clear();
                for (Carte c : liste) {
                    cartes.add(c);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorString = error.toString();
            }
        };

        Callback<List<Tags>> callbackTag = new Callback<List<Tags>>() {
            @Override
            public void success(List<Tags> liste, Response response) {
                tags.clear();
                for (Tags t : liste) {
                    tags.add(t);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorString = error.toString();

            }
        };

        Callback<List<TagCarte>> callbackTagCarte = new Callback<List<TagCarte>>() {
            @Override
            public void success(List<TagCarte> liste, Response response) {
                tagCarte.clear();
                for (TagCarte t : liste) {
                    tagCarte.add(t);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String errorString = error.toString();

            }
        };

        jsonService.getCartes(callbackCarte);
        jsonService.getTags(callbackTag);
        jsonService.getTagCarte(callbackTagCarte);
    }

    public ArrayList<Carte> getCartes(){
        return cartes;
    }
    public ArrayList<Tags> getTags(){
        return tags;
    }
    public ArrayList<TagCarte> getTagCarte(){
        return tagCarte;
    }

    public int filtreFaction;
    public int filtreTag;
    public ArrayList<Boolean> filtreRarete;
    public ArrayList<Boolean> filtreType;


}
