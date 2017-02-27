package com.example.nicta.gdb;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeckBuilderActivity extends AppCompatActivity {

    RecyclerView listeCartesFiltre;
    RecyclerView listeCartesMelee;
    RecyclerView listeCartesRange;
    RecyclerView listeCartesSiege;
    RecyclerView listeCartesEvent;
    TextView txtNombreCartesDeck;
    Button btnFiltrerCartes;
    Button btnParamDeck;
    SearchView searchNom;

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
        checkListesFiltre();
        deck = gd.getDeckSelectionne();

        getCartesFaction();
        filtrerCartes();

        listeCartesFiltre = (RecyclerView) findViewById(R.id.listeCartesFiltre);
        listeCartesMelee = (RecyclerView) findViewById(R.id.listeCartesMelee);
        listeCartesRange = (RecyclerView) findViewById(R.id.listeCartesRange);
        listeCartesSiege = (RecyclerView) findViewById(R.id.listeCartesSiege);
        listeCartesEvent = (RecyclerView) findViewById(R.id.listeCartesEvent);
        txtNombreCartesDeck = (TextView) findViewById(R.id.txtNombreCartesDeck);
        btnFiltrerCartes = (Button) findViewById(R.id.btnFiltrerCartes);
        btnParamDeck = (Button) findViewById(R.id.btnParamDeck);
        searchNom = (SearchView) findViewById(R.id.searchNom);

        btnFiltrerCartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeckBuilderActivity.this, DeckFiltreActivity.class);
                startActivity(intent);
            }
        });

        btnParamDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeckBuilderActivity.this, ParamDeckActivity.class);
                startActivity(intent);
            }
        });

        searchNom.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrerCartes();
                ArrayList<Carte> temp = new ArrayList<Carte>();
                for (Carte c : cartesFiltrees) { // Cherche le nom dans les cartes affichées présentement (tient compte des filtres)
                    if (c.nom.toLowerCase().contains(query.toLowerCase())) {
                        temp.add(c);
                    }
                }

                cartesFiltrees.clear();
                cartesFiltrees.addAll(temp);
                afficherCartesFiltrees();
                searchNom.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()==0){
                    filtrerCartes();
                    afficherCartesFiltrees();
                }

                return false;
            }
        });

        afficherCartesFiltrees();
        rafraichirDeck();
    }

    @Override
    public void onResume(){
        super.onResume();
        rafraichirDeck();
        filtrerCartes();
        afficherCartesFiltrees();
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
            if(deck.getFaction() == c.faction || c.faction == 0) {
                if (c.type != Enums.Type.Leader.getValue()){
                    cartesFaction.add(c);
                }
            }
        }
    }

    private void filtrerCartes(){
        cartesFiltrees = new ArrayList<>();
        cartesFiltrees.addAll(cartesFaction);


        if(gd.filtreTag>0) { // Si un tag à été sélectionné
            ArrayList<TagCarte> tagCartes = fc.getTagCarte();
            ArrayList<Tags> tags = fc.getTags();
            ArrayList<Integer> idsCarte = new ArrayList<>();
            Tags t = tags.get(gd.filtreTag-1); // Trouve le tag
            for(TagCarte tc : tagCartes){
                if(tc.idTag==t.id){
                    idsCarte.add(tc.idCarte);
                }
            }
            for(Carte c : cartesFaction){
                if(!idsCarte.contains(c.id)){
                    cartesFiltrees.remove(c);
                }
            }
        }

        ArrayList<Carte> temp = new ArrayList<>();
        int count = 0;
        for(boolean b : gd.filtreType){
            if(!b)
                count++; // Compte le nombre de faux
        }
        if(count<3) { // S'il y a 3 faux, ne filtre pas le type
            for (int i = 0; i < 3; i++) {
                if (!gd.filtreType.get(i)) {
                    count++;
                    for (Carte c : cartesFiltrees) {
                        if (c.type == i) {
                            temp.add(c);
                        }
                    }
                }
            }
            cartesFiltrees.removeAll(temp);
        }

    }

    protected void checkListesFiltre(){
        if(gd.filtreType==null){
            gd.filtreType = new ArrayList<>();
            gd.filtreType.add(true);
            gd.filtreType.add(true);
            gd.filtreType.add(true);
        }
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

        txtNombreCartesDeck.setText(String.valueOf(deck.getCartesDeck().size()));
        if(deck.checkValide()){
            txtNombreCartesDeck.setTextColor(Color.BLUE);
        }
        else{
            txtNombreCartesDeck.setTextColor(Color.RED);
        }
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
