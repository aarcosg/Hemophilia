package us.idinfor.hemophilia.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import us.idinfor.hemophilia.R;
import us.idinfor.hemophilia.fragment.BleedFragment;
import us.idinfor.hemophilia.fragment.InfusionFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String NAV_ITEM_ID = "nav_item_id";

    private Toolbar mToolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private int mNavItemId;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el layout del Activity
        setContentView(R.layout.activity_main);
        // Inicializar el Toolbar
        mToolbar = buildActionBarToolbar(getString(R.string.app_name),true);
        /* Guardar dos referencias de títulos.
         * @mTitle Título de los fragments. Se muestra cuando el drawer está cerrado.
         * @mDrawerTitle Nombre de la aplicación. Se muestra cuando el drawer está abierto.
         * */
        mTitle = mDrawerTitle = getTitle();

        // Cargar el estado de navegación si está presente
        if (null == savedInstanceState) {
            mNavItemId = R.id.nav_infusion;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // Inicializar el NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Seleccionar el item del menú correspondiente
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        //Inicializar el DrawerLayout y el ActionBarDrawerToggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /**
             * Se llama cuando el drawer se ha cerrado por completo.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Se pone en el Toolbar el título del Fragment actual.
                mToolbar.setTitle(mTitle);
            }

            /**
             * Se llama cuando el drawer se ha abierto por completo.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Se pone en el Toolbar el título de la aplicación
                mToolbar.setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /*
         * Llamar a función privada encargada de gestionar los fragments dependiendo
         * del elemento del menú de navegación que haya pulsado el usuario.
         * */
        selectDrawerItem(mNavItemId);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del toggle cuando el Activity haga una llamada a onRestoreInstanceState
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Notificar al toggle de una nueva configuración, por ejemplo al cambiar la orientación del dispositivo
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        /* Guardar el identificador del elemento que está seleccionado en el menú de navegación
        *  para poder cargar el Fragment correspondiente cuando se vuelva a este Activity
        **/
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    public void onBackPressed() {
        // Si el DrawerLayout está abierto y se pulsa el botón "Atrás", cerrarlo, sino, volver atrás.
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú para añadir los elementos de éste al Toolbar.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Manejar los clicks sobre los elementos del menú de navegación.
        item.setChecked(true);
        mNavItemId = item.getItemId();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        selectDrawerItem(mNavItemId);
        return true;
    }

    private void selectDrawerItem(int itemId) {
        Fragment fragment = null;
        // Crear el Fragment correspondiente al elemento del menú de navegación seleccionado
        switch (itemId) {
            case R.id.nav_infusion:
                fragment = InfusionFragment.newInstance();
                mTitle = getString(R.string.title_fragment_infusion);
                break;
            case R.id.nav_bleed:
                fragment = BleedFragment.newInstance();
                mTitle = getString(R.string.title_fragment_bleed);
                break;
        }

        if(fragment != null){
            // Poner el título del Fragment seleccionado en el Toolbar
            if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(mTitle);
            // Mostrar el Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
        }
    }
}
