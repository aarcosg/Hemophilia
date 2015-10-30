package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.bleedApi.BleedApi;
import us.idinfor.hemophilia.backend.bleedApi.model.Bleed;


public class LoadBleedsAsyncTask extends AsyncTask<Void,Void,List<Bleed>> {

    private static BleedApi bleedApi;
    String deviceId;

    public LoadBleedsAsyncTask(String deviceId){
        this.deviceId = deviceId;
    }

    @Override
    protected List<Bleed> doInBackground(Void... params) {
        List<Bleed> bleeds = new ArrayList<>();
        if(bleedApi == null){
            BleedApi.Builder builder = new BleedApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            bleedApi = builder.build();
        }
        try {
            bleeds = bleedApi.list(deviceId).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bleeds;
    }
}
