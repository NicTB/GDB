package com.example.nicta.gdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nicta on 2017-02-23.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView nomDeck;
    public ImageView imageDeck;

    public MyViewHolder(View v) {
        super(v);
        nomDeck = (TextView) v.findViewById(R.id.nomDeck);
        imageDeck = (ImageView) v.findViewById(R.id.imgDeck);
        imageDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}