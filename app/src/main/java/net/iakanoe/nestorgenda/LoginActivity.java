package net.iakanoe.nestorgenda;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;
import java.util.prefs.Preferences;

public class LoginActivity extends AppCompatActivity{
	TextInputLayout txtUser;
	TextInputLayout txtPass;
	Button btnLogin;
	Button btnSignup;
	SharedPreferences preferences;
	
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
		setupUI();
	}
	
	void setupUI(){
		txtUser = findViewById(R.id.txtUser);
		txtPass = findViewById(R.id.txtPass);
		btnLogin = findViewById(R.id.btnLogin);
		btnSignup = findViewById(R.id.btnSignup);
		
		txtUser.getEditText().clearComposingText();
		txtPass.getEditText().clearComposingText();
		btnLogin.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				tryLogin();
			}
		});
		btnSignup.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){ startActivity(new Intent(getApplicationContext(), SignupActivity.class)); }
		});
	}
	
	void tryLogin(){
		String user = txtUser.getEditText().getText().toString();
		String pass = txtPass.getEditText().getText().toString();
		
		if(!preferences.contains(user)){
			txtUser.setError("El usuario no existe");
			txtPass.getEditText().clearComposingText();
			return;
		}
		
		if(!pass.equals(preferences.getString(user, null))){
			txtPass.setError("La contraseña es incorrecta");
			txtPass.getEditText().clearComposingText();
			return;
		}
	
		startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("user", user));
		finish();
	}
}
