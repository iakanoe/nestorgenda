package net.iakanoe.nestorgenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity{
	TextInputLayout txtUser;
	TextInputLayout txtPass;
	Button btnSignup;
	SharedPreferences preferences;
	
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
		setupUI();
	}
	
	void setupUI(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		txtUser = findViewById(R.id.txtUser);
		txtPass = findViewById(R.id.txtPass);
		btnSignup = findViewById(R.id.btnSignup);
		
		txtUser.getEditText().clearComposingText();
		txtPass.getEditText().clearComposingText();
		btnSignup.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){ signUp(); }
		});
	}
	
	void signUp(){
		String user = txtUser.getEditText().getText().toString();
		String pass = txtPass.getEditText().getText().toString();
		if(preferences.contains(user)){
			txtUser.setError("El usuario ya existe");
			txtPass.getEditText().clearComposingText();
		} else {
			preferences.edit().putString(user, pass).apply();
			finish();
		}
	}
}
