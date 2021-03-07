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

public class login extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    database_helper login_db;
    ArrayList<String> login_id, login_title, login_username, login_password, login_website, login_notes, login_foreign_key,
            login_favourite;
    login_adapter login_adapter;
    ImageView empty_locker_iv;
    TextView empty_locker_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //**************************************************************************************************************
        //RECYCLER VIEW DECLARATION
        //**************************************************************************************************************
        RecyclerView login_recyclerview;
        login_recyclerview = findViewById(R.id.login_recyclerview);

        empty_locker_iv = findViewById(R.id.empty_locker_iv);
        empty_locker_tv = findViewById(R.id.empty_locker_tv);

        //**************************************************************************************************************
        //FLOATING ACTION BAR IMPLEMENTAION
        //**************************************************************************************************************
        FloatingActionButton login_add_fab = findViewById(R.id.login_add_fab);
        login_add_fab.setOnClickListener((View v) -> {
            Intent login_fab_intent = new Intent(login.this,login_de.class);
            startActivity(login_fab_intent);
        });

        //ARRAY LIST DEFINITIONS
        login_db = new database_helper(login.this);
        login_id = new ArrayList<>();
        login_title = new ArrayList<>();
        login_username = new ArrayList<>();
        login_password = new ArrayList<>();
        login_website = new ArrayList<>();
        login_notes = new ArrayList<>();
        login_foreign_key = new ArrayList<>();
        login_favourite = new ArrayList<>();


        //FUNCTION CALL
        login_data_in_arrays();

        //SETTING UP ADAPTER
        login_adapter = new login_adapter(login.this, login_id, login_title, login_username,
                login_password, login_website, login_notes);
        login_recyclerview.setAdapter(login_adapter);
        login_recyclerview.setLayoutManager(new LinearLayoutManager(login.this));

    }

    //**************************************************************************************************************
    //STORING DATA IN ARRAYS
    //**************************************************************************************************************
    void login_data_in_arrays(){
        Cursor cursor = login_db.read_login();
        if(cursor.getCount() == 0){
            empty_locker_iv.setVisibility(View.VISIBLE);
            empty_locker_tv.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                login_id.add(cursor.getString(0));
                login_title.add(cursor.getString(1));
                login_username.add(cursor.getString(2));
                login_password.add(cursor.getString(3));
                login_website.add(cursor.getString(4));
                login_notes.add(cursor.getString(5));
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
        builder.setMessage("Entire Login will be permanently deleted.");

        builder.setPositiveButton("Delete them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database_helper login_table = new database_helper(login.this);
                login_table.login_delete_all();

                //TO REFRESH THE LOGIN ACTIVITY
                Intent login_refresh_intent = new Intent(login.this,login.class);
                startActivity(login_refresh_intent);
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