package com.example.nicta.gdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class CollectionActivity extends AppCompatActivity {


    // Activité pour la Collection de cartes

    private ArrayList<Carte> cartesAAfficher;
    CollectionAdapter adapter;
    private ListView listeCartes;
    SearchView chercherCarte;
    FournisseurCartes fc;
    Button btnFiltre;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        fc = FournisseurCartes.getInstance();
        // Initialise la liste des filtres (ils se trouvent dans la classe FournisseurCartes)
        checkListesFiltre();

        btnFiltre = (Button) findViewById(R.id.btnFiltre);
        listeCartes = (ListView) findViewById(R.id.listeCarte);
        chercherCarte = (SearchView) findViewById(R.id.chercherCarte);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        if(fc.getCartes().size()==0){ // Si on n'a pas de cartes chargées, on va les chercher
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                    .setClient(new OkClient())
                    .build();

            JsonService jsonService = restAdapter.create(JsonService.class);

            Callback<List<Carte>> callbackCarte = new Callback<List<Carte>>() {
                @Override
                public void success(List<Carte> liste, Response response) {
                    fc.cartes.clear();
                    fc.leaders.clear();
                    for (Carte c : liste) {
                        fc.cartes.add(c);
                        if(c.type == 3){
                            fc.leaders.add(c);
                        }
                    }
                    filtrerListeCartesAAfficher();
                    rafraichirListe();
                    // On enlève la barre de chargement
                    progressBar1.setVisibility(View.GONE);
                }
                @Override
                public void failure(RetrofitError error) {
                    String errorString = error.toString();
                }
            };
            jsonService.getCartes(callbackCarte);
            // On indique à l'utilisateur qu'on charge les cartes
            progressBar1.setVisibility(View.VISIBLE);
        }
        else{ // S'il y a des cartes, on les affiche
            filtrerListeCartesAAfficher();
            rafraichirListe();
            progressBar1.setVisibility(View.GONE);
        }

        // SearchView
        chercherCarte.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // Quand on appuie sur Enter
                // On filtre les cartes à afficher
                filtrerListeCartesAAfficher();
                ArrayList<Carte> temp = new ArrayList<Carte>();
                for (Carte c : cartesAAfficher) { // Cherche le nom dans les cartes affichées présentement (tient compte des filtres)
                    if (!c.nom.toLowerCase().contains(query.toLowerCase())) {
                        temp.remove(c); // On enlève la carte si elle ne contient pas ce que l'on cherche
                    }
                }

                cartesAAfficher.removeAll(temp);

                // On réaffiche la liste des cartes
                rafraichirListe();
                // clearFocus permet de fermer le clavier automatiquement
                chercherCarte.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Si la SearchView est vide, on affiche toutes les cartes filtrées
                if(newText.length()==0){
                    filtrerListeCartesAAfficher();
                    rafraichirListe();
                }

                return false;
            }
        });

        btnFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionActivity.this,FiltreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fc.getCartes().size() > 0) {
            filtrerListeCartesAAfficher();
            rafraichirListe();
        }
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
        if(fc.filtreRarete==null){
            fc.filtreRarete = new ArrayList<>();
            fc.filtreRarete.add(true);
            fc.filtreRarete.add(true);
            fc.filtreRarete.add(true);
            fc.filtreRarete.add(true);
        }
        if(fc.filtreType==null){
            fc.filtreType = new ArrayList<>();
            fc.filtreType.add(true);
            fc.filtreType.add(true);
            fc.filtreType.add(true);
            fc.filtreType.add(true);
        }
    }

    protected void filtrerListeCartesAAfficher(){
        cartesAAfficher = new ArrayList<>();
        ArrayList<Carte> cartes = fc.getCartes();

        /**********************   FILTRE PAR TAG     ************************/

        if(fc.filtreTag>0) { // Si un tag à été sélectionné
            ArrayList<TagCarte> tagCartes = fc.getTagCarte();
            ArrayList<Tags> tags = fc.getTags();
            ArrayList<Integer> idsCarte = new ArrayList<>();
            Tags t = tags.get(fc.filtreTag-1); // Trouve le tag
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
        if(fc.filtreFaction>0){ // Si une faction à été sélectionnée
            temp = new ArrayList<>();
            for(Carte c : cartesAAfficher){ // On itère sur les cartes restantes
                if(fc.filtreFaction-1 != c.faction){ // On ne veut pas celles qui ne font pas partie de la faction
                    temp.add(c); // on met les cartes dans une autre liste pour les enlever plus facilement (on ne peut pas directement dans la boucle foreach)
                }
            }
            cartesAAfficher.removeAll(temp); // on enlève les cartes qui ne font pas partie de la faction sélectionnée
        }

        /**********************   FILTRE PAR TYPE     ************************/

        temp.clear();
        int count = 0;
        for(boolean b : fc.filtreType){
            if(!b)
                count++; // Compte le nombre de faux
        }
        if(count<4) { // S'il y a 4 faux, ne filtre pas le type
            for (int i = 0; i < 4; i++) {
                if (!fc.filtreType.get(i)) {
                    count++;
                    for (Carte c : cartesAAfficher) {
                        if (c.type == i) {
                            temp.add(c);
                        }
                    }
                }
            }
            cartesAAfficher.removeAll(temp);
        }
        /**********************   FILTRE PAR RARETÉ     ************************/

        temp.clear();
        count = 0;
        for(boolean b : fc.filtreRarete){
            if(!b)
                count++; // Compte le nombre de faux
        }
        if(count<4) { // S'il y a 4 faux, ne filtre pas la rareté

            for(Carte c : cartesAAfficher){ // Pour toutes les cartes qu'il nous reste
                switch(c.coutCreation){
                    case 30: // Si c'est une carte commune
                        if(!fc.filtreRarete.get(0)) {
                            temp.add(c); // Si on ne veut pas cette rareté, on l'enlève
                        }
                    case 80: // Si c'est une carte rare
                        if(!fc.filtreRarete.get(1)) {
                            temp.add(c);
                        }
                    case 200: // Si c'est une carte épique
                        if(!fc.filtreRarete.get(2)) {
                            temp.add(c);
                        }
                    case 800: // Si c'est une carte légendaire
                        if(!fc.filtreRarete.get(3)) {
                            temp.add(c);
                        }
                }
            }
            cartesAAfficher.removeAll(temp);
        }
    }

}
