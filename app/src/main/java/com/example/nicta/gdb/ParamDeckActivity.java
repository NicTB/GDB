package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ParamDeckActivity extends AppCompatActivity {

    // Activité pour les paramètres du deck

    Button btnSauvegarderDeck;
    Button btnPartager;
    EditText editTextNomDeck;
    EditText editTextDescriptionDeck;
    Spinner spinnerLeader;
    ArrayList<Carte> leadersFaction;

    FournisseurCartes fc;
    GestionDeck gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_deck);
        gd = GestionDeck.getInstance();
        fc = FournisseurCartes.getInstance();
        leadersFaction = new ArrayList<>();
        ArrayList<Carte> leaders = fc.getLeaders();

        btnSauvegarderDeck = (Button) findViewById(R.id.btnSauvegarderDeck);
        btnPartager = (Button) findViewById(R.id.btnPartager);
        editTextNomDeck = (EditText) findViewById(R.id.editTextNomDeck);
        editTextDescriptionDeck = (EditText) findViewById(R.id.editTextDescriptionDeck);
        spinnerLeader = (Spinner) findViewById(R.id.spinnerLeader);

        // Changement de leader
        spinnerLeader.setSelection(gd.filtreTag);
        ArrayList<String> nomLeaders = new ArrayList<>();
        int position = 0;
        int count = 0;
        for(Carte c : leaders){
            if(c.faction == gd.getDeckSelectionne().getFaction()) {
                leadersFaction.add(c);
                nomLeaders.add(c.nom);
                if (c.id == gd.getDeckSelectionne().getLeader().id) {
                    position = count;
                }
                count++;
            }
        }

        ArrayAdapter<String> adapterTags = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomLeaders);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeader.setAdapter(adapterTags);
        spinnerLeader.setSelection(position);


        editTextNomDeck.setText(gd.getDeckSelectionne().getNom());
        editTextDescriptionDeck.setText(gd.getDeckSelectionne().getDescription());

        // Change le nom, la description et le leader du deck
        btnSauvegarderDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gd.getDeckSelectionne().setNom(editTextNomDeck.getText().toString());
                gd.getDeckSelectionne().setDescription(editTextDescriptionDeck.getText().toString());
                gd.getDeckSelectionne().setLeader(leadersFaction.get(spinnerLeader.getSelectedItemPosition()));

                gd.sauvegarderDeck();
                finish();
            }
        });

    }
}
