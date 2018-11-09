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
import us.idinfor.hemophilia.activity.EditBleedActivity;
import us.idinfor.hemophilia.asynctask.DeleteBleedAsyncTask;
import us.idinfor.hemophilia.backend.bleedApi.model.Bleed;


public class BleedAdapter extends RecyclerView.Adapter<BleedAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Bleed> bleeds;


    public BleedAdapter(List<Bleed> bleeds) {
        this.bleeds = bleeds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bleed_item,parent,false);
        ViewHolder holder = new ViewHolder(rootView);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bleed bleed = bleeds.get(position);
        holder.mMedication.setText(bleed.getMedication());
        holder.mDose.setText(String.format("%s",bleed.getDose()));
        holder.mLotNumber.setText(bleed.getLotNumber());
        holder.mTime.setText(DateUtils.getRelativeTimeSpanString(bleed.getTime().getValue(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
        holder.mBodyPart.setText(bleed.getBodyPart());
        holder.itemView.setTag(bleed);
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
        Bleed bleed = (Bleed) v.getTag();
        EditBleedActivity.launch((Activity) v.getContext(), new us.idinfor.hemophilia.model.Bleed(bleed));
    }

    @Override
    public boolean onLongClick(final View v) {
        final AlertDialog.Builder mDialog = new AlertDialog.Builder(v.getContext());
        mDialog.setTitle(v.getContext().getResources().getString(R.string.delete));
        mDialog.setMessage(v.getContext().getResources().getString(R.string.ask_delete_bleed));
        mDialog.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Bleed bleed = (Bleed) v.getTag();
                new DeleteBleedAsyncTask(bleed.getId()) {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        remove(bleed);
                        Snackbar.make(v, v.getContext().getString(R.string.item_deleted, v.getContext().getString(R.string.bleed)), Snackbar.LENGTH_LONG).show();
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

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMedication;
        TextView mDose;
        TextView mLotNumber;
        TextView mTime;
        TextView mBodyPart;

        public ViewHolder(View itemView) {
            super(itemView);
            mMedication = (TextView)itemView.findViewById(R.id.medication);
            mDose = (TextView)itemView.findViewById(R.id.dose);
            mLotNumber = (TextView)itemView.findViewById(R.id.lotNumber);
            mTime = (TextView)itemView.findViewById(R.id.date);
            mBodyPart = (TextView) itemView.findViewById(R.id.bodyPart);
        }
    }
}
