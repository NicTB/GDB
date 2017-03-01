package com.example.nicta.gdb;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nicta on 2017-02-28.
 */

public class GdbTest {

    @Test
    public void ajouterCarteDeck() throws Exception {
        Deck deck = new Deck();
        Carte c = new Carte();
        c.type = 0;
        deck.AjouterCarte(c);

        assertEquals(1, deck.getCartesDeck().size());
    }

    @Test
    public void ajouterCarteDeckPlusQue4Golds() throws Exception {
        Deck deck = new Deck();

        Carte c1 = new Carte();
        c1.type = 2; // Type == Gold
        deck.AjouterCarte(c1);

        Carte c2 = new Carte();
        c2.type = 2;
        deck.AjouterCarte(c2);

        Carte c3 = new Carte();
        c3.type = 2;
        deck.AjouterCarte(c3);

        Carte c4 = new Carte();
        c4.type = 2;
        deck.AjouterCarte(c4);

        Carte c5 = new Carte(); // Ne va pas être insérée, car la limite est de 4 cartes gold
        c5.type = 2;
        deck.AjouterCarte(c5);

        assertEquals(4, deck.getCartesDeck().size());
    }

    @Test
    public void ajouterCarteDeckPlusQue3BronzesPareilles() throws Exception {
        Deck deck = new Deck();

        Carte c = new Carte();
        c.id = 2;
        c.type = 0; // Type == Bronze
        deck.AjouterCarte(c);
        deck.AjouterCarte(c);
        deck.AjouterCarte(c);
        deck.AjouterCarte(c); // Ne vas pas être insérée, car la limite est de 3 cartes bronzes identiques
        assertEquals(3, deck.getCartesDeck().size());

        Carte c1 = new Carte();
        c1.id = 4;
        c1.type = 0; // Type == Bronze
        deck.AjouterCarte(c1);

        assertEquals(4, deck.getCartesDeck().size());
    }



}
