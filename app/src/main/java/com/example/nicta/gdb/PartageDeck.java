package com.example.nicta.gdb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicta on 2017-03-01.
 */

public class PartageDeck {
    public PartageDeck(Deck deck){
        id = deck.getId();
        proprietaire = deck.getProprietaire();
        nom = deck.getNom();
        description = deck.getDescription();
        faction = deck.getFaction();
        leader = deck.getLeader().id;
        idCarteDeck= new ArrayList<>();
        for(Carte carte : deck.getCartesDeck()){
            idCarteDeck.add(carte.id);
        }
    }

    public int id;
    public String proprietaire;
    public String nom;
    public String description;
    public int faction;
    public int leader;
    public List<Integer> idCarteDeck;
}
