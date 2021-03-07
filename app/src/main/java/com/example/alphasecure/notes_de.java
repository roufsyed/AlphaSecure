package com.example.alphasecure;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

public class notes_de extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    EditText notes_title_et, notes_body_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_de);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //BINDING BY IDs
        notes_title_et = findViewById(R.id.notes_title_et);
        notes_body_et = findViewById(R.id.notes_body_et);
    }

    //**************************************************************************************************************
    //ACTIONBAR BUTTONS
    //**************************************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.save_menu, menu);
        return true;
    }

    //SAVE ACTION
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_button) {

            //ENCRYPTING PASSWORD
            String notes_body_et_encrypted = "", notes_title_et_encrypted = "";
            try {
                notes_title_et_encrypted = AESUtils.encrypt(notes_title_et.getText().toString());
                notes_body_et_encrypted = AESUtils.encrypt(notes_body_et.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            database_helper notes_table = new database_helper(notes_de.this);
            notes_table.add_notes(notes_title_et_encrypted, notes_body_et_encrypted);

        }
        return super.onOptionsItemSelected(item);
    }

    //**************************************************************************************************************
    //BACK BUTTON CONFIG
    //**************************************************************************************************************
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}