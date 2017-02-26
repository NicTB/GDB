package com.example.nicta.gdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by nicta on 2017-02-26.
 */

public class MyViewHolderCarteDeck extends RecyclerView.ViewHolder {

    public ImageView imageCarte;
    public Carte carte;
    private GestionDeck gd;
    public DeckBuilderActivity deckBuilderActivity;

    public MyViewHolderCarteDeck(View v, DeckBuilderActivity dba) {
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
        gd.getDeckSelectionne().RetirerCarte(carte);
        deckBuilderActivity.rafraichirDeck();
    }

}