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

public class notes extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    database_helper notes_db;
    ArrayList<String> notes_id, notes_title, notes_body;
    notes_adapter notes_adapter;
    ImageView empty_locker_iv;
    TextView empty_locker_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //**************************************************************************************************************
        //RECYCLER VIEW DECLARATION
        //**************************************************************************************************************
        RecyclerView notes_recyclerview;
        notes_recyclerview = findViewById(R.id.notes_recyclerview);

        empty_locker_iv = findViewById(R.id.empty_locker_iv);
        empty_locker_tv = findViewById(R.id.empty_locker_tv);

        //**************************************************************************************************************
        //FLOATING ACTION BAR IMPLEMENTAION
        //**************************************************************************************************************
        FloatingActionButton notes_add_fab = findViewById(R.id.notes_add_fab);
        notes_add_fab.setOnClickListener((View v) -> {
            Intent notes_fab_intent = new Intent(notes.this,notes_de.class);
            startActivity(notes_fab_intent);
        });

        //ARRAY LIST DEFINITIONS
        notes_db = new database_helper(notes.this);
        notes_id = new ArrayList<>();
        notes_title = new ArrayList<>();
        notes_body = new ArrayList<>();

        //FUNCTION CALL
        notes_data_in_arrays();

        //SETTING UP ADAPTER
        notes_adapter = new notes_adapter(notes.this, notes_id, notes_title, notes_body);
        notes_recyclerview.setAdapter(notes_adapter);
        notes_recyclerview.setLayoutManager(new LinearLayoutManager(notes.this));

    }

    //**************************************************************************************************************
    //STORING DATA IN ARRAYS
    //**************************************************************************************************************
    void notes_data_in_arrays(){
        Cursor cursor = notes_db.read_notes();
        if(cursor.getCount() == 0){
            empty_locker_iv.setVisibility(View.VISIBLE);
            empty_locker_tv.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                notes_id.add(cursor.getString(0));
                notes_title.add(cursor.getString(1));
                notes_body.add(cursor.getString(2));
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
        builder.setMessage("Entire Notes will be permanently deleted.");

        builder.setPositiveButton("Delete them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database_helper notes_table = new database_helper(notes.this);
                notes_table.notes_delete_all();

                //TO REFRESH THE notes ACTIVITY
                Intent notes_refresh_intent = new Intent(notes.this,notes.class);
                startActivity(notes_refresh_intent);
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