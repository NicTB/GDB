package com.example.nicta.gdb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AfficherCarteActivity extends Dialog {

    public Activity c;
    public Carte carte;

    // Dialogue qui s'ouvre lorsqu'on clique sur une carte dans la Collection

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
        TextView txtViewTagsCarte = (TextView) findViewById(R.id.txtViewTagsCarte);

        // Cherche les tags de la carte
        FournisseurCartes fc = FournisseurCartes.getInstance();
        ArrayList<TagCarte> tc = fc.getTagCarte();
        ArrayList<Tags> tags = fc.getTags();
        ArrayList<Integer> idtags = new ArrayList<>();
        for(TagCarte tagcarte : tc){
            if(tagcarte.idCarte == carte.id){
                idtags.add(tagcarte.idTag);
            }
        }
        String stringTags = "";
        boolean premier = true;
        for(Tags tag : tags){
            if(idtags.contains(tag.id)){
                if(!premier){
                    stringTags += ", ";
                }
                stringTags += tag.nom;
                premier = false;
            }
        }


        String nomImage = carte.image;

        // Va chercher l'image dans les ressources
        Context context = imgAfficherCarte.getContext();
        int id = context.getResources().getIdentifier(nomImage, "drawable", context.getPackageName());
        imgAfficherCarte.setImageResource(id);

        txtViewNom.setText(carte.nom);
        txtViewCapacite.setText(carte.capacite);
        if(idtags.size() > 0) {
            txtViewTagsCarte.setVisibility(View.VISIBLE);
            txtViewTagsCarte.setText("Tags : " + stringTags);
        }
        else{
            txtViewTagsCarte.setVisibility(View.GONE);
        }
        txtViewType.setText("Type : " + Enums.Type.valueOf(carte.type).toString());
        txtViewFaction.setText("Faction : " + Enums.Faction.valueOf(carte.faction).toString());
        txtViewPosition.setText("Position : " + Enums.Position.valueOf(carte.position).toString());
        txtViewLoyaute.setText("Loyalty : " + Enums.Loyaute.valueOf(carte.loyaute).toString());
        txtViewCout.setText("Crafting cost : " + String.valueOf(carte.coutCreation) + " scraps");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.dismiss(); // ferme le dialogue si on clique dessus
        return true;
    }
}
