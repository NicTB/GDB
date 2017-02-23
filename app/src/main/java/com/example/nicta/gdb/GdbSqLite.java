package com.example.nicta.gdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by nicta on 2017-02-23.
 */

public class GdbSqLite extends SQLiteOpenHelper {

    private static final String TABLE_DECK = "table_deck";
    private static final String COL_ID = "ID";
    private static final String COL_PROPRIETAIRE = "Proprietaire";
    private static final String COL_NOM = "Nom";
    private static final String COL_DESCRIPTION = "Description";
    private static final String COL_FACTION = "Faction";
    private static final String COL_LEADER = "Leader";


    private static final String TABLE_CARTEDECK = "table_carteDeck";
    private static final String COL_IDCARTE = "IdCarte";
    private static final String COL_IDDECK = "IdDeck";

    private static final String CREATE_BDD_DECK = "CREATE TABLE " + TABLE_DECK + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PROPRIETAIRE + " TEXT NOT NULL, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_DESCRIPTION + " TEXT NOT NULL, "
            + COL_FACTION + " INTEGER NOT NULL, "
            + COL_LEADER + " INTEGER NOT NULL);";

    private static final String CREATE_BDD_CARTEDECK = "CREATE TABLE " + TABLE_CARTEDECK + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IDCARTE + " TEXT NOT NULL, "
            + COL_IDDECK + " INTEGER NOT NULL);";

    public GdbSqLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD_DECK);
        db.execSQL(CREATE_BDD_CARTEDECK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_DECK + ";");
        db.execSQL("DROP TABLE " + TABLE_CARTEDECK + ";");
        onCreate(db);
    }

}