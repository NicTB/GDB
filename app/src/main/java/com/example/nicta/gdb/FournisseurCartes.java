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

public class FournisseurCartes {

    private FournisseurCartes(){}
    private static FournisseurCartes FC;
    public static FournisseurCartes getInstance(){
        if(FC == null)
            FC = new FournisseurCartes();
        return FC;
    }
    protected ArrayList<Carte> cartes = new ArrayList<Carte>();
    protected ArrayList<Carte> leaders = new ArrayList<>();
    private ArrayList<Tags> tags = new ArrayList<Tags>();
    private ArrayList<TagCarte> tagCarte = new ArrayList<TagCarte>();
    JsonService jsonService;
    protected void chercherCartes() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                .setClient(new OkClient())
                .build();

        jsonService = restAdapter.create(JsonService.class);

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

        jsonService.getTags(callbackTag);
        jsonService.getTagCarte(callbackTagCarte);
    }

    public ArrayList<Carte> getCartes(){
        return cartes;
    }
    public ArrayList<Carte> getLeaders(){
        return leaders;
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



    public void PartagerDeck(PartageDeck partageDeck){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                .setClient(new OkClient())
                .build();

        jsonService = restAdapter.create(JsonService.class);

        Callback<Boolean> callbackPartagerDeck = new Callback<Boolean>() {
            @Override
            public void success(Boolean bool, Response response) {
                Boolean bfd = bool;
            }

            @Override
            public void failure(RetrofitError error) {
                String errorString = error.toString();

            }
        };

        jsonService.partagerDeck(partageDeck,callbackPartagerDeck);
    }

}
