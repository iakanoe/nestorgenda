package net.iakanoe.nestorgenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	static final int REQUEST_ADD = 1;
	static final int REQUEST_EDIT = 2;
	SharedPreferences preferences;
	String user;
	RecyclerView recView;
	EventAdapter adapter;
	
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSupportActionBar((Toolbar) findViewById(R.id.tbMain));
		
		setupRecyclerView(getData());
		refreshUI();
	}
	
	@Override public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main_toolbar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.tbMain_action_add:
				addItem();
				break;
				
			case R.id.tbMain_action_logoff:
				logOff();
				break;
				
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_CANCELED) return;
		
		switch(requestCode){
			case REQUEST_ADD:
				((EventAdapter) recView.getAdapter()).add(new Evento(
					data.getStringExtra("nombre"),
					data.getStringExtra("fecha"),
					data.getStringExtra("descripcion")
				));
				refreshUI();
				break;
				
			case REQUEST_EDIT:
				((EventAdapter) recView.getAdapter()).remove(data.getIntExtra("posicion", -1));
				((EventAdapter) recView.getAdapter()).add(new Evento(
					data.getStringExtra("nombre"),
					data.getStringExtra("fecha"),
					data.getStringExtra("descripcion")
				), data.getIntExtra("posicion", -1));
				refreshUI();
				break;
				
			default: break;
		}
	}
	
	void refreshUI(){
		if(recView.getAdapter().getItemCount() == 0){
			recView.setVisibility(View.GONE);
			findViewById(R.id.txtEmpty).setVisibility(View.VISIBLE);
		} else {
			recView.setVisibility(View.VISIBLE);
			findViewById(R.id.txtEmpty).setVisibility(View.GONE);
		}
		saveData();
	}
	
	void setupRecyclerView(List<Evento> list){
		recView = findViewById(R.id.recView);
		recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		adapter = new EventAdapter(list);
		recView.setAdapter(adapter);
		
		(new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END){
			@Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
				return false;
			}
			
			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
				adapter.remove(viewHolder.getAdapterPosition());
				Snackbar.make(findViewById(R.id.coordinatorLayout), "Eliminado", Snackbar.LENGTH_SHORT)
					.setAction("Deshacer", new View.OnClickListener(){
						@Override
						public void onClick(View v){
							adapter.addLastRemoved();
							refreshUI();
						}
					})
					.show();
				refreshUI();
			}
		})).attachToRecyclerView(recView);
		
		recView.addOnItemTouchListener(new RecyclerItemClickListener(this, recView, new RecyclerItemClickListener.OnItemClickListener(){
			@Override public void onItemClick(View view, int position){
				tryEdit(position);
			}
			
			@Override public void onLongItemClick(View view, int position){}
		}));
	}
	
	List<Evento> getData(){
		user = getIntent().getStringExtra("user");
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		if(!preferences.contains(user)) return new ArrayList<>();
		
		//String data = preferences.getString(user, null);
		//Type tipo = new TypeToken<List<Evento>>(){}.getType();
		
		return new Gson().fromJson(preferences.getString(user, null), new TypeToken<List<Evento>>(){}.getType());
	}
	
	@Override protected void onDestroy(){
		saveData();
		super.onDestroy();
	}
	
	@Override protected void onStop(){
		saveData();
		super.onStop();
	}
	
	void saveData(){
		/*
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		eventoList = ((EventAdapter)recView.getAdapter()).getItems();
		Gson gson = new Gson();
		preferences.edit().putString(user, gson.toJson(eventoList)).apply();*/
		
		getPreferences(Context.MODE_PRIVATE).edit().putString(user, new Gson().toJson(((EventAdapter)recView.getAdapter()).getItems())).apply();
	}
	
	void tryEdit(int position){
		startActivityForResult(new Intent(getApplicationContext(), DetailsActivity.class)
				.putExtra("nombre", ((EventAdapter)recView.getAdapter()).getItems().get(position).getNombre())
				.putExtra("fecha", ((EventAdapter)recView.getAdapter()).getItems().get(position).getFecha())
				.putExtra("descripcion", ((EventAdapter)recView.getAdapter()).getItems().get(position).getDescripcion())
				.putExtra("posicion", position)
			, REQUEST_EDIT);
	}
	
	void addItem(){
		startActivityForResult(new Intent(getApplicationContext(), AddActivity.class), REQUEST_ADD);
	}
	
	void logOff(){
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
		finish();
	}
}
