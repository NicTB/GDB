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
import android.widget.Toast;

public class AfficherCarteActivity extends Dialog implements android.view.View.OnClickListener {


    public Activity c;
    public Carte carte;
    public Dialog d;

    public AfficherCarteActivity(Activity a, Carte ca) {
        super(a);
        // TODO Auto-generated constructor stub
        carte = ca;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_afficher_carte);

        ImageView imgAfficherCarte = (ImageView) findViewById(R.id.imgAfficherCarte);
        String nomImage = carte.image;

        Context context = imgAfficherCarte.getContext();
        int id = context.getResources().getIdentifier(nomImage, "drawable", context.getPackageName());
        imgAfficherCarte.setImageResource(id);
    }

    @Override
    public void onClick(View v) {

    }
}
