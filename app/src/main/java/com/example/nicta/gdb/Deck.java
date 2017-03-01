package com.example.nicta.gdb;

import java.util.ArrayList;

public class Deck {

    private int Id;
    private String proprietaire;
    private String nom;
    private String description;
    private int faction;
    private Carte leader;

    private int countSilver;
    private int countGold;

    private ArrayList<Carte> cartesDeck;

    public Deck(){
        cartesDeck = new ArrayList<>();
    }

    protected void setId(int id){
        Id = id;
    }

    protected void setFaction(int f){
        faction = f;
    }

    protected void setLeader(Carte nouvLeader){
        leader = nouvLeader;
    }

    protected void setLeaderParId(int id){
        FournisseurCartes fc = FournisseurCartes.getInstance();
        ArrayList<Carte> leaders = fc.getLeaders();
        for(Carte c : leaders){
            if(c.id == id){
                leader = c;
            }
        }
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

    // Transforme les objets CarteDeck en Carte, puis les ajoute au deck
    // Utilisé dans la classe GdbBDD, dnas la méthode getDecks()
    protected void setCartesDeck(ArrayList<CarteDeck> cds){
        if(cds!=null){
            ArrayList<Integer> ids = new ArrayList<>(); // Liste des id des cartes
            for(CarteDeck cd : cds){
                ids.add(cd.getIdCarte());
            }

            FournisseurCartes fc = FournisseurCartes.getInstance();
            ArrayList<Carte> cartes = fc.getCartes(); // Liste de toutes les cartes
            // Pour chacune des cartes
            for(Carte carte : cartes){
                // Si la liste d'id contient l'id de cette carte
                if(ids.contains(carte.id)){
                    if(carte.type == Enums.Type.Bronze.getValue()) { // Si la carte est une carte bronze (on peut en avoir jusq'à 3 dans un deck)
                        for (int i : ids) { // Ajoute cette carte chaque fois qu'elle apparait dans la liste d'id
                            if (i == carte.id)
                                cartesDeck.add(carte);
                        }
                    }
                    else{ // Si ce n'est pas une carte bronze, elle ne peut être là qu'une seule fois, donc on l'ajoute directement
                        cartesDeck.add(carte);
                    }

                }
            }
        }
    }

    protected int getId(){
        return Id;
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


    // Méthode pour ajouter une carte au deck
    protected void AjouterCarte(Carte carte){
        // Un deck peut avoir jusqu'à 6 cartes Silver et 4 cartes Gold
        // Donc on compte ici le nombre de cartes de chaque dans le deck
        CompteCartesType();
        // Un deck peut contenir au maximum 40 cartes
        if(cartesDeck.size()<40) {
            switch (carte.type) {
                case 0: // Type == Bronze
                    if (!cartesDeck.contains(carte)) { // Si le deck ne contient pas cette carte, on peut l'ajouter
                        cartesDeck.add(carte);
                    }
                    else {
                        // Un deck peut avoir jusqu'à 3 copies d'une carte Bronze
                        int compte = 0; // Donc on compte le nombre de copies de cette carte
                        for (Carte c : cartesDeck) {
                            if (c.id == carte.id) {
                                compte++;
                            }
                        }
                        // S'il y en a moins de 3, on peut l'ajouter
                        if (compte < 3) {
                            cartesDeck.add(carte);
                        }
                    }
                    break;
                case 1: // Type == Silver
                    if(countSilver<6) { // S'il y a moins de 6 cartes Silver dans le deck
                        if (!cartesDeck.contains(carte)) { // Et si le deck ne contient pas déjà cette carte, on peut l'ajouter
                            cartesDeck.add(carte);
                        }
                    }
                    break;
                case 2: // Type == Gold
                    if(countGold < 4) {// S'il y a moins de 4 cartes Gold dans le deck
                        if (!cartesDeck.contains(carte)) {  // Et si le deck ne contient pas déjà cette carte, on peut l'ajouter
                            cartesDeck.add(carte);
                        }
                    }
                    break;
            }
        }
    }

    // Méthode pour retirer une carte du deck
    protected void RetirerCarte(Carte c){
        cartesDeck.remove(c);
    }

    protected ArrayList<Carte> getCartesDeck(){
        return cartesDeck;
    }

    protected boolean checkValide(){ // Un deck est valide s'il a entre 25 et 40 cartes
        if(cartesDeck.size()>=25 && cartesDeck.size()<=40)
            return true;
        else
            return false;
    }

    // Compte les cartes Silver et Gold du deck
    private void CompteCartesType(){
        countSilver = 0;
        countGold = 0;
        for(Carte c : cartesDeck){
            switch (c.type) {
                case 1: // Type == Silver
                    countSilver++;
                    break;
                case 2: // Type == Gold
                    countGold++;
                    break;
            }
        }
    }

}
