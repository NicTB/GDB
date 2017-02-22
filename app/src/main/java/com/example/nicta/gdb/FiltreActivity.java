package com.example.nicta.gdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class FiltreActivity extends AppCompatActivity {

    private Spinner spinFaction; //Sélection de faction
    private Spinner spinTags; //Sélection de tag

    private CheckBox checkNeutre; // sélection des cartes neutres

    private Button btnConfirmerFiltre;

    ArrayList<CheckBox> checksType;
    ArrayList<CheckBox> checksRarete;
    FournisseurCartes fc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);
        fc = FournisseurCartes.getInstance();

        spinFaction = (Spinner) findViewById(R.id.spinFaction);
        spinTags = (Spinner) findViewById(R.id.spinTags);
        checksType = new ArrayList<CheckBox>();
        checksType.add((CheckBox) findViewById(R.id.checkBronze));
        checksType.add((CheckBox) findViewById(R.id.checkSilver));
        checksType.add((CheckBox) findViewById(R.id.checkGold));
        checksType.add((CheckBox) findViewById(R.id.checkLeader));
        checksRarete = new ArrayList<CheckBox>();
        checksRarete.add((CheckBox) findViewById(R.id.checkCommon));
        checksRarete.add((CheckBox) findViewById(R.id.checkRare));
        checksRarete.add((CheckBox) findViewById(R.id.checkEpic));
        checksRarete.add((CheckBox) findViewById(R.id.checkLegendary));

        btnConfirmerFiltre = (Button) findViewById(R.id.btnConfirmerFiltre);

        // Initialisation du spinner de factions
        Enums.Faction[] factionsValues = Enums.Faction.values();
        ArrayList<String> listeNomFaction = new ArrayList<String>();
        listeNomFaction.add("Aucune faction");
        for(Enums.Faction val : factionsValues){
            listeNomFaction.add(val.name());
        }
        ArrayAdapter<String> adapterFaction = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeNomFaction);
        adapterFaction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFaction.setAdapter(adapterFaction);

        // Initialisation du spinner de tags
        ArrayList<Tags> tags = fc.getTags();
        ArrayList<String> nomTags = new ArrayList<>();
        nomTags.add("Aucun tag");
        for(Tags t : tags)
            nomTags.add(t.nom);
        ArrayAdapter<String> adapterTags = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nomTags);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTags.setAdapter(adapterTags);


        // Initialisation des filtres sélectionnés
        spinFaction.setSelection(fc.filtreFaction);
        spinTags.setSelection(fc.filtreTag);


        // Checkbox pour les types et les raretés
        for(int i =0; i<4; i++){
            checksType.get(i).setChecked(fc.filtreType.get(i));
            checksRarete.get(i).setChecked(fc.filtreRarete.get(i));
        }

        btnConfirmerFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miseAJourFiltre();
                /*Intent intent = new Intent(FiltreActivity.this,CollectionActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }


    protected void miseAJourFiltre(){
        fc.filtreFaction = spinFaction.getSelectedItemPosition();
        fc.filtreTag = spinTags.getSelectedItemPosition();
        fc.filtreType = new ArrayList<>();
        fc.filtreRarete = new ArrayList<>();
        for(int i =0; i<4; i++){
            fc.filtreType.add(checksType.get(i).isChecked());
            fc.filtreRarete.add(checksRarete.get(i).isChecked());
        }

    }
}
