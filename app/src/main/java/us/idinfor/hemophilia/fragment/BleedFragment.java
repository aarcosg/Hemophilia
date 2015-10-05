package us.idinfor.hemophilia.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.idinfor.hemophilia.R;
import us.idinfor.hemophilia.activity.EditBleedActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BleedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BleedFragment extends Fragment {

    public static BleedFragment newInstance() {
        BleedFragment fragment = new BleedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public BleedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bleed, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBleedActivity.launch(getActivity());
            }
        });

        //TODO Inicializar RecyclerView para mostrar el listado de hemorragias. Hay que implementar el Adapter BleedAdapter.

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBleeds();
    }

    //TODO Función que recupera del servidor las hemorragias y las carga en el RecyclerView
    private void loadBleeds(){
        //TODO Implementar LoadBleedsAsyncTask para conectar con el servidor
    }
}
