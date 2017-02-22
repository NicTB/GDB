package com.example.nicta.gdb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AfficherCarteActivity extends Dialog {


    public Activity c;
    public Carte carte;
    public Dialog d;

    public AfficherCarteActivity(Activity a, Carte ca) {
        super(a);
        carte = ca;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_afficher_carte);

        ImageView imgAfficherCarte = (ImageView) findViewById(R.id.imgAfficherCarte);
        TextView txtViewNom = (TextView) findViewById(R.id.txtViewNom);
        TextView txtViewCapacite = (TextView) findViewById(R.id.txtViewCapacite);
        TextView txtViewType = (TextView) findViewById(R.id.txtViewType);
        TextView txtViewFaction = (TextView) findViewById(R.id.txtViewFaction);
        TextView txtViewPosition = (TextView) findViewById(R.id.txtViewPosition);
        TextView txtViewLoyaute = (TextView) findViewById(R.id.txtViewLoyaute);
        TextView txtViewCout = (TextView) findViewById(R.id.txtViewCout);


        String nomImage = carte.image;

        Context context = imgAfficherCarte.getContext();
        int id = context.getResources().getIdentifier(nomImage, "drawable", context.getPackageName());
        imgAfficherCarte.setImageResource(id);

        txtViewNom.setText(carte.nom);
        txtViewCapacite.setText(carte.capacite);
        txtViewType.setText("Type : " + Enums.Type.valueOf(carte.type).toString());
        txtViewFaction.setText("Faction : " + Enums.Faction.valueOf(carte.faction).toString());
        txtViewPosition.setText("Position : " + Enums.Position.valueOf(carte.position).toString());
        txtViewLoyaute.setText("Loyalty : " + Enums.Loyaute.valueOf(carte.loyaute).toString());
        txtViewCout.setText("Crafting cost : " + String.valueOf(carte.coutCreation) + " scraps");
    }

}
