
package com.example.nicta.gdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nicta.gdb.Enums.Faction;

import java.util.ArrayList;

public class ChoisirFactionActivity extends AppCompatActivity {

    // Choix de la faction lors de la création d'un nouveau deck

    ListView listeChoixFaction;
    ChoisirFactionAdapter adapter;
    ArrayList<Faction> factions;
    int nbrFactions;
    GestionDeck gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choisir_faction);
        listeChoixFaction = (ListView) findViewById(R.id.listeChoixFaction);
        gd = GestionDeck.getInstance();
        nbrFactions = Faction.values().length;
        factions = new ArrayList<>();
        for(int i=1;i<nbrFactions;i++){ // On ne tient pas compte de la première faction, car c'est les cartes neutres
            factions.add(Faction.valueOf(i));
        }

        adapter = new ChoisirFactionAdapter(getBaseContext(),R.layout.choisir_faction,factions);
        listeChoixFaction.setAdapter(adapter);

        listeChoixFaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Faction f = (Faction)adapterView.getItemAtPosition(i);
                // Crée le deck
                Deck nouvDeck = new Deck();
                nouvDeck.setFaction(f.getValue());
                nouvDeck.setNom("Nouveau deck");
                nouvDeck.setDescription("Aucune description");
                nouvDeck.setProprietaire("Moi"); // Changer Moi pour l'identifiant Google Play

                // Change le deck sélectionné pour le nouveau deck
                gd.setDeckSelectionne(nouvDeck);

                // Après avoir choisi la faction, on choisi le leader
                Intent intent = new Intent(ChoisirFactionActivity.this, ChoisirLeaderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
