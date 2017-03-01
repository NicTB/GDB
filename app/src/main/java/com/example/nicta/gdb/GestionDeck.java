package com.example.nicta.gdb;

import android.content.Context;

import java.util.ArrayList;

public class GestionDeck {

    private GestionDeck(){}

    private static GestionDeck gd;
    private Deck deckSelectionne;
    protected Context context;

    public static GestionDeck getInstance(){
        if(gd==null){
            gd = new GestionDeck();
        }
        return gd;
    }


    public void setDeckSelectionne(Deck d){
        deckSelectionne = d;
    }

    public Deck getDeckSelectionne(){
        return deckSelectionne;
    }

    public void sauvegarderDeck(){
        GdbBDD gdbBDD = new GdbBDD(context);
        gdbBDD.open();
        gdbBDD.removeCarteDeckWithDeckID(deckSelectionne.getId());
        for(Carte c : deckSelectionne.getCartesDeck()){
            gdbBDD.insertCarteDeck(new CarteDeck(c.id, deckSelectionne.getId()));
        }
        gdbBDD.updateDeck(deckSelectionne);
        gdbBDD.close();
    }

    public int filtreTag;
    public ArrayList<Boolean> filtreType;
}
