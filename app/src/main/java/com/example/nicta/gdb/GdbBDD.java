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
    private static final String COL_PROPRIETAIRE = "Proprietaire";
    private static final String COL_NOM = "Nom";
    private static final String COL_DESCRIPTION = "Description";
    private static final String COL_FACTION = "Faction";
    private static final String COL_LEADER = "Leader";


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
