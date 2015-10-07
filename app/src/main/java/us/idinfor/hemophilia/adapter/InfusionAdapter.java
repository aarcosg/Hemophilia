package us.idinfor.hemophilia.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import us.idinfor.hemophilia.R;
import us.idinfor.hemophilia.activity.EditInfusionActivity;
import us.idinfor.hemophilia.asynctask.DeleteInfusionAsyncTask;
import us.idinfor.hemophilia.backend.infusionApi.model.Infusion;


public class InfusionAdapter extends RecyclerView.Adapter<InfusionAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Infusion> infusions;


    public InfusionAdapter(List<Infusion> infusions) {
        this.infusions = infusions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout de los elementos del adaptador y seleccionar los listeners de los eventos a los que responderá
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.infusion_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /* Esta función se llama por cada elemento del adaptador.
        *  Inicializar las Views del elemento del adaptador con los datos del modelo que se está representando.
        * */
        Infusion infusion = infusions.get(position);
        holder.mMedication.setText(infusion.getMedication());
        holder.mDose.setText(String.format("%s",infusion.getDose()));
        holder.mLotNumber.setText(infusion.getLotNumber());
        holder.mTime.setText(DateUtils.getRelativeTimeSpanString(infusion.getTime().getValue(),System.currentTimeMillis(),DateUtils.DAY_IN_MILLIS));
        holder.itemView.setTag(infusion);
    }

    @Override
    public int getItemCount() {
        return infusions.size();
    }

    public void add(Infusion infusion){
        infusions.add(infusion);
        notifyItemInserted(infusions.size()-1);
    }

    public void addAll(List<Infusion> infusions){
        this.infusions.addAll(infusions);
        notifyDataSetChanged();
    }

    public void remove(Infusion infusion){
        int position = infusions.indexOf(infusion);
        infusions.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        infusions.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        /* Evento que se ejecutará al hacer click sobre un elemento del adaptador.
        *  En este caso, se abre un nuevo Activity desde el que se podrá editar el elemento y guardarlo en el servidor.
        */
        Infusion infusion = (Infusion) v.getTag();
        EditInfusionActivity.launch((Activity) v.getContext(), new us.idinfor.hemophilia.model.Infusion(infusion));
    }

    @Override
    public boolean onLongClick(final View v) {
        /* Evento que se ejecutará al mantener pulsado un elemento del adaptador.
        *  En este caso, se muestra por pantalla un Dialog que pregunta al usuario si desea eliminar el elemento seleccionado.
        * */
        final AlertDialog.Builder mDialog = new AlertDialog.Builder(v.getContext());
        mDialog.setTitle(v.getContext().getResources().getString(R.string.delete));
        mDialog.setMessage(v.getContext().getResources().getString(R.string.ask_delete_infusion));
        mDialog.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Infusion infusion = (Infusion) v.getTag();
                new DeleteInfusionAsyncTask(infusion.getId()) {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        remove(infusion);
                        Snackbar.make(v, v.getContext().getString(R.string.item_deleted, v.getContext().getString(R.string.infusion)), Snackbar.LENGTH_LONG).show();
                    }
                }.execute();
            }
        });
        mDialog.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.show();
        return false;
    }

    // Clase estática que declara e inicializa las Views que componen un elemento del adaptador.
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMedication;
        TextView mDose;
        TextView mLotNumber;
        TextView mTime;


        public ViewHolder(View itemView) {
            super(itemView);
            mMedication = (TextView)itemView.findViewById(R.id.medication);
            mDose = (TextView)itemView.findViewById(R.id.dose);
            mLotNumber = (TextView)itemView.findViewById(R.id.lotNumber);
            mTime = (TextView)itemView.findViewById(R.id.date);
        }
    }
}
