package com.example.alphasecure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class homescreen extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    RelativeLayout login_rl, bank_rl, email_rl, notes_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //**************************************************************************************************************
        //ACTIONBAR LOGO IMPLEMENTATION
        //**************************************************************************************************************
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.alphasecure_ic);
        actionBar.setDisplayUseLogoEnabled(true);

        //**************************************************************************************************************
        //PIPELINE TO OPEN VARIOUS ACTIVITIES
        //**************************************************************************************************************
        login_rl=findViewById(R.id.login_rl);
        login_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(homescreen.this,login.class);
                startActivity(login_intent);
            }
        });

        bank_rl=findViewById(R.id.bank_rl);
        bank_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bank_intent = new Intent(homescreen.this,bank.class);
                startActivity(bank_intent);
            }
        });

        email_rl=findViewById(R.id.email_rl);
        email_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_intent = new Intent(homescreen.this,email.class);
                startActivity(email_intent);
            }
        });

        notes_rl=findViewById(R.id.notes_rl);
        notes_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notes_intent = new Intent(homescreen.this, notes.class);
                startActivity(notes_intent);
            }
        });


        //**************************************************************************************************************
        //BOTTOM NAVBAR IMPLEMENTATION
        //**************************************************************************************************************
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navbar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.settings){
                    Toast.makeText(homescreen.this, "COMING SOON!", Toast.LENGTH_SHORT).show();
                    Intent settings_intent = new Intent(homescreen.this, settings.class);
                    startActivity(settings_intent);
                }
                /*switch (item.getItemId())
                {
                    case R.id.settings:
                    {
                        Toast.makeText(homescreen.this, "COMING SOON!", Toast.LENGTH_SHORT).show();
                        Intent settings_intent = new Intent(homescreen.this, settings.class);
                        startActivity(settings_intent);
                        break;
                    }
                }*/
                return false;

            }
        });


        //**************************************************************************************************************
        //FLOATING ACTION BAR + BOTTOMSHEET IMPLEMENTAION
        //**************************************************************************************************************
        FloatingActionButton add_fab = findViewById(R.id.add_bv);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(homescreen.this, R.style.BottomSheetDialogtheme);

                View bottomSheetView = getLayoutInflater().from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_layout,
                                (LinearLayout)findViewById(R.id.bottomsheetcontainer)
                        );

                bottomSheetView.findViewById(R.id.login_de_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent login_de_intent = new Intent(homescreen.this,login_de.class);
                        startActivity(login_de_intent);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.email_de_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent email_de_intent = new Intent(homescreen.this,email_de.class);
                        startActivity(email_de_intent);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.bank_de_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent bank_de_intent = new Intent(homescreen.this, bank_de.class);
                        startActivity(bank_de_intent);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.notes_de_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent notes_de_intent = new Intent(homescreen.this,notes_de.class);
                        startActivity(notes_de_intent);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });
    }

   /* //**************************************************************************************************************
    //ACTIONBAR BUTTONS
    //**************************************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.search_all_menu, menu);
        return true;
    }

    //SEARCH ALL ACTION
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_all_button) {
            Toast.makeText(this, "COMING SOON!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/

    //**************************************************************************************************************
    //CONFIRMATION FOR DELETE
    //**************************************************************************************************************
    void confirm_exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to Exit?");
        builder.setMessage("AlphaSecure will be closed");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        confirm_exit();
    }

    //**************************************************************************************************************
    //HOME BUTTON CONFIG
    //**************************************************************************************************************


}