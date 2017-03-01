package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

public class DeckFiltreActivity extends AppCompatActivity {

    // Activité pour le filtre dans l'activité DeckBuilder

    Button btnFiltrerCarte;
    Spinner spinnerTags;
    ArrayList<CheckBox> checksType;
    FournisseurCartes fc;
    GestionDeck gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_filtre);
        btnFiltrerCarte = (Button) findViewById(R.id.btnFiltrerCarte);
        fc = FournisseurCartes.getInstance();
        gd = GestionDeck.getInstance();
        checksType = new ArrayList<CheckBox>();
        checksType.add((CheckBox) findViewById(R.id.chkBronze));
        checksType.add((CheckBox) findViewById(R.id.chkSilver));
        checksType.add((CheckBox) findViewById(R.id.chkGold));

        spinnerTags = (Spinner) findViewById(R.id.spinnerTags);

        // Initialisation du spinner de tags
        ArrayList<Tags> tags = fc.getTags();
        ArrayList<String> nomTags = new ArrayList<>();
        nomTags.add("Aucun tag");
        for(Tags t : tags)
            nomTags.add(t.nom);
        ArrayAdapter<String> adapterTags = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nomTags);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTags.setAdapter(adapterTags);

        spinnerTags.setSelection(gd.filtreTag);

        // Checkbox pour les types et les raretés
        for(int i = 0; i<3; i++){
            checksType.get(i).setChecked(gd.filtreType.get(i));
        }

        btnFiltrerCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gd.filtreTag = spinnerTags.getSelectedItemPosition();
                gd.filtreType = new ArrayList<>();
                for(int i =0; i<3; i++){
                    gd.filtreType.add(checksType.get(i).isChecked());
                }
                finish();
            }
        });



    }
}
