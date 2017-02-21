package com.example.nicta.gdb;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    private ArrayList<Carte> cartesAAfficher;
    CollectionAdapter adapter;
    private ListView listeCartes;
    int filtreFaction;
    int filtreTag;
    ArrayList<Boolean> filtreRarete;
    ArrayList<Boolean> filtreType;
    FournisseurCartes fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        fc = FournisseurCartes.getInstance();
        Intent intent = getIntent();
        filtreFaction = intent.getIntExtra("filtreFaction",0);
        filtreTag = intent.getIntExtra("filtreTag",0);
        filtreRarete = (ArrayList<Boolean>) intent.getSerializableExtra("filtreRarete");
        filtreType = (ArrayList<Boolean>) intent.getSerializableExtra("filtreType");
        checkListesFiltre();

        Button btnFiltre = (Button) findViewById(R.id.btnFiltre);
        Button btnChercher = (Button) findViewById(R.id.btnChercher);
        listeCartes = (ListView) findViewById(R.id.listeCarte);

        btnFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionActivity.this,FiltreActivity.class);
                intent.putExtra("filtreFaction", filtreFaction);
                intent.putExtra("filtreTag", filtreTag);
                intent.putExtra("filtreRarete", filtreRarete);
                intent.putExtra("filtreType", filtreType);
                startActivity(intent);
            }
        });

        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        filtrerListeCartesAAfficher();
        rafraichirListe();
    }

    protected void rafraichirListe(){
        adapter = new CollectionAdapter(getBaseContext(),R.layout.collection_carte, cartesAAfficher);
        listeCartes.setAdapter(adapter);

        listeCartes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Carte c = (Carte)adapterView.getItemAtPosition(i);
                AfficherCarteActivity cdd = new AfficherCarteActivity(CollectionActivity.this, c);
                cdd.show();
            }
        });
    }

    protected void checkListesFiltre(){
        if(filtreRarete==null){
            filtreRarete = new ArrayList<>();
            filtreRarete.add(true);
            filtreRarete.add(true);
            filtreRarete.add(true);
            filtreRarete.add(true);
        }
        if(filtreType==null){
            filtreType = new ArrayList<>();
            filtreType.add(true);
            filtreType.add(true);
            filtreType.add(true);
            filtreType.add(true);
        }
    }

    protected void filtrerListeCartesAAfficher(){
        cartesAAfficher = new ArrayList<>();
        ArrayList<Carte> cartes = fc.getCartes();

        /**********************   FILTRE PAR TAG     ************************/

        if(filtreTag>0) { // Si un tag à été sélectionné
            ArrayList<TagCarte> tagCartes = fc.getTagCarte();
            ArrayList<Tags> tags = fc.getTags();
            ArrayList<Integer> idsCarte = new ArrayList<>();
            Tags t = tags.get(filtreTag-1); // Trouve le tag
            for(TagCarte tc : tagCartes){
                if(tc.idTag==t.id){ //Si les id correspondent, on veut cette carte
                    idsCarte.add(tc.idCarte);
                }
            }
            for(Carte c : cartes){
                if(idsCarte.contains(c.id)){ // On ajoute les carte qu'on veut à la liste à afficher
                    cartesAAfficher.add(c);
                }
            }
        }
        else{ // S'il n'y a pas de tag sélectionné, on prend toutes les cartes
            cartesAAfficher.addAll(cartes);
        }
        ArrayList<Carte> temp = new ArrayList<>();
        /**********************   FILTRE PAR FACTION     ************************/
        if(filtreFaction>0){ // Si une faction à été sélectionnée
            temp = new ArrayList<>();
            for(Carte c : cartesAAfficher){ // On itère sur les cartes restantes
                if(filtreFaction-1 != c.faction){ // On ne veut pas celles qui ne font pas partie de la faction
                    temp.add(c); // on met les cartes dans une autre liste pour les enlever plus facilement (on ne peut pas directement dans la boucle foreach)
                }
            }
            cartesAAfficher.removeAll(temp); // on enlève les cartes qui ne font pas partie de la faction sélectionnée
        }

        /**********************   FILTRE PAR TYPE     ************************/

        temp.clear();
        for(int i = 0;i < 4;i++){
            if(!filtreType.get(i)){
                for(Carte c : cartesAAfficher){
                    if(c.type == i){
                        temp.add(c);
                    }
                }
            }
        }
        cartesAAfficher.removeAll(temp);

        /**********************   FILTRE PAR RARETÉ     ************************/

        temp.clear();
        if(!filtreRarete.get(0)){ // Carte créées et communes
            for(Carte c : cartesAAfficher){
                if(c.coutCreation <= 30 ){
                    temp.add(c);
                }
            }
        }
        if(!filtreRarete.get(1)){ // Cartes rares
            for(Carte c : cartesAAfficher){
                if(c.coutCreation == 80 ){
                    temp.add(c);
                }
            }
        }
        if(!filtreRarete.get(2)){ // Cartes épiques
            for(Carte c : cartesAAfficher){
                if(c.coutCreation == 200 ){
                    temp.add(c);
                }
            }
        }
        if(!filtreRarete.get(3)){ // Cartes légendaires
            for(Carte c : cartesAAfficher){
                if(c.coutCreation == 800 ){
                    temp.add(c);
                }
            }
        }

        cartesAAfficher.removeAll(temp);

    }

}
