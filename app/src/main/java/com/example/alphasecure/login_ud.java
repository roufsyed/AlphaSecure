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

public class login_ud extends AppCompatActivity {
	EditText login_ud_title_et, login_ud_username_et, login_ud_password_et, login_ud_website_et, login_ud_notes_et;
	String login_id, login_title, login_username, login_password, login_website, login_notes;
	CheckBox show_password_cb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_ud);

		//DARK MODE OFF BY DEFAULT
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

		//DISABLE SCREENSHOT IMPLEMENTATION
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

		//BINDING BY IDs
		login_ud_title_et = findViewById(R.id.login_ud_title_et);
		login_ud_username_et = findViewById(R.id.login_ud_username_et);
		login_ud_password_et = findViewById(R.id.login_ud_password_et);
		login_ud_website_et = findViewById(R.id.login_ud_website_et);
		login_ud_notes_et = findViewById(R.id.login_ud_notes_et);
		show_password_cb = findViewById(R.id.show_password_cb);

		//**************************************************************************************************************
		//REVEAL AND CONCEAL PASSWORD IMPLEMENTATION
		//**************************************************************************************************************
		show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					login_ud_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				else{
					login_ud_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

		get_and_set_intent_data();
	}

	void get_and_set_intent_data(){
		if (getIntent().hasExtra("login_id") && getIntent().hasExtra("login_title") &&
				getIntent().hasExtra("login_username") && getIntent().hasExtra("login_password") &&
				getIntent().hasExtra("login_website") && getIntent().hasExtra("login_notes")){
			//GETTING INTENT DATA
			login_id = getIntent().getStringExtra("login_id");
			login_title = getIntent().getStringExtra("login_title");
			login_username = getIntent().getStringExtra("login_username");
			login_password = getIntent().getStringExtra("login_password");
			login_website = getIntent().getStringExtra("login_website");
			login_notes = getIntent().getStringExtra("login_notes");

			//DECRYPTING PASSWORD
			String login_password_et_decrypted = "";
			try {
				login_password_et_decrypted = AESUtils.decrypt( login_password );
				//Log.d(“TEST”, “decrypted:” + decrypted); decrypt_result.setText( decrypted );
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			//SETTING INTENT DATA
			login_ud_title_et.setText(login_title);
			login_ud_username_et.setText(login_username);
			login_ud_password_et.setText(login_password_et_decrypted);
			login_ud_website_et.setText(login_website);
			login_ud_notes_et.setText(login_notes);

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
		switch (item.getItemId())
		{
			case R.id.delete_button:
			{
				confirm_delete();
				break;
			}

			case R.id.update_button:
			{
				update();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	void update() {
		database_helper login_table = new database_helper(login_ud.this);

		//ENCRYPTING PASSWORD
		String login_ud_password_et_encrypted = "";
		try {
			login_ud_password_et_encrypted = AESUtils.encrypt(login_ud_password_et.getText().toString() );
		} catch (Exception e) {
			e.printStackTrace();
		}

		login_title = login_ud_title_et.getText().toString().trim();
		login_username = login_ud_username_et.getText().toString().trim();
		login_password = login_ud_password_et_encrypted;
		login_website = login_ud_website_et.getText().toString().trim();

		login_table.login_update(login_id, login_title, login_username, login_password,
				login_website, login_notes);
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
				database_helper login_table = new database_helper(login_ud.this);
				login_table.login_delete(login_id);

				//TO REFRESH THE LOGIN ACTIVITY
				Intent login_refresh_intent = new Intent(login_ud.this,login.class);
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