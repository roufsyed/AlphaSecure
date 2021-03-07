package com.example.alphasecure;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

public class bank_de extends AppCompatActivity {

    //**************************************************************************************************************
    //ALL DECLARATIONS
    //**************************************************************************************************************
    EditText bank_title_et, bank_name_of_the_bank_et, bank_name_on_account_et, bank_account_number_et,
            bank_pin_et, bank_phone_et, bank_address_et, bank_notes_et;
    CheckBox show_password_cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_de);

        //DARK MODE OFF BY DEFAULT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //DISABLE SCREENSHOT IMPLEMENTATION
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        bank_title_et = findViewById(R.id.bank_title_et);
        bank_name_of_the_bank_et = findViewById(R.id.bank_name_of_the_bank_et);
        bank_name_on_account_et = findViewById(R.id.bank_name_on_account_et);
        bank_account_number_et = findViewById(R.id.bank_account_number_et);
        bank_pin_et = findViewById(R.id.bank_pin_et);
        bank_phone_et = findViewById(R.id.bank_phone_et);
        bank_address_et = findViewById(R.id.bank_address_et);
        bank_notes_et = findViewById(R.id.bank_notes_et);
        show_password_cb = findViewById(R.id.show_password_cb);

        //**************************************************************************************************************
        //REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
        //**************************************************************************************************************
        show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bank_pin_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    bank_pin_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
            database_helper bank_table = new database_helper(bank_de.this);

            //ENCRYPTING PASSWORD
            String bank_pin_et_encrypted = "", bank_title_et_encrypted = "", bank_name_of_the_bank_et_encrypted = "",
                    bank_name_on_account_et_encrypted = "", bank_account_number_et_encrypted = "", bank_phone_et_encrypted = "",
                    bank_address_et_encrypted = "", bank_notes_et_encrypted = "";
            try {
                bank_title_et_encrypted = AESUtils.encrypt(bank_title_et.getText().toString());
                bank_name_of_the_bank_et_encrypted = AESUtils.encrypt(bank_title_et.getText().toString());
                bank_name_on_account_et_encrypted = AESUtils.encrypt(bank_name_on_account_et.getText().toString());
                bank_account_number_et_encrypted = AESUtils.encrypt(bank_account_number_et.getText().toString());
                bank_pin_et_encrypted = AESUtils.encrypt(bank_pin_et.getText().toString());
                bank_phone_et_encrypted = AESUtils.encrypt(bank_phone_et.getText().toString());
                bank_address_et_encrypted = AESUtils.encrypt(bank_address_et.getText().toString());
                bank_notes_et_encrypted = AESUtils.encrypt(bank_notes_et.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            bank_table.add_bank(bank_title_et_encrypted,
                    bank_name_of_the_bank_et_encrypted,
                    bank_name_on_account_et_encrypted,
                    bank_account_number_et_encrypted,
                    bank_pin_et_encrypted ,
                    bank_phone_et_encrypted,
                    bank_address_et_encrypted,
                    bank_notes_et_encrypted);

        }
            /*bank_table.add_bank(bank_title_et.getText().toString().trim(),
                    bank_name_of_the_bank_et.getText().toString().trim(),
                    bank_name_on_account_et.getText().toString().trim(),
                    bank_account_number_et.getText().toString().trim(),
                    bank_pin_et_encrypted ,
                    bank_phone_et.getText().toString().trim(),
                    bank_address_et.getText().toString().trim(),
                    bank_notes_et.getText().toString().trim());
        }*/
        return super.onOptionsItemSelected(item);
    }

    //**************************************************************************************************************
    //TO RESTART ACTIVITY
    //**************************************************************************************************************
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
