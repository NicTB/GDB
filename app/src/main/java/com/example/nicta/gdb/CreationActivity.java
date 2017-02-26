package com.example.nicta.gdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreationActivity extends AppCompatActivity {

    FournisseurCartes fc;
    RecyclerView listeDecks;
    ArrayList<Deck> decks;
    static TextView txtNomDeckSelectionne;
    Button btnCreerDeck;
    Button btnSupprimerDeck;
    GdbBDD gdbBDD;
    GestionDeck gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        fc = FournisseurCartes.getInstance();
        listeDecks = (RecyclerView) findViewById(R.id.listeDecks);
        txtNomDeckSelectionne = (TextView) findViewById(R.id.txtNomDeckSelectionne);
        btnCreerDeck = (Button) findViewById(R.id.btnCreerDeck);
        btnSupprimerDeck = (Button) findViewById(R.id.btnSupprimerDeck);
        gd = GestionDeck.getInstance();

        gdbBDD = new GdbBDD(this);

        afficherListeDecks();

        btnCreerDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreationActivity.this,ChoisirFactionActivity.class);
                startActivity(intent);
            }
        });

        btnSupprimerDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deck deck = gd.getDeckSelectionne();
                if(deck==null){
                    Toast.makeText(getBaseContext(), "Veullez choisir un deck", Toast.LENGTH_SHORT).show();
                }
                else{
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    supprimerDeckSelectionne();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Voulez-vous vraiment supprimer ce deck?")
                            .setPositiveButton("Oui", dialogClickListener)
                            .setNegativeButton("Non", dialogClickListener).show();
                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        afficherListeDecks();
    }


    // Change l'affichage du nom du deck sélectionné
    public static void changerDeckSelectionne(){
        GestionDeck gd = GestionDeck.getInstance();
        Deck d = gd.getDeckSelectionne();
        if(d==null){
            txtNomDeckSelectionne.setText("Aucun");
        }
        else{
            txtNomDeckSelectionne.setText(gd.getDeckSelectionne().getNom());
        }
    }

    private void afficherListeDecks(){
        // Mets à jour la liste des decks
        decks = new ArrayList<>();
        gdbBDD.open();
        decks = gdbBDD.getDecks();
        gdbBDD.close();

        // Affichage des decks dans la RecyclerView
        listeDecks.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (decks.size() > 0 & listeDecks != null) {
            listeDecks.setAdapter(new CreationAdapter(decks, getBaseContext()));
        }
        listeDecks.setLayoutManager(MyLayoutManager);
    }

    private void supprimerDeckSelectionne(){
        gdbBDD.open();
        gdbBDD.removeDeckWithID(gd.getDeckSelectionne().getId());
        gdbBDD.removeCarteDeckWithDeckID(gd.getDeckSelectionne().getId());
        gdbBDD.close();

        gd.setDeckSelectionne(null);
        afficherListeDecks();
        changerDeckSelectionne();
    }

}
