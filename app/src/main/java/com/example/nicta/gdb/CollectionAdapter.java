package com.example.nicta.gdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicta on 2017-02-17.
 */

public class CollectionAdapter extends ArrayAdapter<TrioCarte> {


    public  CollectionAdapter(Context context, int resource, List<TrioCarte> items)
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
            maView = viewInflater.inflate(R.layout.multi_carte_layout, null);
        }

        TrioCarte c  = getItem(position);

        if(c != null)
        {

        }

        return maView;
    }
}
