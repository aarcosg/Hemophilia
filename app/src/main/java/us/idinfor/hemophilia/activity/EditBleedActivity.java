package us.idinfor.hemophilia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import us.idinfor.hemophilia.R;

public class EditBleedActivity extends BaseActivity {

    public static final String EXTRA_BLEED = "extra_bleed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bleed);
        //TODO
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EditBleedActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    //TODO Inicializar valores de las vistas con el objeto Bleed recuperado de servidor
    private void loadBleed(){

    }
}
