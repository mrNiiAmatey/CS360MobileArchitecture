package com.example.cs360_nii_tagoe_option3;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cs360_nii_tagoe_option3.db";
    public static final int DATABASE_VERSION = 1;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Weight table
    public static final String TABLE_WEIGHT = "weight";
    public static final String COLUMN_WEIGHT_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WEIGHT_VALUE = "weight";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT UNIQUE," +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        db.execSQL(createUsersTable);

        String createWeightTable = "CREATE TABLE " + TABLE_WEIGHT + "(" +
                COLUMN_WEIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_WEIGHT_VALUE + " TEXT" +
                ")";
        db.execSQL(createWeightTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For simplicity, drop and recreate the tables on upgrade.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        onCreate(db);
    }

    // Create a new user account.
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Verify user credentials.
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_USER_ID };
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Insert a weight record.
    public boolean insertWeight(String date, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WEIGHT_VALUE, weight);
        long result = db.insert(TABLE_WEIGHT, null, values);
        return result != -1;
    }

    // Retrieve all weight records.
    public Cursor getAllWeights() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WEIGHT;
        return db.rawQuery(query, null);
    }

    // Delete a weight record by id.
    public boolean deleteWeight(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_WEIGHT, COLUMN_WEIGHT_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Update a weight record.
    public boolean updateWeight(int id, String date, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WEIGHT_VALUE, weight);
        int result = db.update(TABLE_WEIGHT, values, COLUMN_WEIGHT_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
