package com.example.nicta.gdb;

public class CarteDeck {
    private int Id;
    private int IdDeck;
    private int IdCarte;

    public CarteDeck(int idC, int idD){
        IdCarte = idC;
        IdDeck = idD;
    }

    protected int getIdCarte(){
        return IdCarte;
    }

    protected int getIdDeck(){
        return IdDeck;
    }

}
