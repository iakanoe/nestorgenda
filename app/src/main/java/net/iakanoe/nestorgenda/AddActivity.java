package net.iakanoe.nestorgenda;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;
import java.util.Timer;

public class AddActivity extends AppCompatActivity {
	TextInputLayout txtAddNombre;
	TextInputLayout txtAddDescripcion;
	Button btnAddFecha;
	
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		setupToolbar();
		setupUI();
	}
	
	@Override public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.add_toolbar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	void setupToolbar(){
		Toolbar toolbar = findViewById(R.id.tbAdd);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.tbAdd_action_save) trySave();
		return super.onOptionsItemSelected(item);
	}
	
	@Override protected void onDestroy(){
		setResult(RESULT_CANCELED);
		super.onDestroy();
	}
	
	void trySave(){
		if(txtAddNombre.getEditText().getText().toString().equals("")){
			txtAddNombre.setError("Este campo es obligatorio");
		} else {
			Intent intent = new Intent()
				.putExtra("nombre", txtAddNombre.getEditText().getText().toString())
				.putExtra("fecha", btnAddFecha.getText().toString())
				.putExtra("descripcion", txtAddDescripcion.getEditText().getText().toString());
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	@SuppressLint("SetTextI18n")
	void setupUI(){
		txtAddNombre = findViewById(R.id.txtAddNombre);
		txtAddDescripcion = findViewById(R.id.txtAddDescripcion);
		btnAddFecha = findViewById(R.id.btnAddFecha);
		
		int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int mes = Calendar.getInstance().get(Calendar.MONTH);
		int anio = Calendar.getInstance().get(Calendar.YEAR);
		btnAddFecha.setText(String.valueOf(dia) + '/' + String.valueOf(mes) + '/' + String.valueOf(anio));
		
		btnAddFecha.setOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View view){
				DatePickerDialog d = new DatePickerDialog(AddActivity.this);
				int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				int mes = Calendar.getInstance().get(Calendar.MONTH);
				int anio = Calendar.getInstance().get(Calendar.YEAR);
				d.updateDate(anio, mes, dia);
				d.setOnDateSetListener(new DatePickerDialog.OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker datePicker, int y, int m, int d){
						btnAddFecha.setText(String.valueOf(d) + '/' + String.valueOf(m) + '/' + String.valueOf(y));
					}
				});
				d.show();
			}
		});
	}
}
