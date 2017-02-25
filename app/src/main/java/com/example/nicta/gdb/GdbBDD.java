package com.example.nicta.gdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by nicta on 2017-02-23.
 */

public class GdbBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "gdb.db";

    private static final String TABLE_DECK = "table_deck";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_PROPRIETAIRE = "Proprietaire";
    private static final int NUM_COL_PROPRIETAIRE = 1;
    private static final String COL_NOM = "Nom";
    private static final int NUM_COL_NOM = 2;
    private static final String COL_DESCRIPTION = "Description";
    private static final int NUM_COL_DESCRIPTION = 3;
    private static final String COL_FACTION = "Faction";
    private static final int NUM_COL_FACTION = 4;
    private static final String COL_LEADER = "Leader";
    private static final int NUM_COL_LEADER = 5;


    private static final String TABLE_CARTEDECK = "table_carteDeck";
    private static final String COL_IDCARTE = "IdCarte";
    private static final int NUM_COL_IDCARTE = 1;
    private static final String COL_IDDECK = "IdDeck";
    private static final int NUM_COL_IDDECK = 2;

    private SQLiteDatabase bdd;

    private GdbSqLite maBaseSQLite;

    public GdbBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new GdbSqLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    /***********************     Deck       ****************************/

    public long insertDeck(Deck deck){
        ContentValues values = new ContentValues();
        values.put(COL_PROPRIETAIRE, deck.getProprietaire());
        values.put(COL_NOM, deck.getNom());
        values.put(COL_DESCRIPTION, deck.getDescription());
        values.put(COL_FACTION, deck.getFaction());
        values.put(COL_LEADER, deck.getLeader().id);
        return bdd.insert(TABLE_DECK, null, values);
    }

    public int updateDeck(int id, Deck deck){
        ContentValues values = new ContentValues();
        values.put(COL_PROPRIETAIRE, deck.getProprietaire());
        values.put(COL_NOM, deck.getNom());
        values.put(COL_DESCRIPTION, deck.getDescription());
        values.put(COL_FACTION, deck.getFaction());
        values.put(COL_LEADER, deck.getLeader().id);
        return bdd.update(TABLE_DECK, values, COL_ID + " = " +id, null);
    }

    public int removeDeckWithID(int id){
        return bdd.delete(TABLE_DECK, COL_ID + " = " +id, null);
    }

    ArrayList<Deck> getDecks() {
        String[] colonnes = new String[]{COL_ID, COL_PROPRIETAIRE, COL_NOM, COL_DESCRIPTION, COL_FACTION, COL_LEADER};

        Cursor curseur = bdd.query(TABLE_DECK, colonnes, null, null, null, null, null);
        return cursorToDeck(curseur);
    }

    private ArrayList<Deck> cursorToDeck(Cursor c){
        if (c.getCount() == 0)
            return null;

        ArrayList<Deck> decks = new ArrayList<>();
        while(c.moveToNext()) {
            Deck d = new Deck();
            d.setId(c.getInt(NUM_COL_ID));
            d.setProprietaire(c.getString(NUM_COL_PROPRIETAIRE));
            d.setNom(c.getString(NUM_COL_NOM));
            d.setDescription(c.getString(NUM_COL_DESCRIPTION));
            d.setFaction(c.getInt(NUM_COL_FACTION));
            d.setLeaderParId(c.getInt(NUM_COL_LEADER));
            ArrayList<CarteDeck> cd = new ArrayList<>();
            cd = getCarteDeckWithIdDeck(d.getId());

            decks.add(d);
        }
        c.close();

        return decks;
    }


    /***********************     CarteDeck       ****************************/

    public long insertCarteDeck(CarteDeck cd){
        ContentValues values = new ContentValues();
        values.put(COL_IDCARTE, cd.getIdCarte());
        values.put(COL_IDDECK, cd.getIdDeck());
        return bdd.insert(TABLE_CARTEDECK, null, values);
    }

    public int removeCarteDeckWithID(int id){
        return bdd.delete(TABLE_CARTEDECK, COL_ID + " = " +id, null);
    }

    public ArrayList<CarteDeck> getCarteDeckWithIdDeck(int id){
        String[] colonnesCarteDeck = {COL_ID, COL_IDCARTE, COL_IDDECK};
        String selection = COL_IDDECK + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor c = bdd.query(TABLE_CARTEDECK, colonnesCarteDeck, selection, selectionArgs, null, null, null);
        return cursorToCarteDeck(c);
    }

    private ArrayList<CarteDeck> cursorToCarteDeck(Cursor c){
        if (c.getCount() == 0)
            return null;

        ArrayList<CarteDeck> cartes = new ArrayList<>();
        while(c.moveToNext()) {
            cartes.add(new CarteDeck(c.getInt(NUM_COL_IDCARTE), c.getInt(NUM_COL_IDDECK)));
        }
        c.close();

        return cartes;
    }
}
