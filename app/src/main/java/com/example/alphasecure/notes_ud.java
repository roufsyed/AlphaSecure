package com.example.alphasecure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

public class notes_ud extends AppCompatActivity {
	EditText notes_ud_title_et, notes_ud_body_et;
	String notes_id, notes_title, notes_body;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes_ud);

		//DARK MODE OFF BY DEFAULT
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

		//DISABLE SCREENSHOT IMPLEMENTATION
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

		notes_ud_title_et = findViewById(R.id.notes_ud_title_et);
		notes_ud_body_et = findViewById(R.id.notes_ud_body_et);

		get_and_set_intent_data();
	}

	//**************************************************************************************************************
	//STRING INITIALIZATION (GETTER AND SETTER)
	//**************************************************************************************************************
	void get_and_set_intent_data(){
		if (getIntent().hasExtra("notes_id") && getIntent().hasExtra("notes_title") &&
				getIntent().hasExtra("notes_body")){
			//GETTING INTENT DATA
			notes_id = getIntent().getStringExtra("notes_id");
			notes_title = getIntent().getStringExtra("notes_title");
			notes_body = getIntent().getStringExtra("notes_body");

			//DECRYPTING PASSWORD
			String notes_ud_title_et_decrypted = "", notes_ud_body_et_decrypted = "";
			try {
				notes_ud_title_et_decrypted = AESUtils.decrypt( notes_title );
				notes_ud_body_et_decrypted = AESUtils.decrypt( notes_body );
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			//SETTING INTENT DATA
			notes_ud_title_et.setText(notes_ud_title_et_decrypted);
			notes_ud_body_et.setText(notes_ud_body_et_decrypted);

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

			case R.id.update_button: {
				notes_update();
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
				database_helper notes_table = new database_helper(notes_ud.this);
				notes_table.notes_delete(notes_id);

				//TO REFRESH THE notes ACTIVITY
				Intent notes_refresh_intent = new Intent(notes_ud.this,notes.class);
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

	void notes_update(){
		database_helper notes_table = new database_helper(notes_ud.this);

		//ENCRYPTING PASSWORD
		String notes_ud_body_et_encrypted = "", notes_ud_title_et_encrypted = "";
		try {
			notes_ud_title_et_encrypted = AESUtils.encrypt(notes_ud_title_et.getText().toString());
			notes_ud_body_et_encrypted = AESUtils.encrypt(notes_ud_body_et.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		notes_title = notes_ud_title_et_encrypted;
		notes_body = notes_ud_body_et_encrypted;

		notes_table.notes_update(notes_id, notes_title, notes_body);
	}
	//**************************************************************************************************************
	//BACK BUTTON CONFIG
	//**************************************************************************************************************
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
	}

}