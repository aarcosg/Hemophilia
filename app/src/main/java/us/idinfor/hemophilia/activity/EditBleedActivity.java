package us.idinfor.hemophilia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import us.idinfor.hemophilia.R;

public class EditBleedActivity extends BaseActivity {

    public static final String EXTRA_BLEED = "extra_bleed";

    Toolbar mToolbar;
    boolean disableSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bleed);
        initUI();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EditBleedActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    private void loadBleed(){
        //TODO Inicializar valores de las vistas con el objeto Bleed recuperado de servidor
    }

    private void initUI(){
        mToolbar = buildActionBarToolbar(getString(R.string.title_activity_add_bleed), true);
        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_bleed, menu);
        menu.findItem(R.id.action_save).setEnabled(!disableSaveBtn);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveBleed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveBleed() {
        //TODO Guardar nuevo objeto Bleed en servidor
    }
}
