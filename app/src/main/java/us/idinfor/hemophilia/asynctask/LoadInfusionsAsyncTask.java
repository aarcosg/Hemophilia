package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.infusionApi.InfusionApi;
import us.idinfor.hemophilia.backend.infusionApi.model.Infusion;

public class LoadInfusionsAsyncTask extends AsyncTask<Void,Void,List<Infusion>> {

    private static InfusionApi infusionApi;
    String deviceId;

    public LoadInfusionsAsyncTask(String deviceId){
        this.deviceId = deviceId;
    }

    @Override
    protected List<Infusion> doInBackground(Void... params) {
        List<Infusion> infusions = new ArrayList<>();
        if(infusionApi == null){
            InfusionApi.Builder builder = new InfusionApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            infusionApi = builder.build();
        }
        try {
            infusions = infusionApi.list(deviceId).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infusions;
    }
}
