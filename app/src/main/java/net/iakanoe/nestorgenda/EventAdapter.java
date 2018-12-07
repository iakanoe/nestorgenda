package net.iakanoe.nestorgenda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{
	private List<Evento> list;
	private Evento lastRemoved;
	private int lastRemovedPosition;
	
	EventAdapter(List<Evento> list){
		this.list = list;
	}
	
	void remove(int position){
		lastRemoved = list.get(position);
		lastRemovedPosition = position;
		list.remove(position);
		notifyItemRemoved(position);
	}
	
	void add(Evento evento, int position){
		list.add(position, evento);
		notifyItemInserted(position);
	}
	
	void add(Evento evento){
		list.add(evento);
		notifyItemInserted(list.indexOf(evento));
	}
	
	void addLastRemoved(){
		add(lastRemoved, lastRemovedPosition);
	}
	
	List<Evento> getItems(){
		return list;
	}
	
	@Override public void onBindViewHolder(@NonNull EventHolder holder, int position){
		holder.setEvento(list.get(position));
	}
	
	@Override public int getItemCount(){
		return list.size();
	}
	
	@NonNull @Override public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
		return new EventHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evento, parent, false));
	}
	
	class EventHolder extends RecyclerView.ViewHolder {
		EventHolder(View itemView){
			super(itemView);
		}
		
		void setEvento(Evento e){
			((TextView) itemView.findViewById(R.id.txtNombre)).setText(e.getNombre());
			((TextView) itemView.findViewById(R.id.txtFecha)).setText(e.getFecha());
		}
	}
}
