package com.example.nicta.gdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChoisirLeaderActivity extends AppCompatActivity {

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

        int faction = gd.getDeckSelectionne().getFaction();
        ArrayList<Carte> leaderFactionSelect = new ArrayList<>();
        for(Carte c : fc.getLeaders()){
            if(c.faction == faction){
                leaderFactionSelect.add(c);
            }
        }

        adapter = new ChoisirLeaderAdapter(getBaseContext(),R.layout.choisir_faction,leaderFactionSelect);
        listeChoixLeader.setAdapter(adapter);

        listeChoixLeader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Carte leader = (Carte)adapterView.getItemAtPosition(i);
                Deck deck = gd.getDeckSelectionne();
                deck.setLeader(leader);
                gdbBDD = new GdbBDD(getBaseContext());
                gdbBDD.open();
                long nbr = gdbBDD.insertDeck(deck);
                gdbBDD.close();

                deck.setId((int)nbr);
                gd.setDeckSelectionne(deck);

                Intent intent = new Intent(ChoisirLeaderActivity.this, DeckBuilderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
