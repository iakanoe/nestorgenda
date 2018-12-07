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

public class DetailsActivity extends AppCompatActivity {
	TextInputLayout txtDetNombre;
	TextInputLayout txtDetDescripcion;
	Button btnDetFecha;
	int posicion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		setupToolbar();
		posicion = getIntent().getIntExtra("posicion", -1);
		setupUI();
	}
	
	void setupToolbar(){
		Toolbar toolbar = findViewById(R.id.tbDet);
		setSupportActionBar(toolbar);
	}
	
	@Override public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.add_toolbar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.tbAdd_action_save) trySave();
		return super.onOptionsItemSelected(item);
	}
	
	void trySave(){
		if(txtDetNombre.getEditText().getText().equals("")){
			txtDetNombre.setError("Este campo es obligatorio");
		} else {
			Intent intent = new Intent()
				.putExtra("nombre", txtDetNombre.getEditText().getText().toString())
				.putExtra("fecha", btnDetFecha.getText().toString())
				.putExtra("descripcion", txtDetDescripcion.getEditText().getText().toString())
				.putExtra("posicion", posicion);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	@Override protected void onDestroy(){
		setResult(RESULT_CANCELED);
		super.onDestroy();
	}
	
	@SuppressLint("SetTextI18n")
	void setupUI(){
		txtDetNombre = findViewById(R.id.txtDetNombre);
		txtDetDescripcion = findViewById(R.id.txtDetDescripcion);
		btnDetFecha = findViewById(R.id.btnDetFecha);
		
		txtDetNombre.getEditText().setText(getIntent().getStringExtra("nombre"));
		txtDetDescripcion.getEditText().setText(getIntent().getStringExtra("descripcion"));
		btnDetFecha.setText(getIntent().getStringExtra("fecha"));
		
		btnDetFecha.setOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View view){
				DatePickerDialog d = new DatePickerDialog(DetailsActivity.this);
				int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				int mes = Calendar.getInstance().get(Calendar.MONTH);
				int anio = Calendar.getInstance().get(Calendar.YEAR);
				d.updateDate(anio, mes, dia);
				d.setOnDateSetListener(new DatePickerDialog.OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker datePicker, int y, int m, int d){
						btnDetFecha.setText(String.valueOf(d) + '/' + String.valueOf(m) + '/' + String.valueOf(y));
					}
				});
				d.show();
			}
		});
	}
}
