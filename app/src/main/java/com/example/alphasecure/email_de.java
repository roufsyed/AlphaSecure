package com.example.alphasecure;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

public class email_de extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    EditText email_title_et, email_username_et, email_password_et, email_website_et, email_phone_et, email_notes_et;
    Button auto_generate_bt;
    CheckBox show_password_cb;
    CardView loginCardViewExpandable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_de);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //emailCardViewExpandable = findViewById(R.id.emailCardViewExpandable);
        //threeDot = findViewById(R.id.threeDot);

        //BINDING BY IDs
        email_title_et = findViewById(R.id.email_title_et);
        email_username_et = findViewById(R.id.email_username_et);
        email_password_et = findViewById(R.id.email_password_et);
        email_website_et = findViewById(R.id.email_website_et);
        email_phone_et = findViewById(R.id.email_phone_et);
        email_notes_et = findViewById(R.id.email_notes_et);
        show_password_cb = findViewById(R.id.show_password_cb);

        //**************************************************************************************************************
        //REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
        //**************************************************************************************************************
        show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    email_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    email_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

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
            database_helper email_table = new database_helper(email_de.this);

            //ENCRYPTING PASSWORD
            String email_password_et_encrypted = "", email_title_et_encrypted = "", email_username_et_encrypted = "",
                    email_website_et_encrypted = "", email_phone_et_encrypted = "", email_notes_et_encrypted = "";
            try {
                email_password_et_encrypted = AESUtils.encrypt(email_password_et.getText().toString());
                email_title_et_encrypted = AESUtils.encrypt(email_title_et.getText().toString());
                email_username_et_encrypted = AESUtils.encrypt(email_username_et.getText().toString());
                email_website_et_encrypted = AESUtils.encrypt(email_website_et.getText().toString());
                email_phone_et_encrypted = AESUtils.encrypt(email_phone_et.getText().toString());
                email_notes_et_encrypted = AESUtils.encrypt(email_notes_et.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            email_table.add_email(email_title_et_encrypted, email_username_et_encrypted, email_password_et_encrypted,
                    email_website_et_encrypted, email_phone_et_encrypted, email_notes_et_encrypted);


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