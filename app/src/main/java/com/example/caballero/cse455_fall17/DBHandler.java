package com.example.caballero.cse455_fall17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    //version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "profinfo";
    // Contacts table name
    private static final String TABLE_PROF = "prof";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_PROF + "("
        + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_PHONE
        + " TEXT, " + KEY_EMAIL + " TEXT)";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PROF);
        onCreate(db);
    }

    public void addProf(ProfessorInfo professorInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, professorInfo.getName());
        values.put(KEY_PHONE, professorInfo.getPhone());
        values.put(KEY_EMAIL, professorInfo.getEmail());
        db.insert(TABLE_PROF, null, values);
        db.close();
    }

    public ProfessorInfo getProf(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROF, new String[] {KEY_ID,
            KEY_NAME, KEY_PHONE, KEY_EMAIL}, KEY_ID + "=?",
            new String[] {String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        ProfessorInfo contact = new ProfessorInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2), cursor.getString(3));
        return contact;
    }


    public List<ProfessorInfo> getAllProf() {
        List<ProfessorInfo> profList = new ArrayList<ProfessorInfo>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PROF;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProfessorInfo professorInfo = new ProfessorInfo();
                professorInfo.setId(Integer.parseInt(cursor.getString(0)));
                professorInfo.setName(cursor.getString(1));
                professorInfo.setPhone(cursor.getString(2));
                professorInfo.setEmail(cursor.getString(3));
                // Adding contact to list
                profList.add(professorInfo);
            } while (cursor.moveToNext());
        }
        // return contact list
        return profList;
    }

    // Getting shops Count
    public int getProfCount() {
        String countQuery = "SELECT * FROM " + TABLE_PROF;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateProf(ProfessorInfo professorInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, professorInfo.getName());
        values.put(KEY_PHONE, professorInfo.getPhone());
        values.put(KEY_EMAIL, professorInfo.getEmail());
        // updating row
        return db.update(TABLE_PROF, values, KEY_ID + " = ?",
                new String[]{String.valueOf(professorInfo.getId())});
    }

    // Deleting a shop
    public void deleteProf(ProfessorInfo professorInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROF, KEY_ID + " = ?",
                new String[] { String.valueOf(professorInfo.getId())});
        db.close();
    }
}
