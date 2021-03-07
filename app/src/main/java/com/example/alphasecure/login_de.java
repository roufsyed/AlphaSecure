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

public class login_de extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    EditText login_title_et, login_username_et, login_password_et, login_website_et, login_notes_et;
    Button auto_generate_bt;
    CheckBox show_password_cb;
    CardView loginCardViewExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_de);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //BINDING BY IDs
        login_title_et = findViewById(R.id.login_title_et);
        login_username_et = findViewById(R.id.login_username_et);
        login_password_et = findViewById(R.id.login_password_et);
        login_website_et = findViewById(R.id.login_website_et);
        login_notes_et = findViewById(R.id.login_notes_et);
        show_password_cb = findViewById(R.id.show_password_cb);

        //**************************************************************************************************************
        //REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
        //**************************************************************************************************************
        show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    login_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    login_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
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

            //ENCRYPTING PASSWORD
            String login_password_et_encrypted = "";
            try {
                login_password_et_encrypted = AESUtils.encrypt(login_password_et.getText().toString() );
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (database_helper login_table = new database_helper(login_de.this)) {
                login_table.add_login(login_title_et.getText().toString().trim(),
                        login_username_et.getText().toString().trim(),
                        login_password_et_encrypted,
                        login_website_et.getText().toString().trim(),
                        login_notes_et.getText().toString().trim());

            }
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