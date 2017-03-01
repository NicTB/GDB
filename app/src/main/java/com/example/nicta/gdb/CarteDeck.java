package com.example.nicta.gdb;

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
