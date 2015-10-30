package us.idinfor.hemophilia.asynctask;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import us.idinfor.hemophilia.Constants;
import us.idinfor.hemophilia.backend.infusionApi.InfusionApi;
import us.idinfor.hemophilia.backend.infusionApi.model.Infusion;

public class SaveInfusionAsyncTask extends AsyncTask<Void,Void,Infusion>{

    private Infusion infusion;
    private static InfusionApi infusionApi;

    public SaveInfusionAsyncTask(Infusion infusion){
        this.infusion = infusion;
    }

    @Override
    protected Infusion doInBackground(Void... params) {
        Infusion newInfusion = null;
        if(infusionApi == null){
            InfusionApi.Builder builder = new InfusionApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            infusionApi = builder.build();
        }
        try {
            if(infusion.getId() == null){
                //Add infusion
                newInfusion = infusionApi.insert(infusion).execute();
            }else{
                //Update infusion
                newInfusion = infusionApi.update(infusion.getId(),infusion).execute();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newInfusion;
    }
}
