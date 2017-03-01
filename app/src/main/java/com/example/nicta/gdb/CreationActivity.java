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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class CreationActivity extends AppCompatActivity {

    // Activité d'accueil pour la création de deck

    FournisseurCartes fc;
    RecyclerView listeDecks;
    ArrayList<Deck> decks;
    static TextView txtNomDeckSelectionne;
    Button btnCreerDeck;
    Button btnSupprimerDeck;
    Button btnModifierDeck;
    ProgressBar progressBar2;
    GdbBDD gdbBDD;
    GestionDeck gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        fc = FournisseurCartes.getInstance();
        gd = GestionDeck.getInstance();

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        listeDecks = (RecyclerView) findViewById(R.id.listeDecks);
        txtNomDeckSelectionne = (TextView) findViewById(R.id.txtNomDeckSelectionne);
        btnCreerDeck = (Button) findViewById(R.id.btnCreerDeck);
        btnSupprimerDeck = (Button) findViewById(R.id.btnSupprimerDeck);
        btnModifierDeck = (Button) findViewById(R.id.btnModifierDeck);

        gd.context = this;
        gdbBDD = new GdbBDD(gd.context);

        if(fc.getCartes().size()==0){ // Si on n'a pas de cartes chargées, on va les chercher
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://d75e14.sv55.cmaisonneuve.qc.ca/")
                    .setClient(new OkClient())
                    .build();

            JsonService jsonService = restAdapter.create(JsonService.class);

            Callback<List<Carte>> callbackCarte = new Callback<List<Carte>>() {
                @Override
                public void success(List<Carte> liste, Response response) {
                    fc.cartes.clear();
                    fc.leaders.clear();
                    for (Carte c : liste) {
                        fc.cartes.add(c);
                        if(c.type == 3){
                            fc.leaders.add(c);
                        }
                    }
                    afficherListeDecks();
                    // On enlève la barre de chargement
                    progressBar2.setVisibility(View.GONE);
                }
                @Override
                public void failure(RetrofitError error) {
                    String errorString = error.toString();
                }
            };
            jsonService.getCartes(callbackCarte);
            // On indique à l'utilisateur qu'on charge les cartes
            progressBar2.setVisibility(View.VISIBLE);
        }
        else{
            afficherListeDecks();
            progressBar2.setVisibility(View.GONE);
        }


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
                    Toast.makeText(getBaseContext(), "Veuillez choisir un deck", Toast.LENGTH_SHORT).show();
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

        btnModifierDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreationActivity.this,DeckBuilderActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        if(fc.getCartes().size()>0)
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
        // Trouvé sur ce site : https://www.codeproject.com/Articles/1152595/Android-Horizontal-ListView-Tutorial
        listeDecks.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (decks != null & listeDecks != null) {
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
