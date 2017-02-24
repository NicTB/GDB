package com.example.nicta.gdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nicta.gdb.Enums.Faction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicta on 2017-02-24.
 */

public class ChoisirFactionAdapter extends ArrayAdapter<Faction> {;
    ArrayList<String> capaciteFaction;
    ArrayList<String> StringImgBackFaction;

    public  ChoisirFactionAdapter(Context context, int resource, List<Faction> items)
    {
        super(context, resource, items);
        CapacitesDeFaction();
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

        Faction f  = getItem(position);

        if(f != null)
        {
            TextView txtChoixNomFaction = (TextView) maView.findViewById(R.id.txtChoixNomFaction);
            TextView txtChoixFactionCapacite = (TextView) maView.findViewById(R.id.txtChoixFactionCapacite);
            ImageView imgBackFaction = (ImageView) maView.findViewById(R.id.imgBackFaction);

            txtChoixNomFaction.setText(f.name());
            txtChoixFactionCapacite.setText(capaciteFaction.get(position));
            int id = getContext().getResources().getIdentifier(StringImgBackFaction.get(position), "drawable", getContext().getPackageName());
            imgBackFaction.setImageResource(id);

        }

        return maView;
    }

    private void CapacitesDeFaction(){
        capaciteFaction = new ArrayList<>();
        StringImgBackFaction = new ArrayList<>();

        capaciteFaction.add("At the end of each round, keep the last loyal non-Gold, non-Fleeting, non-Resilient Neutral or Monsters unit that appeared on your side of the battlefield.");
        capaciteFaction.add("After drawing cards at the start of Rounds 2 and 3, redraw up to 1 card in your hand.");
        capaciteFaction.add("Add 2 strength to each Gold unit when it appears on your side.");
        capaciteFaction.add("Choose who begins one round per game.");
        capaciteFaction.add("At the start of Rounds 2 and 3, add 1 to the base strength of every non-Gold unit in your hand, deck and graveyard.");

        StringImgBackFaction.add("back_monster");
        StringImgBackFaction.add("back_nilfgaard");
        StringImgBackFaction.add("back_northern_realms");
        StringImgBackFaction.add("back_scoiatael");
        StringImgBackFaction.add("back_skellige");
    }

}
