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

public class bank extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    database_helper bank_db;
    ArrayList<String> bank_id, bank_title, bank_name_of_the_bank, bank_name_on_account,
            bank_account_number, bank_pin, bank_phone, bank_address, bank_notes;
    bank_adapter bank_adapter;
    ImageView empty_locker_iv;
    TextView empty_locker_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //**************************************************************************************************************
        //RECYCLER VIEW DECLARATION
        //**************************************************************************************************************
        RecyclerView bank_recyclerview;
        bank_recyclerview = findViewById(R.id.bank_recyclerview);

        empty_locker_iv = findViewById(R.id.empty_locker_iv);
        empty_locker_tv = findViewById(R.id.empty_locker_tv);

        //**************************************************************************************************************
        //FLOATING ACTION BAR IMPLEMENTAION
        //**************************************************************************************************************
        FloatingActionButton bank_add_fab = findViewById(R.id.bank_add_fab);
        bank_add_fab.setOnClickListener((View v) -> {
            Intent bank_fab_intent = new Intent(bank.this,bank_de.class);
            startActivity(bank_fab_intent);
        });

        //ARRAY LIST DEFINITIONS
        bank_db = new database_helper(bank.this);
        bank_id = new ArrayList<>();
        bank_title = new ArrayList<>();
        bank_name_of_the_bank = new ArrayList<>();
        bank_name_on_account = new ArrayList<>();
        bank_account_number = new ArrayList<>();
        bank_pin = new ArrayList<>();
        bank_phone = new ArrayList<>();
        bank_address = new ArrayList<>();
        bank_notes = new ArrayList<>();

        //FUNCTION CALL
        bank_data_in_arrays();

        //SETTING UP ADAPTER
        bank_adapter = new bank_adapter(bank.this, bank_id, bank_title, bank_name_of_the_bank,
                bank_name_on_account, bank_account_number, bank_pin, bank_phone, bank_address, bank_notes);
        bank_recyclerview.setAdapter(bank_adapter);
        bank_recyclerview.setLayoutManager(new LinearLayoutManager(bank.this));

    }

    //**************************************************************************************************************
    //STORING DATA IN ARRAYS
    //**************************************************************************************************************
    void bank_data_in_arrays(){
        Cursor cursor = bank_db.read_bank();
        if(cursor.getCount() == 0){
            empty_locker_iv.setVisibility(View.VISIBLE);
            empty_locker_tv.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                bank_id.add(cursor.getString(0));
                bank_title.add(cursor.getString(1));
                bank_name_of_the_bank.add(cursor.getString(2));
                bank_name_on_account.add(cursor.getString(3));
                bank_account_number.add(cursor.getString(4));
                bank_pin.add(cursor.getString(5));
                bank_phone.add(cursor.getString(6));
                bank_address.add(cursor.getString(7));
                bank_notes.add(cursor.getString(8));
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
        builder.setMessage("Entire Bank will be permanently deleted.");

        builder.setPositiveButton("Move to Trash", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database_helper bank_table = new database_helper(bank.this);
                bank_table.bank_delete_all();

                //TO REFRESH THE bank ACTIVITY
                Intent bank_refresh_intent = new Intent(bank.this,bank.class);
                startActivity(bank_refresh_intent);
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
    //TO RESTART ACTIVITY
    //**************************************************************************************************************
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}