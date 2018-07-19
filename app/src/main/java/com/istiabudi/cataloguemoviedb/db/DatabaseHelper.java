package com.istiabudi.cataloguemoviedb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "dbfave";

    private static final int DATABASE_VERSION = 7;

    private static final String SQL_CREATE_TABLE_FAVE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "%s INTEGER NOT NULL," + // tmdbid
                            "%s TEXT NOT NULL," + // title
                            "%s TEXT NOT NULL," + // releasedate
                            "%s TEXT NOT NULL," + // decription
                            "%s TEXT NOT NULL" + // poster
                            ")",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.FaveColumns._ID,
                    DatabaseContract.FaveColumns.TMDBID,
                    DatabaseContract.FaveColumns.TITLE,
                    DatabaseContract.FaveColumns.RELEASEDATE,
                    DatabaseContract.FaveColumns.DESCRIPTION,
                    DatabaseContract.FaveColumns.POSTER
            );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        this.onCreate(db);
    }
}
