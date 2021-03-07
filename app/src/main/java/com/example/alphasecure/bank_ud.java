package com.example.alphasecure;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

public class bank_ud extends AppCompatActivity {
	//**************************************************************************************************************
	//ALL DECLARATIONS
	//**************************************************************************************************************
	EditText bank_ud_title_et, bank_ud_name_of_the_bank_et, bank_ud_name_on_account_et, bank_ud_account_number_et,
			bank_ud_pin_et, bank_ud_phone_et, bank_ud_address_et, bank_ud_notes_et;

	String bank_id, bank_title, bank_name_of_the_bank, bank_name_on_account, bank_account_number,
			bank_pin, bank_phone, bank_address, bank_notes;

	CheckBox show_password_cb;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_ud);

		//DARK MODE OFF BY DEFAULT
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

		//DISABLE SCREENSHOT IMPLEMENTATION
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

		bank_ud_title_et = findViewById(R.id.bank_ud_title_et);
		bank_ud_name_of_the_bank_et = findViewById(R.id.bank_ud_name_of_the_bank_et);
		bank_ud_name_on_account_et = findViewById(R.id.bank_ud_name_on_account_et);
		bank_ud_account_number_et = findViewById(R.id.bank_ud_account_number_et);
		bank_ud_pin_et = findViewById(R.id.bank_ud_pin_et);
		bank_ud_phone_et = findViewById(R.id.bank_ud_phone_et);
		bank_ud_address_et = findViewById(R.id.bank_ud_address_et);
		bank_ud_notes_et = findViewById(R.id.bank_ud_notes_et);
		show_password_cb = findViewById(R.id.show_password_cb);

		//**************************************************************************************************************
		//REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
		//**************************************************************************************************************
		show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					bank_ud_pin_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				else{
					bank_ud_pin_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

		get_and_set_intent_data();
	}

	//**************************************************************************************************************
	//STRING INITIALIZATION (GETTER AND SETTER)
	//**************************************************************************************************************
	void get_and_set_intent_data(){
		if (getIntent().hasExtra("bank_id") && getIntent().hasExtra("bank_title") &&
				getIntent().hasExtra("bank_name_of_the_bank") && getIntent().hasExtra("bank_name_on_account") &&
				getIntent().hasExtra("bank_account_number") && getIntent().hasExtra("bank_pin") &&
				getIntent().hasExtra("bank_phone") && getIntent().hasExtra("bank_address") &&
				getIntent().hasExtra("bank_notes")){

			//GETTING INTENT DATA
			bank_id = getIntent().getStringExtra("bank_id");
			bank_title = getIntent().getStringExtra("bank_title");
			bank_name_of_the_bank = getIntent().getStringExtra("bank_name_of_the_bank");
			bank_name_on_account = getIntent().getStringExtra("bank_name_on_account");
			bank_account_number = getIntent().getStringExtra("bank_account_number");
			bank_pin = getIntent().getStringExtra("bank_pin");
			bank_phone = getIntent().getStringExtra("bank_phone");
			bank_address = getIntent().getStringExtra("bank_address");
			bank_notes = getIntent().getStringExtra("bank_notes");

			//DECRYPTING PASSWORD
			String bank_title_decrypted = "", bank_name_of_the_bank_decrypted = "", bank_name_on_account_decrypted = "",
					bank_account_number_decrypted = "", bank_pin_et_decrypted = "", bank_phone_decrypted = "",
					bank_address_decrypted = "", bank_notes_decrypted = "";

			try {
				bank_title_decrypted = AESUtils.decrypt(bank_title);
				bank_name_of_the_bank_decrypted = AESUtils.decrypt(bank_name_of_the_bank);
				bank_name_on_account_decrypted = AESUtils.decrypt(bank_name_on_account);
				bank_account_number_decrypted = AESUtils.decrypt(bank_account_number);
				bank_pin_et_decrypted = AESUtils.decrypt(bank_pin);
				bank_phone_decrypted = AESUtils.decrypt(bank_phone);
				bank_address_decrypted = AESUtils.decrypt(bank_address);
				bank_notes_decrypted = AESUtils.decrypt(bank_notes);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			//SETTING INTENT DATA
			bank_ud_title_et.setText(bank_title_decrypted);
			bank_ud_name_of_the_bank_et.setText(bank_name_of_the_bank_decrypted);
			bank_ud_name_on_account_et.setText(bank_name_on_account_decrypted);
			bank_ud_account_number_et.setText(bank_account_number_decrypted);
			bank_ud_pin_et.setText(bank_pin_et_decrypted);
			bank_ud_phone_et.setText(bank_phone_decrypted);
			bank_ud_address_et.setText(bank_address_decrypted);
			bank_ud_notes_et.setText(bank_notes_decrypted);

		}else{
			Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
		}
	}

	//**************************************************************************************************************
	//ACTIONBAR BUTTONS (UPDATE, DELETE AND FAVOURITE)
	//**************************************************************************************************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.update_delete_menu, menu);
		return true;
	}

	//UPDATE, DELETE AND FAVOURITE ACTION
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		//DELETION OF ROW
		switch (item.getItemId())
		{
			case R.id.delete_button:
			{
				confirm_delete();
				break;
			}

			case R.id.update_button:
			{
				bank_update();
				break;
			}

		}

		return super.onOptionsItemSelected(item);
	}



	//**************************************************************************************************************
	//CONFIRMATION FOR DELETE
	//**************************************************************************************************************
	void confirm_delete(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Are you sure?");
		builder.setMessage("It will be deleted permanently.");

		builder.setPositiveButton("Delete it", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				database_helper bank_table = new database_helper(bank_ud.this);
				bank_table.bank_delete(bank_id);

				//TO REFRESH THE bank ACTIVITY
				Intent bank_refresh_intent = new Intent(bank_ud.this,bank.class);
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

	void bank_update(){
		database_helper bank_table = new database_helper(bank_ud.this);


		//ENCRYPTING PASSWORD
		String bank_ud_pin_et_encrypted = "", bank_ud_title_et_encrypted = "", bank_ud_name_of_the_bank_ud_et_encrypted = "",
				bank_ud_name_on_account_et_encrypted = "", bank_ud_account_number_et_encrypted = "", bank_ud_phone_et_encrypted = "",
				bank_ud_address_et_encrypted = "", bank_ud_notes_et_encrypted = "";
		try {
			bank_ud_title_et_encrypted = AESUtils.encrypt(bank_ud_title_et.getText().toString());
			bank_ud_name_of_the_bank_ud_et_encrypted = AESUtils.encrypt(bank_ud_title_et.getText().toString());
			bank_ud_name_on_account_et_encrypted = AESUtils.encrypt(bank_ud_name_on_account_et.getText().toString());
			bank_ud_account_number_et_encrypted = AESUtils.encrypt(bank_ud_account_number_et.getText().toString());
			bank_ud_pin_et_encrypted = AESUtils.encrypt(bank_ud_pin_et.getText().toString());
			bank_ud_phone_et_encrypted = AESUtils.encrypt(bank_ud_phone_et.getText().toString());
			bank_ud_address_et_encrypted = AESUtils.encrypt(bank_ud_address_et.getText().toString());
			bank_ud_notes_et_encrypted = AESUtils.encrypt(bank_ud_notes_et.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//INITIALIZATION
		bank_title = bank_ud_title_et_encrypted;
		bank_name_of_the_bank = bank_ud_name_of_the_bank_ud_et_encrypted;
		bank_name_on_account = bank_ud_name_on_account_et_encrypted;
		bank_account_number = bank_ud_account_number_et_encrypted;
		bank_pin = bank_ud_pin_et_encrypted;
		bank_phone = bank_ud_phone_et_encrypted;
		bank_address = bank_ud_address_et_encrypted;
		bank_notes = bank_ud_notes_et_encrypted;


		bank_table.bank_update(bank_id, bank_title, bank_name_of_the_bank, bank_name_on_account,
				bank_account_number, bank_pin, bank_phone, bank_address, bank_notes);

	}

	//**************************************************************************************************************
	//TO RESTART ACTIVITY
	//**************************************************************************************************************
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
	}


}