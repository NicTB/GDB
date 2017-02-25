package com.example.nicta.gdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicta on 2017-02-25.
 */

public class ChoisirLeaderAdapter extends ArrayAdapter<Carte> {

    public ChoisirLeaderAdapter(Context context, int resource, List<Carte> items)
    {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View maView = convertView;

        if (maView == null)
        {
            LayoutInflater viewInflater = LayoutInflater.from(getContext());
            maView = viewInflater.inflate(R.layout.choisir_faction, null);
        }

        Carte carte  = getItem(position);

        if(carte != null)
        {
            TextView txtChoixNomLeader = (TextView) maView.findViewById(R.id.txtChoixNomFaction);
            TextView txtChoixLeaderCapacite = (TextView) maView.findViewById(R.id.txtChoixFactionCapacite);
            ImageView imgLeader = (ImageView) maView.findViewById(R.id.imgBackFaction);

            txtChoixNomLeader.setText(carte.nom);
            txtChoixLeaderCapacite.setText(carte.capacite);
            int id = getContext().getResources().getIdentifier(carte.image, "drawable", getContext().getPackageName());
            imgLeader.setImageResource(id);

        }

        return maView;
    }
}
