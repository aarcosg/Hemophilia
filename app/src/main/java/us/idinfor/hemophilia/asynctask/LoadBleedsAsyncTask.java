package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import java.util.List;

import us.idinfor.hemophilia.model.Bleed;

//TODO
public class LoadBleedsAsyncTask extends AsyncTask<Void,Void,List<Bleed>> {

    String deviceId;

    public LoadBleedsAsyncTask(String deviceId){
        this.deviceId = deviceId;
    }

    @Override
    protected List<Bleed> doInBackground(Void... params) {
        return null;
    }
}
