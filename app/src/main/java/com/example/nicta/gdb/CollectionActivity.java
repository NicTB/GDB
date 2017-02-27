package com.example.nicta.gdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CollectionActivity extends AppCompatActivity {

    private ArrayList<Carte> cartesAAfficher;
    CollectionAdapter adapter;
    private ListView listeCartes;
    SearchView chercherCarte;
    FournisseurCartes fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        fc = FournisseurCartes.getInstance();
        checkListesFiltre();

        Button btnFiltre = (Button) findViewById(R.id.btnFiltre);
        listeCartes = (ListView) findViewById(R.id.listeCarte);
        chercherCarte = (SearchView) findViewById(R.id.chercherCarte);

        chercherCarte.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrerListeCartesAAfficher();
                ArrayList<Carte> temp = new ArrayList<Carte>();
                for (Carte c : cartesAAfficher) { // Cherche le nom dans les cartes affichées présentement (tient compte des filtres)
                    if (c.nom.toLowerCase().contains(query.toLowerCase())) {
                        temp.add(c);
                    }
                }

                cartesAAfficher.clear();
                cartesAAfficher.addAll(temp);
                rafraichirListe();
                chercherCarte.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
        filtrerListeCartesAAfficher();
        rafraichirListe();
    }

    @Override
    protected void onResume(){
        super.onResume();
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
        if(count<4) { // S'il y a 4 faux, ne filtre pas la rareté{
            if (!fc.filtreRarete.get(0)) { // Carte créées et communes
                for (Carte c : cartesAAfficher) {
                    if (c.coutCreation <= 30) {
                        temp.add(c);
                    }
                }
            }
            if (!fc.filtreRarete.get(1)) { // Cartes rares
                for (Carte c : cartesAAfficher) {
                    if (c.coutCreation == 80) {
                        temp.add(c);
                    }
                }
            }
            if (!fc.filtreRarete.get(2)) { // Cartes épiques
                for (Carte c : cartesAAfficher) {
                    if (c.coutCreation == 200) {
                        temp.add(c);
                    }
                }
            }
            if (!fc.filtreRarete.get(3)) { // Cartes légendaires
                for (Carte c : cartesAAfficher) {
                    if (c.coutCreation == 800) {
                        temp.add(c);
                    }
                }
            }

            cartesAAfficher.removeAll(temp);
        }
    }

}
