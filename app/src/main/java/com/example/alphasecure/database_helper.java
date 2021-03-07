package com.example.alphasecure;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class database_helper extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "alphasecure.db";
    public static final int DATABASE_VERSION = 1;

    public static final String LOGIN_TABLE = "login";
    public static final String LOGIN_ID = "login_id";
    public static final String LOGIN_TITLE = "login_title";
    public static final String LOGIN_USERNAME = "login_username";
    public static final String LOGIN_PASSWORD = "login_password";
    public static final String LOGIN_WEBSITE = "login_website";
    public static final String LOGIN_NOTES = "login_notes";

    public static final String EMAIL_TABLE = "email";
    public static final String EMAIL_ID = "email_id";
    public static final String EMAIL_TITLE = "email_title";
    public static final String EMAIL_USERNAME = "email_username";
    public static final String EMAIL_PASSWORD = "email_password";
    public static final String EMAIL_WEBSITE = "email_website";
    public static final String EMAIL_PHONE = "email_phone";
    public static final String EMAIL_NOTES = "email_notes";

    public static final String BANK_TABLE = "bank";
    public static final String BANK_ID = "bank_id";
    public static final String BANK_TITLE = "bank_title";
    public static final String BANK_NAME_OF_THE_BANK = "bank_name_of_the_bank";
    public static final String BANK_NAME_ON_ACCOUNT = "bank_name_on_account";
    public static final String BANK_ACCOUNT_NUMBER = "bank_account_number";
    public static final String BANK_PIN = "bank_pin";
    public static final String BANK_PHONE = "bank_phone";
    public static final String BANK_ADDRESS = "bank_address";
    public static final String BANK_NOTES = "bank_notes";

    public static final String NOTES_TABLE = "notes";
    public static final String NOTES_ID = "notes_id";
    public static final String NOTES_TITLE = "notes_title";
    public static final String NOTES_BODY = "notes_body";

    public database_helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    String login_query = "CREATE TABLE " + LOGIN_TABLE + " (" +
            LOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LOGIN_TITLE + " TEXT, " +
            LOGIN_USERNAME + " TEXT, " +
            LOGIN_PASSWORD + " TEXT, " +
            LOGIN_WEBSITE + " TEXT, " +
            LOGIN_NOTES + " TEXT);";

    String email_query = "CREATE TABLE " + EMAIL_TABLE + " (" +
            EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EMAIL_TITLE + " TEXT, " +
            EMAIL_USERNAME + " TEXT, " +
            EMAIL_PASSWORD + " TEXT, " +
            EMAIL_WEBSITE + " TEXT, " +
            EMAIL_PHONE + " TEXT, " +
            EMAIL_NOTES + " TEXT);";

    String bank_query = "CREATE TABLE " + BANK_TABLE + " (" +
            BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BANK_TITLE + " TEXT, " +
            BANK_NAME_OF_THE_BANK + " TEXT, " +
            BANK_NAME_ON_ACCOUNT + " TEXT, " +
            BANK_ACCOUNT_NUMBER + " TEXT, " +
            BANK_PIN + " TEXT, " +
            BANK_PHONE + " TEXT, " +
            BANK_ADDRESS + " TEXT, " +
            BANK_NOTES + " TEXT);";

    String notes_query = "CREATE TABLE " + NOTES_TABLE + " (" +
            NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTES_TITLE + " TEXT, " +
            NOTES_BODY + " TEXT);";

    //**************************************************************************************************************************
    //FIRES UP ALL THE TABLE CREATION QUERIES
    //**************************************************************************************************************************
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(login_query);
        db.execSQL(email_query);
        db.execSQL(bank_query);
        db.execSQL(notes_query);
    }

    //**************************************************************************************************************************
    //TO DROP TABLE IF ALREADY EXIST
    //**************************************************************************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EMAIL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BANK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(db);
    }


    //**************************************************************************************************************************
    //PIPELINE TO INSERT DATA FOR VARIOUS CATEGORIES
    //**************************************************************************************************************************
    void add_login(String login_title, String login_username, String login_password, String login_website, String login_notes){
        SQLiteDatabase db = this.getWritableDatabase(); // calling method from object db associated to SQLiteOpenHelper
        ContentValues cv = new ContentValues();

        cv.put(LOGIN_TITLE, login_title);
        cv.put(LOGIN_USERNAME, login_username);
        cv.put(LOGIN_PASSWORD, login_password);
        cv.put(LOGIN_WEBSITE, login_website);
        cv.put(LOGIN_NOTES, login_notes);

        long result = db.insert(LOGIN_TABLE, null, cv);

        if (result == -1){
            Toast.makeText(context,"Failed Insertion",Toast.LENGTH_SHORT).show(); //called "context" from the constructor line 27
        }else {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    void add_email(String email_title, String email_username, String email_password, String email_website,
                   String email_phone, String email_notes){
        SQLiteDatabase db = this.getWritableDatabase(); // calling method from object db associated to SQLiteOpenHelper
        ContentValues cv = new ContentValues();

        cv.put(EMAIL_TITLE, email_title);
        cv.put(EMAIL_USERNAME, email_username);
        cv.put(EMAIL_PASSWORD, email_password);
        cv.put(EMAIL_WEBSITE, email_website);
        cv.put(EMAIL_PHONE, email_phone);
        cv.put(EMAIL_NOTES, email_notes);

        long result = db.insert(EMAIL_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context,"Failed Insertion",Toast.LENGTH_SHORT).show(); //called "context" from the constructor line 27
        }else {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    void add_bank(String bank_title, String bank_name_of_the_bank, String bank_name_on_account, String bank_account_number,
                  String bank_pin, String bank_phone, String bank_address, String bank_notes){
        SQLiteDatabase db = this.getWritableDatabase(); // calling method from object db associated to SQLiteOpenHelper
        ContentValues cv = new ContentValues();

        cv.put(BANK_TITLE, bank_title);
        cv.put(BANK_NAME_OF_THE_BANK, bank_name_of_the_bank);
        cv.put(BANK_NAME_ON_ACCOUNT, bank_name_on_account);
        cv.put(BANK_ACCOUNT_NUMBER, bank_account_number);
        cv.put(BANK_PIN, bank_pin);
        cv.put(BANK_PHONE, bank_phone);
        cv.put(BANK_ADDRESS, bank_address);
        cv.put(BANK_NOTES, bank_notes);

        long result = db.insert(BANK_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context,"Failed Insertion",Toast.LENGTH_SHORT).show(); //called "context" from the constructor line 27
        }else {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    void add_notes(String notes_title, String notes_body){
        SQLiteDatabase db = this.getWritableDatabase(); // calling method from object db associated to SQLiteOpenHelper
        ContentValues cv = new ContentValues();

        cv.put(NOTES_TITLE, notes_title);
        cv.put(NOTES_BODY, notes_body);

        long result = db.insert(NOTES_TABLE, null, cv);

        if (result == -1){
            Toast.makeText(context,"Failed Insertion",Toast.LENGTH_SHORT).show(); //called "context" from the constructor line 27
        }else {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    //**************************************************************************************************************************
    //PIPELINE TO READ DATA FROM VARIOUS TABLES
    //*************************************************************************************************************************
    Cursor read_login(){
        String login_query = "SELECT * FROM " + LOGIN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase(); //Created sqlite db object

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(login_query, null);
        }
        return cursor;
    }

   Cursor read_email(){
        String email_query = "SELECT * FROM " + EMAIL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase(); //Created sqlite db object

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(email_query, null);
        }
        return cursor;
    }

    Cursor read_bank(){
        String email_query = "SELECT * FROM " + BANK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase(); //Created sqlite db object

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(email_query, null);
        }
        return cursor;
    }

    Cursor read_notes(){
        String email_query = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase(); //Created sqlite db object

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(email_query, null);
        }
        return cursor;
    }

    //**************************************************************************************************************************
    //PIPELINE TO UPDATE DATA FROM VARIOUS ACTIVITIES
    //**************************************************************************************************************************

    void login_update(String login_row_id, String login_title, String login_username, String login_password,
                      String login_website, String login_notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(LOGIN_TITLE,login_title);
        cv.put(LOGIN_USERNAME,login_username);
        cv.put(LOGIN_PASSWORD,login_password);
        cv.put(LOGIN_WEBSITE,login_website);
        cv.put(LOGIN_NOTES,login_notes);

        long result = db.update(LOGIN_TABLE, cv, "LOGIN_ID=?", new String[]{login_row_id});

        if(result == -1){
            Toast.makeText(context, "Failed Updation!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void email_update(String email_row_id, String email_title, String email_username, String email_password,
                      String email_website, String email_phone, String email_notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EMAIL_TITLE,email_title);
        cv.put(EMAIL_USERNAME,email_username);
        cv.put(EMAIL_PASSWORD,email_password);
        cv.put(EMAIL_WEBSITE,email_website);
        cv.put(EMAIL_PHONE,email_phone);
        cv.put(EMAIL_NOTES,email_notes);

        long result = db.update(EMAIL_TABLE, cv, "EMAIL_ID=?", new String[]{email_row_id});

        if(result == -1){
            Toast.makeText(context, "Failed Updation!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void bank_update(String bank_row_id, String bank_title, String bank_name_of_the_bank, String bank_name_on_account,
                     String bank_account_number, String bank_pin, String bank_phone, String bank_address, String bank_notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BANK_TITLE, bank_title);
        cv.put(BANK_NAME_OF_THE_BANK, bank_name_of_the_bank);
        cv.put(BANK_NAME_ON_ACCOUNT, bank_name_on_account);
        cv.put(BANK_ACCOUNT_NUMBER, bank_account_number);
        cv.put(BANK_PIN, bank_pin);
        cv.put(BANK_PHONE, bank_phone);
        cv.put(BANK_ADDRESS, bank_address);
        cv.put(BANK_NOTES, bank_notes);

        long result = db.update(BANK_TABLE, cv, "BANK_ID=?", new String[]{bank_row_id});

        if(result == -1){
            Toast.makeText(context, "Failed Updation!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void notes_update(String notes_row_id, String notes_title, String notes_body){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTES_TITLE, notes_title);
        cv.put(NOTES_BODY, notes_body);

        long result = db.update(NOTES_TABLE, cv, "NOTES_ID=?", new String[]{notes_row_id});

        if(result == -1){
            Toast.makeText(context, "Failed Updation!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    //**************************************************************************************************************************
    //PIPELINE TO DELETE RESPECTIVE DATA FROM VARIOUS TABLES
    //**************************************************************************************************************************

    void login_delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(LOGIN_TABLE, "LOGIN_ID = ?", new String[] {row_id});
        if (result == -1){
            Toast.makeText(context, "Deletion Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void email_delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(EMAIL_TABLE, "EMAIL_ID = ?", new String[] {row_id});
        if (result == -1){
            Toast.makeText(context, "Deletion Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void bank_delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(BANK_TABLE, "BANK_ID = ?", new String[] {row_id});
        if (result == -1){
            Toast.makeText(context, "Deletion Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void notes_delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(NOTES_TABLE, "NOTES_ID = ?", new String[] {row_id});
        if (result == -1){
            Toast.makeText(context, "Deletion Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    //**************************************************************************************************************************
    //PIPELINE TO DELETE ALL THE DATA FROM VARIOUS TABLES
    //**************************************************************************************************************************Fall

    void login_delete_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + LOGIN_TABLE);
        db.execSQL("DROP TABLE " + LOGIN_TABLE);
        db.execSQL(login_query);
    }

    void email_delete_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + EMAIL_TABLE);
        db.execSQL("DROP TABLE " + EMAIL_TABLE);
        db.execSQL(email_query);

    }

    void bank_delete_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + BANK_TABLE);
        db.execSQL("DROP TABLE " + BANK_TABLE);
        db.execSQL(bank_query);
    }

    void notes_delete_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + NOTES_TABLE);
        db.execSQL("DROP TABLE " + NOTES_TABLE);
        db.execSQL(notes_query);
    }

}
