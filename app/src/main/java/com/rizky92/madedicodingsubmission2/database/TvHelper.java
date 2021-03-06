package com.rizky92.madedicodingsubmission2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.TVS_TABLE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.TvColumns.TV_ID;

public class TvHelper {

    private static final String DATABASE_TABLE = TVS_TABLE;
    private static DatabaseHelper helper;
    private static TvHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                TV_ID + " ASC"
        );
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                TV_ID + " = ?", new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, TV_ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, TV_ID + " = ?", new String[]{id});
    }
}
