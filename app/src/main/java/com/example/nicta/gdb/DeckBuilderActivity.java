package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DeckBuilderActivity extends AppCompatActivity {

    RecyclerView listeCartesFiltre;
    RecyclerView listeCartesMelee;
    RecyclerView listeCartesRange;
    RecyclerView listeCartesSiege;
    RecyclerView listeCartesEvent;
    ArrayList<Carte> cartesFaction;
    ArrayList<Carte> cartesFiltrees;
    ArrayList<Carte> cartesMelee;
    ArrayList<Carte> cartesRange;
    ArrayList<Carte> cartesSiege;
    ArrayList<Carte> cartesEvent;
    GestionDeck gd;
    FournisseurCartes fc;
    Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_builder);
        fc = FournisseurCartes.getInstance();
        gd = GestionDeck.getInstance();
        deck = gd.getDeckSelectionne();

        getCartesFaction();
        filtrerCartes();

        listeCartesFiltre = (RecyclerView) findViewById(R.id.listeCartesFiltre);
        listeCartesMelee = (RecyclerView) findViewById(R.id.listeCartesMelee);
        listeCartesRange = (RecyclerView) findViewById(R.id.listeCartesRange);
        listeCartesSiege = (RecyclerView) findViewById(R.id.listeCartesSiege);
        listeCartesEvent = (RecyclerView) findViewById(R.id.listeCartesEvent);

        afficherCartesFiltrees();
        rafraichirDeck();
    }

    @Override
    public void onResume(){
        super.onResume();
        rafraichirDeck();
    }

    private void afficherCartesFiltrees(){
        // Affichage des cartes dans la RecyclerView
        listeCartesFiltre.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (cartesFiltrees != null & listeCartesFiltre != null) {
            listeCartesFiltre.setAdapter(new DeckBuilderAdapter(cartesFiltrees, getBaseContext(), this));
        }
        listeCartesFiltre.setLayoutManager(MyLayoutManager);
    }

    private void getCartesFaction(){
        ArrayList<Carte> cartes = fc.getCartes();
        cartesFaction = new ArrayList<>();
        for(Carte c : cartes){
            if(deck.getFaction() == c.faction || c.faction == 0){
                cartesFaction.add(c);
            }
        }
    }

    private void filtrerCartes(){
        cartesFiltrees = new ArrayList<>();
        cartesFiltrees.addAll(cartesFaction);
    }

    protected void rafraichirDeck(){
        deck = gd.getDeckSelectionne();
        rafraichirListeParRange();
        // Affichage des cartes dans les RecyclerView du deck
        listeCartesMelee.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerMelee = new LinearLayoutManager(this);
        MyLayoutManagerMelee.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (cartesMelee != null  & listeCartesMelee != null) {
            listeCartesMelee.setAdapter(new CarteDeckAdapter(cartesMelee, getBaseContext(), this));
        }
        listeCartesMelee.setLayoutManager(MyLayoutManagerMelee);

        // Affichage des cartes dans les RecyclerView du deck
        listeCartesRange.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerRange = new LinearLayoutManager(this);
        MyLayoutManagerRange.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (cartesRange != null  & listeCartesRange != null) {
            listeCartesRange.setAdapter(new CarteDeckAdapter(cartesRange, getBaseContext(), this));
        }
        listeCartesRange.setLayoutManager(MyLayoutManagerRange);

        // Affichage des cartes dans les RecyclerView du deck
        listeCartesSiege.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerSiege = new LinearLayoutManager(this);
        MyLayoutManagerSiege.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (cartesSiege != null & listeCartesSiege != null) {
            listeCartesSiege.setAdapter(new CarteDeckAdapter(cartesSiege, getBaseContext(), this));
        }
        listeCartesSiege.setLayoutManager(MyLayoutManagerSiege);

        // Affichage des cartes dans les RecyclerView du deck
        listeCartesEvent.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerEvent = new LinearLayoutManager(this);
        MyLayoutManagerEvent.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (cartesEvent != null & listeCartesEvent != null) {
            listeCartesEvent.setAdapter(new CarteDeckAdapter(cartesEvent, getBaseContext(), this));
        }
        listeCartesEvent.setLayoutManager(MyLayoutManagerEvent);
    }

    private void rafraichirListeParRange(){
        cartesMelee = new ArrayList<>();
        cartesRange = new ArrayList<>();
        cartesSiege = new ArrayList<>();
        cartesEvent = new ArrayList<>();

        ArrayList<Carte> cartes = deck.getCartesDeck();
        for(Carte c : cartes){
            switch(c.position){
                case 0:
                    cartesMelee.add(c);
                    break;
                case 1:
                    cartesRange.add(c);
                    break;
                case 2:
                    cartesSiege.add(c);
                    break;
                default:
                    cartesEvent.add(c);
                    break;
            }
        }

    }

}
