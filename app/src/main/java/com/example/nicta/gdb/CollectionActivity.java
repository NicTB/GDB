package com.example.nicta.gdb;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class CollectionActivity extends AppCompatActivity {

    private ArrayList<Carte> cartes = new ArrayList<Carte>();
    private ArrayList<Carte> cartesAAfficher = new ArrayList<Carte>();
    CollectionAdapter adapter;
    private ListView listeCartes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Button btnFiltre = (Button) findViewById(R.id.btnFiltre);
        Button btnChercher = (Button) findViewById(R.id.btnChercher);
        listeCartes = (ListView) findViewById(R.id.listeCarte);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                .setClient(new OkClient())
                .build();

        JsonService jsonService = restAdapter.create(JsonService.class);

        Callback<List<Carte>> callback = new Callback<List<Carte>>() {
            @Override
            public void success(List<Carte> liste, Response response) {
                cartes.clear();
                for (Carte c : liste) {
                    cartes.add(c);
                    cartesAAfficher.add(c);
                }

                rafraichirListe();
            }

            @Override
            public void failure(RetrofitError error) {
                String errorString = error.toString();

            }
        };

        jsonService.getCartes(callback);


        /*
        btnFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carte c1 = new Carte("adrenaline_rush");
                AfficherCarteActivity cdd = new AfficherCarteActivity(CollectionActivity.this, c1);
                cdd.show();
            }
        });

        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carte c2 = new Carte("aeromancy");
                AfficherCarteActivity cdd = new AfficherCarteActivity(CollectionActivity.this, c2);
                cdd.show();
            }
        });*/

    }

    protected void rafraichirListe(){
        adapter = new CollectionAdapter(getBaseContext(),R.layout.collection_carte, cartesAAfficher);
        listeCartes.setAdapter(adapter);
    }

}
