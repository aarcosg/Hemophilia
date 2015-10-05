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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfusionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_infusion, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfusionActivity.launch(getActivity());
            }
        });

        mProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.infusionRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new InfusionAdapter(new ArrayList<Infusion>());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInfusions();
    }

    private void loadInfusions() {
        new LoadInfusionsAsyncTask(Utils.getUDID(getActivity())) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(List<Infusion> infusions) {
                super.onPostExecute(infusions);
                mProgressBar.setVisibility(View.GONE);
                if (infusions != null && !infusions.isEmpty()) {
                    mAdapter.clear();
                    mAdapter.addAll(infusions);
                }
            }
        }.execute();
    }
}
