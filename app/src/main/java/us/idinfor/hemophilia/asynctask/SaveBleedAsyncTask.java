package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import us.idinfor.hemophilia.model.Bleed;

public class SaveBleedAsyncTask extends AsyncTask<Void,Void,Bleed>{

    private Bleed bleed;

    public SaveBleedAsyncTask(Bleed bleed){
        this.bleed = bleed;
    }

    @Override
    protected Bleed doInBackground(Void... params) {
        return null;
    }
}
