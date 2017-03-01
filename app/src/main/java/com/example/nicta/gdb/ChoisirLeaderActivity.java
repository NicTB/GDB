package com.example.nicta.gdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChoisirLeaderActivity extends AppCompatActivity {

    // Choix d'un leader lors de la création d'un deck

    ChoisirLeaderAdapter adapter;
    ListView listeChoixLeader;
    FournisseurCartes fc;
    GestionDeck gd;
    GdbBDD gdbBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choisir_leader);
        fc = FournisseurCartes.getInstance();
        gd = GestionDeck.getInstance();
        listeChoixLeader = (ListView) findViewById(R.id.listeChoixLeader);

        // Cherche la faction du nouveau deck
        int faction = gd.getDeckSelectionne().getFaction();
        ArrayList<Carte> leaderFactionSelect = new ArrayList<>();
        for(Carte c : fc.getLeaders()){ // De tous les leaders
            if(c.faction == faction){ // Prend ceux qui font partie de la faction sélectionnée
                leaderFactionSelect.add(c);
            }
        }

        adapter = new ChoisirLeaderAdapter(getBaseContext(),R.layout.choisir_faction,leaderFactionSelect);
        listeChoixLeader.setAdapter(adapter);

        listeChoixLeader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Carte leader = (Carte)adapterView.getItemAtPosition(i);

                // On prend le nouveau deck, qu'on a commencé à assembler dans l'activité ChoisirFaction
                Deck deck = gd.getDeckSelectionne();
                // On y ajoute le leader
                deck.setLeader(leader);
                // Puis on insère le nouveau deck dans notre base SQLite
                gdbBDD = new GdbBDD(getBaseContext());
                gdbBDD.open();
                long nbr = gdbBDD.insertDeck(deck); // Retourne l'id du deck
                gdbBDD.close();

                deck.setId((int)nbr);
                gd.setDeckSelectionne(deck);

                // Après avoir choisi le leader, on peut commencer à ajouter des cartes au deck
                Intent intent = new Intent(ChoisirLeaderActivity.this, DeckBuilderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
