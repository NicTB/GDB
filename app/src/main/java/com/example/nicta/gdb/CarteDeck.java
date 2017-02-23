package com.example.nicta.gdb;

/**
 * Created by nicta on 2017-02-23.
 */

public class CarteDeck {
    private int id;
    private int idCarte;
    private int idDeck;

    public CarteDeck(int idC, int idD){
        idCarte = idC;
        idDeck = idD;
    }

    protected int getIdCarte(){
        return idCarte;
    }

    protected int getIdDeck(){
        return idDeck;
    }

}
