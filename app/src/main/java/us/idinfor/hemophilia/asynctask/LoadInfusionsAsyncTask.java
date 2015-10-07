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

/* AsyncTask cuya funcionalidad es obtener un listado de inyecciones filtradas por el id del dispositivo.
*  Para ello realiza una consulta al Google Cloud Datastore utilizando la API que se ha generado previamente
*  en el módulo "backend" del proyecto.
*/

public class LoadInfusionsAsyncTask extends AsyncTask<Void,Void,List<Infusion>> {

    private static InfusionApi infusionApi;
    String deviceId;

    public LoadInfusionsAsyncTask(String deviceId){
        this.deviceId = deviceId;
    }

    @Override
    protected List<Infusion> doInBackground(Void... params) {
        List<Infusion> infusions = new ArrayList<>();
        // Inicializar la API encargada de manejar las inyecciones almacenadas en el Google Cloud Datastore
        if(infusionApi == null){
            InfusionApi.Builder builder = new InfusionApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(Constants.GOOGLE_APPENGINE_URL)
                    .setApplicationName(Constants.GOOGLE_APPENGINE_APP_NAME);
            infusionApi = builder.build();
        }
        try {
            // Obtener listado de inyecciones filtradas por id de dispositivo
            infusions = infusionApi.list(deviceId).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infusions;
    }
}
