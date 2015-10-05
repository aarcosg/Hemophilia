package us.idinfor.hemophilia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import us.idinfor.hemophilia.model.Bleed;


public class BleedAdapter extends RecyclerView.Adapter<BleedAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Bleed> bleeds;


    public BleedAdapter(List<Bleed> bleeds) {
        this.bleeds = bleeds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO Inflar layout de los items del RecyclerView. Inicializar ViewHolder
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO Recuperar el objeto Bleed del listado @bleeds que se encuentre en la posición @position.
        //TODO   Añadir la información del objeto recuperado a las vistas que forman parte del layout inflado en @onCreateViewHolder
    }

    @Override
    public int getItemCount() {
        return bleeds.size();
    }

    public void add(Bleed bleed){
        bleeds.add(bleed);
        notifyItemInserted(bleeds.size()-1);
    }

    public void addAll(List<Bleed> bleeds){
        this.bleeds.addAll(bleeds);
        notifyDataSetChanged();
    }

    public void remove(Bleed bleed){
        int position = bleeds.indexOf(bleed);
        bleeds.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        bleeds.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //TODO Lanzar Activity EditBleedActivity
    }

    @Override
    public boolean onLongClick(final View v) {
        //TODO
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        //TODO declarar Views del item

        public ViewHolder(View itemView) {
            //TODO Inicializar Views
            super(itemView);
        }
    }
}
