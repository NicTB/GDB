package com.example.nicta.gdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicta on 2017-02-17.
 */

public class CollectionAdapter extends ArrayAdapter<Carte> {


    public  CollectionAdapter(Context context, int resource, List<Carte> items)
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
            maView = viewInflater.inflate(R.layout.collection_carte, null);
        }

        Carte c  = getItem(position);

        if(c != null)
        {
            ImageView iv = (ImageView) maView.findViewById(R.id.imgCarte);
            TextView tv = (TextView) maView.findViewById(R.id.txtNomCarte);

            int id = getContext().getResources().getIdentifier(c.image, "drawable", getContext().getPackageName());
            iv.setImageResource(id);
            tv.setText(c.nom);
        }

        return maView;
    }
}
