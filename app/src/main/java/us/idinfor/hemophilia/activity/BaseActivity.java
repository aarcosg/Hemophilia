package us.idinfor.hemophilia.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import us.idinfor.hemophilia.R;

public class BaseActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;

    // Inicializar el Toolbar con un @title y la posibilidad de navegar hacia atrás desde el Activity @upEnabled
    protected Toolbar buildActionBarToolbar(String title, boolean upEnabled) {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                if(getSupportActionBar()!=null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(upEnabled);
                    if(title != null){
                        getSupportActionBar().setTitle(title);
                    }
                }
            }
        }
        return mActionBarToolbar;
    }

}
