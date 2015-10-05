package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.infusionApi.InfusionApi;

public class DeleteInfusionAsyncTask extends AsyncTask<Void,Void,Void>{

    private Long infusionId;
    private static InfusionApi infusionApi;

    public DeleteInfusionAsyncTask(Long infusionId) {
        this.infusionId = infusionId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(infusionApi == null){
            InfusionApi.Builder builder = new InfusionApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            infusionApi = builder.build();
        }
        try {
            infusionApi.remove(infusionId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
