package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

public class CreationActivity extends AppCompatActivity {

    FournisseurCartes fc;
    RecyclerView listeDecks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        fc = FournisseurCartes.getInstance();
        listeDecks = (RecyclerView) findViewById(R.id.listeDecks);


        GdbBDD gdbBDD = new GdbBDD(this);


        ArrayList<Deck> decks = new ArrayList<>();

        ArrayList<Carte> cartes = fc.getCartes();

        Deck d = new Deck();
        Carte leader = cartes.get(31);
        d.setLeader(leader);
        d.setNom("Var Espion");
        decks.add(d);

        Deck d2 = new Deck();
        Carte leader2 = cartes.get(47);
        d2.setLeader(leader2);
        d2.setNom("John");
        decks.add(d2);

        Deck d3 = new Deck();
        Carte leader3 = cartes.get(31);
        d3.setLeader(leader3);
        d3.setNom("Emhyr");
        decks.add(d3);

        Deck d4 = new Deck();
        Carte leader4 = cartes.get(47);
        d4.setLeader(leader4);
        d4.setNom("Calveit");
        decks.add(d4);

        CreationAdapter ca = new CreationAdapter(decks, getBaseContext());
        listeDecks.setAdapter(ca);


    }
}
