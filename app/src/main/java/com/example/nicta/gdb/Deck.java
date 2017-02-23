package com.example.nicta.gdb;

import java.util.ArrayList;

/**
 * Created by nicta on 2017-02-22.
 */

public class Deck {

    private int Id;
    private String proprietaire;
    private String nom;
    private String description;
    private int faction;
    private Carte leader;

    private ArrayList<Carte> cartesDeck;

    public Deck(){
        cartesDeck = new ArrayList<>();
    }

    protected void setFaction(int f){
        faction = f;
    }

    protected void setLeader(Carte nouvLeader){
        leader = nouvLeader;
    }

    protected void setNom(String n){
        nom = n;
    }

    protected void setDescription(String d){
        description = d;
    }

    protected void setProprietaire(String p){
        proprietaire = p;
    }

    protected int getFaction(){
        return faction;
    }

    protected Carte getLeader(){
        return leader;
    }

    protected String getNom(){
        return nom;
    }

    protected String getDescription(){
        return description;
    }

    protected String getProprietaire(){
        return proprietaire;
    }

    protected boolean AjouterCarte(Carte carte){
        if(carte.type== Enums.Type.Bronze.getValue()){ // Si c'est une carte bronze, on peut en avoir jusqu'à 3 copies dans un deck
            if(!cartesDeck.contains(carte)){
                cartesDeck.add(carte);
                return true;
            }
            else{
                int compte = 0;
                for(Carte c : cartesDeck){
                    if(c.id == carte.id){
                        compte++;
                    }
                }
                if(compte<3){
                    cartesDeck.add(carte);
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else if(carte.type == Enums.Type.Silver.getValue() || carte.type == Enums.Type.Gold.getValue()){ // Si c'est une carte Silver ou Gold, on ne peut en avoir qu'une seule
            if(!cartesDeck.contains(carte)){
                cartesDeck.add(carte);
                return true;
            }
            else
                return false;
        }
        else // Si la carte est un leader (ceci ne devrait jamais arriver)
            return false;
    }

    protected void RetirerCarte(Carte c){
        cartesDeck.remove(c);
    }

    protected ArrayList<Carte> getCartesDeck(){
        return cartesDeck;
    }

    protected boolean checkValide(){
        if(cartesDeck.size()>=25 && cartesDeck.size()<=40)
            return true;
        else
            return false;
    }

}
