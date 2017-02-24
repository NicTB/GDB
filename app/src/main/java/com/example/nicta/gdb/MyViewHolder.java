package com.example.nicta.gdb;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nicta on 2017-02-23.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView nomDeck;
    public ImageView imageDeck;
    public TextView txtNomDeckSelectionne;
    public Deck deck;
    private GestionDeck gd;
    private Context context;

    public MyViewHolder(View v, Context c) {
        super(v);
        context = c;
        gd = GestionDeck.getInstance();
        nomDeck = (TextView) v.findViewById(R.id.nomDeck);
        imageDeck = (ImageView) v.findViewById(R.id.imgDeck);
        txtNomDeckSelectionne = (TextView) v.findViewById(R.id.txtNomDeckSelectionne);
        imageDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSurCarte();
            }
        });
        nomDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSurCarte();
            }
        });
    }

    private void clickSurCarte(){
        gd.setDeckSelectionne(deck);
        CreationActivity.changerDeckSelectionne();
    }

}