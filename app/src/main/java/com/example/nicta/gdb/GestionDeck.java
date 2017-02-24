package com.example.nicta.gdb;

/**
 * Created by nicta on 2017-02-24.
 */

public class GestionDeck {

    private GestionDeck(){}

    private static GestionDeck gd;
    private Deck deckSelectionne;

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

}
