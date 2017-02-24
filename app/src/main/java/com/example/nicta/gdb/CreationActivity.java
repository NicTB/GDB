package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CreationActivity extends AppCompatActivity {

    FournisseurCartes fc;
    RecyclerView listeDecks;
    ArrayList<Deck> decks;
    static TextView txtNomDeckSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        fc = FournisseurCartes.getInstance();
        listeDecks = (RecyclerView) findViewById(R.id.listeDecks);
        txtNomDeckSelectionne = (TextView) findViewById(R.id.txtNomDeckSelectionne);

        GdbBDD gdbBDD = new GdbBDD(this);


        decks = new ArrayList<>();

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

        // Affichage des decks dans la RecyclerView
        listeDecks.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (decks.size() > 0 & listeDecks != null) {
            listeDecks.setAdapter(new CreationAdapter(decks, getBaseContext()));

        }
        listeDecks.setLayoutManager(MyLayoutManager);

    }

    // Change l'affichage du nom du deck sélectionné
    public static void changerDeckSelectionne(){
        GestionDeck gd = GestionDeck.getInstance();
        txtNomDeckSelectionne.setText(gd.getDeckSelectionne().getNom());
    }
}
