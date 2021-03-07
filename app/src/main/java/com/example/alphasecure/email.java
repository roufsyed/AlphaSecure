package com.example.alphasecure;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class email extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    database_helper email_db;
    ArrayList<String> email_id, email_title, email_username, email_password, email_website, email_phone, email_notes, email_foreign_key;
    email_adapter email_adapter;
    ImageView empty_locker_iv;
    TextView empty_locker_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //**************************************************************************************************************
        //RECYCLER VIEW DECLARATION
        //**************************************************************************************************************
        RecyclerView email_recyclerview;
        email_recyclerview = findViewById(R.id.email_recyclerview);

        empty_locker_iv = findViewById(R.id.empty_locker_iv);
        empty_locker_tv = findViewById(R.id.empty_locker_tv);

        //**************************************************************************************************************
        //FLOATING ACTION BAR IMPLEMENTAION
        //**************************************************************************************************************
        FloatingActionButton email_add_fab = findViewById(R.id.email_add_fab);
        email_add_fab.setOnClickListener((View v) -> {
            Intent email_fab_intent = new Intent(email.this,email_de.class);
            startActivity(email_fab_intent);
        });

        //ARRAY LIST DEFINITIONS
        email_db = new database_helper(email.this);
        email_id = new ArrayList<>();
        email_title = new ArrayList<>();
        email_username = new ArrayList<>();
        email_password = new ArrayList<>();
        email_website = new ArrayList<>();
        email_phone = new ArrayList<>();
        email_notes = new ArrayList<>();
        email_foreign_key = new ArrayList<>();

        //FUNCTION CALL
        email_data_in_arrays();

        email_adapter = new email_adapter(email.this, email_id, email_title, email_username,
                email_password, email_website, email_phone, email_notes);
        email_recyclerview.setAdapter(email_adapter);
        email_recyclerview.setLayoutManager(new LinearLayoutManager(email.this));

    }

    //**************************************************************************************************************
    //STORING DATA IN ARRAYS
    //**************************************************************************************************************
    void email_data_in_arrays(){
        Cursor cursor = email_db.read_email();
        if(cursor.getCount() == 0){
            empty_locker_iv.setVisibility(View.VISIBLE);
            empty_locker_tv.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                email_id.add(cursor.getString(0));
                email_title.add(cursor.getString(1));
                email_username.add(cursor.getString(2));
                email_password.add(cursor.getString(3));
                email_website.add(cursor.getString(4));
                email_phone.add(cursor.getString(5));
                email_notes.add(cursor.getString(6));
            }
            empty_locker_iv.setVisibility(View.GONE);
            empty_locker_tv.setVisibility(View.GONE);
        }
    }

    //**************************************************************************************************************
    //ACTIONBAR SEARCH AND DELETE ALL BUTTONS
    //**************************************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.delete_all_menu, menu);
        return true;
    }

    //SEARCH AND DELETE ALL ACTION
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_button) {
            Toast.makeText(this, "COMING SOON!", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.delete_all_button){
            confirm_delete_all();
        }
        return super.onOptionsItemSelected(item);
    }

    //**************************************************************************************************************
    //CONFIRMATION FOR DELETE
    //**************************************************************************************************************
    void confirm_delete_all(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Entire Email will be permanently deleted.");

        builder.setPositiveButton("Delete them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database_helper email_table = new database_helper(email.this);
                email_table.email_delete_all();

                //TO REFRESH THE email ACTIVITY
                Intent email_refresh_intent = new Intent(email.this,email.class);
                startActivity(email_refresh_intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    //**************************************************************************************************************
    //BACK BUTTON CONFIG
    //**************************************************************************************************************
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}