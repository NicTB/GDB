package com.example.nicta.gdb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by nicta on 2017-02-26.
 */

public class DeckBuilderAdapter extends RecyclerView.Adapter<MyViewHolderCarte> {
    private ArrayList<Carte> list;
    Context context;
    public DeckBuilderActivity deckBuilderActivity;

    public DeckBuilderAdapter(ArrayList<Carte> Data, Context c, DeckBuilderActivity dba) {
        list = Data;
        context = c;
        deckBuilderActivity = dba;
    }
    @Override
    public MyViewHolderCarte onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carte_cardview, parent, false);
        MyViewHolderCarte holder = new MyViewHolderCarte(view, deckBuilderActivity);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolderCarte holder, int position) {
        int id = context.getResources().getIdentifier(list.get(position).image, "drawable", context.getPackageName());
        holder.imageCarte.setImageResource(id);
        holder.carte = list.get(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}