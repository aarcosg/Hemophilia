package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.bleedApi.BleedApi;

public class DeleteBleedAsyncTask extends AsyncTask<Void,Void,Void>{

    private static BleedApi bleedApi;
    Long bleedId;

    public DeleteBleedAsyncTask(Long bleedId) {
        this.bleedId = bleedId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(bleedApi == null){
            BleedApi.Builder builder = new BleedApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            bleedApi = builder.build();
        }
        try {
            bleedApi.remove(bleedId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
