package us.idinfor.hemophilia.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import us.idinfor.hemophilia.R;
import us.idinfor.hemophilia.Utils;
import us.idinfor.hemophilia.activity.EditInfusionActivity;
import us.idinfor.hemophilia.adapter.InfusionAdapter;
import us.idinfor.hemophilia.asynctask.LoadInfusionsAsyncTask;
import us.idinfor.hemophilia.backend.infusionApi.model.Infusion;
import us.idinfor.hemophilia.decorator.SimpleDividerItemDecoration;

public class InfusionFragment extends Fragment {

    RecyclerView mRecyclerView;
    InfusionAdapter mAdapter;
    ProgressBar mProgressBar;

    public static InfusionFragment newInstance() {
        InfusionFragment fragment = new InfusionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InfusionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout de este fragment
        View rootView = inflater.inflate(R.layout.fragment_infusion, container, false);

        // Inicializar el Floating Action Button y añadirle un evento de click
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfusionActivity.launch(getActivity());
            }
        });

        // Inicializar la barra de progreso de carga
        mProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        // Inicializar el RecyclerView que mostrará el listado
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.infusionRV);
        /* Seleccionar el LayoutManager que queremos usar en el listado. Por defecto vienen 3 opciones ya implementadas:
        * 1. LinearLayoutManager. Similar a ListView.
        * https://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager.html
        * 2. GridLayoutManager. Muestra los elementos en formato grid.
        * https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html
        * 3. StaggeredGridLayoutManager. Muestra elementos con distintos tamaños de ancho y alto.
        * https://developer.android.com/reference/android/support/v7/widget/StaggeredGridLayoutManager.html
        * Ejemplo: http://3.bp.blogspot.com/-ol5caJCRMlI/UDQheKR_jzI/AAAAAAAAAXQ/Zy1tpDR6DcM/s320/Screenshot_2012-08-21-18-01-19.png
        * */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /* Seleccionar la animación que se ejecutará cuando se realizan modificaciones sobre los elementos del RecyclerView,
        * por ejemplo al añadir o eliminar. Por defecto, RecyclerView usa DefaultItemAnimator.
        * Librerías de animaciones:
        *   - https://github.com/wasabeef/recyclerview-animators
        *   - https://github.com/gabrielemariotti/RecyclerViewItemAnimators
        */
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /* Seleccionar la decoración que tendrán los elementos del RecyclerView. En este ejemplo se dibujará una línea
        *  horizontal al final del elemento que servirá como divisor.
        * */
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        // Poner a true si el tamaño del RecyclerView no va a cambiar para que se optimice mejor.
        mRecyclerView.setHasFixedSize(true);
        // Seleccionar el adaptador que se encargará de cargar los datos del modelo en el layout de los elementos del RecyclerView
        mAdapter = new InfusionAdapter(new ArrayList<Infusion>());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cargar el listado de inyecciones
        loadInfusions();
    }

    private void loadInfusions() {
        new LoadInfusionsAsyncTask(Utils.getUDID(getActivity())) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Mostrar la barra de progreso
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(List<Infusion> infusions) {
                super.onPostExecute(infusions);
                // Ocultar la barra de progreso
                mProgressBar.setVisibility(View.GONE);
                if (infusions != null && !infusions.isEmpty()) {
                    // Cargar todas las inyecciones obtenidas de la base de datos en el RecyclerView usando el adaptador
                    mAdapter.clear();
                    mAdapter.addAll(infusions);
                }
            }
        }.execute();
    }
}
