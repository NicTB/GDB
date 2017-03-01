package com.example.nicta.gdb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolderCarte extends RecyclerView.ViewHolder {

    // Affichage des cartes en bas de l'activité DeckBuilder

    public ImageView imageCarte;
    public Carte carte;
    private GestionDeck gd;
    public DeckBuilderActivity deckBuilderActivity;

    public MyViewHolderCarte(View v, DeckBuilderActivity dba) {
        super(v);
        deckBuilderActivity = dba;
        gd = GestionDeck.getInstance();
        imageCarte = (ImageView) v.findViewById(R.id.imgCarte);
        imageCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSurCarte();
            }
        });
    }

    private void clickSurCarte(){
        gd.getDeckSelectionne().AjouterCarte(carte);
        deckBuilderActivity.rafraichirDeck();
    }

}
