package com.example.alphasecure;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

public class settings extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//DARK MODE OFF BY DEFAULT
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

		//DISABLE SCREENSHOT IMPLEMENTATION
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
	}

	//**************************************************************************************************************
	//BACK BUTTON CONFIG
	//**************************************************************************************************************
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);
	}
}