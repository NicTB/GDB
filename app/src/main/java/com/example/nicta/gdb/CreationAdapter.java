package com.example.nicta.gdb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreationAdapter extends RecyclerView.Adapter<MyViewHolder> {

    // Adapter pour l'activité Creation

    private ArrayList<Deck> list;
    Context context;
    public CreationAdapter(ArrayList<Deck> Data, Context c) {
        list = Data;
        context = c;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_cardview, parent, false);
        MyViewHolder holder = new MyViewHolder(view, context);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.nomDeck.setText(list.get(position).getNom());
        int id = context.getResources().getIdentifier(list.get(position).getLeader().image, "drawable", context.getPackageName());
        holder.imageDeck.setImageResource(id);
        holder.deck = list.get(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}