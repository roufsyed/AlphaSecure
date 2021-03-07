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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

public class email_ud extends AppCompatActivity {
	EditText email_ud_title_et, email_ud_username_et, email_ud_password_et, email_ud_website_et, email_ud_phone_et, email_ud_notes_et;
	String email_id, email_title, email_username, email_password, email_website, email_phone, email_notes;
	CheckBox show_password_cb;
	Button auto_generate_bt;
	CardView loginCardViewExpandable;
	boolean isauto_generate_btOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email_ud);

		//DARK MODE OFF BY DEFAULT
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

		//DISABLE SCREENSHOT IMPLEMENTATION
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


		email_ud_title_et = findViewById(R.id.email_ud_title_et);
		email_ud_username_et = findViewById(R.id.email_ud_username_et);
		email_ud_password_et = findViewById(R.id.email_ud_password_et);
		email_ud_website_et = findViewById(R.id.email_ud_website_et);
		email_ud_phone_et = findViewById(R.id.email_ud_phone_et);
		email_ud_notes_et = findViewById(R.id.email_ud_notes_et);
		show_password_cb = findViewById(R.id.show_password_cb);

		//**************************************************************************************************************
		//REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
		//**************************************************************************************************************
		show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					email_ud_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				else{
					email_ud_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

		get_and_set_intent_data();
	}

	//**************************************************************************************************************
	//STRING INITIALIZATION (GETTER AND SETTER)
	//**************************************************************************************************************
	void get_and_set_intent_data(){
		if (getIntent().hasExtra("email_id") && getIntent().hasExtra("email_title") &&
				getIntent().hasExtra("email_username") && getIntent().hasExtra("email_password") &&
				getIntent().hasExtra("email_website") && getIntent().hasExtra("email_notes")){
			//GETTING INTENT DATA
			email_id = getIntent().getStringExtra("email_id");
			email_title = getIntent().getStringExtra("email_title");
			email_username = getIntent().getStringExtra("email_username");
			email_password = getIntent().getStringExtra("email_password");
			email_website = getIntent().getStringExtra("email_website");
			email_phone = getIntent().getStringExtra("email_phone");
			email_notes = getIntent().getStringExtra("email_notes");

			//DECRYPTING PASSWORD
			String email_ud_password_et_decrypted = "", email_ud_title_et_decrypted = "", email_ud_username_et_decrypted = "",  email_ud_website_et_decrypted = "",
					email_ud_phone_et_decrypted = "", email_ud_notes_et_decrypted = "";
			try {
				email_ud_password_et_decrypted = AESUtils.decrypt(email_password);
				email_ud_title_et_decrypted = AESUtils.decrypt(email_title);
				email_ud_username_et_decrypted = AESUtils.decrypt(email_username);
				email_ud_website_et_decrypted = AESUtils.decrypt(email_website);
				email_ud_phone_et_decrypted = AESUtils.decrypt(email_phone);
				email_ud_notes_et_decrypted = AESUtils.decrypt(email_notes);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			//SETTING INTENT DATA
			email_ud_title_et.setText(email_ud_title_et_decrypted);
			email_ud_username_et.setText(email_ud_username_et_decrypted);
			email_ud_password_et.setText(email_ud_password_et_decrypted);
			email_ud_website_et.setText(email_ud_website_et_decrypted);
			email_ud_phone_et.setText(email_ud_phone_et_decrypted);
			email_ud_notes_et.setText(email_ud_notes_et_decrypted);
		}else{
			Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
		}
	}

	//**************************************************************************************************************
	//ACTIONBAR BUTTONS (UPDATE, DELETE)
	//**************************************************************************************************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.update_delete_menu, menu);
		return true;
	}

	//UPDATE, DELETE ACTION
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.delete_button:
			{
				confirm_delete();
				break;
			}

			case R.id.update_button:
			{
				email_update();
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
				database_helper email_table = new database_helper(email_ud.this);
				email_table.email_delete(email_id);

				//TO REFRESH THE email ACTIVITY
				Intent email_refresh_intent = new Intent(email_ud.this,email.class);
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
	//UPDATE FUNCTION
	//**************************************************************************************************************
	void email_update(){
		database_helper email_table = new database_helper(email_ud.this);

		//ENCRYPTING PASSWORD
		String email_ud_password_et_encrypted = "", email_ud_title_et_encrypted = "", email_ud_username_et_encrypted = "",  email_ud_website_et_encrypted = "",
				email_ud_phone_et_encrypted = "", email_ud_notes_et_encrypted = "";
		try {
			email_ud_password_et_encrypted = AESUtils.encrypt(email_ud_password_et.getText().toString());
			email_ud_title_et_encrypted = AESUtils.encrypt(email_ud_title_et.getText().toString());
			email_ud_username_et_encrypted = AESUtils.encrypt(email_ud_username_et.getText().toString());
			email_ud_website_et_encrypted = AESUtils.encrypt(email_ud_website_et.getText().toString());
			email_ud_phone_et_encrypted = AESUtils.encrypt(email_ud_phone_et.getText().toString());
			email_ud_notes_et_encrypted = AESUtils.encrypt(email_ud_notes_et.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		email_title = email_ud_title_et_encrypted;
		email_username = email_ud_username_et_encrypted;
		email_password = email_ud_password_et_encrypted;
		email_website = email_ud_website_et_encrypted;
		email_phone = email_ud_phone_et_encrypted;
		email_notes = email_ud_notes_et_encrypted;

		email_table.email_update(email_id, email_title, email_username, email_password,
				email_website, email_phone, email_notes);
	}
	//**************************************************************************************************************
	//BACK BUTTON CONFIG
	//**************************************************************************************************************
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
	}

}