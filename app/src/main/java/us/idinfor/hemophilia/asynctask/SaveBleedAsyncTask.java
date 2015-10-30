package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.bleedApi.BleedApi;
import us.idinfor.hemophilia.backend.bleedApi.model.Bleed;

public class SaveBleedAsyncTask extends AsyncTask<Void,Void,Bleed>{

    private static BleedApi bleedApi;
    Bleed bleed;

    public SaveBleedAsyncTask(Bleed bleed){
        this.bleed = bleed;
    }

    @Override
    protected Bleed doInBackground(Void... params) {
        Bleed newBleed = null;
        if(bleedApi == null){
            BleedApi.Builder builder = new BleedApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            bleedApi = builder.build();
        }
        try {
            if(bleed.getId() == null){
                //Add infusion
                newBleed = bleedApi.insert(bleed).execute();
            }else{
                //Update infusion
                newBleed = bleedApi.update(bleed.getId(),bleed).execute();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newBleed;
    }
}
