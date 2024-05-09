package com.example.mini_projet_mobile_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "matieres.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MATIERE = "matiere";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITRE = "titre";
    private static final String COLUMN_NIVEAU = "niveau";

    private static final String CREATE_TABLE_MATIERE = "CREATE TABLE " +
            TABLE_MATIERE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITRE + " TEXT, " +
            COLUMN_NIVEAU + " TEXT" +
            ")";

    private static final String[] DEFAULT_MATIERES_TITRES = {
            "JEE",
            "Programmation Mobile",
            "SpringMVC",
            "Angular"
    };

    private static final String[] DEFAULT_MATIERES_NIVEAUX = {
            "GL2",
            "RT2/GL2",
            "GL3",
            "GL3"
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MATIERE);
        insertDefaultMatieres(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATIERE);
        onCreate(db);
    }

    public long insertMatiere(String titre, String niveau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITRE, titre);
        values.put(COLUMN_NIVEAU, niveau);
        long id = db.insert(TABLE_MATIERE, null, values);
        db.close();
        return id;
    }

    public List<String> getAllMatieres() {
        List<String> matieres = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MATIERE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String titre = cursor.getString(cursor.getColumnIndex(COLUMN_TITRE));
                String niveau = cursor.getString(cursor.getColumnIndex(COLUMN_NIVEAU));
                matieres.add(titre + "\n" + niveau);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return matieres;
    }

    private void insertDefaultMatieres(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < DEFAULT_MATIERES_TITRES.length; i++) {
            values.put(COLUMN_TITRE, DEFAULT_MATIERES_TITRES[i]);
            values.put(COLUMN_NIVEAU, DEFAULT_MATIERES_NIVEAUX[i]);
            db.insert(TABLE_MATIERE, null, values);
        }
    }
}
