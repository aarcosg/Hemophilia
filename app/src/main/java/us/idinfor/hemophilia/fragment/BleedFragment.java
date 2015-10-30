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
import us.idinfor.hemophilia.activity.EditBleedActivity;
import us.idinfor.hemophilia.adapter.BleedAdapter;
import us.idinfor.hemophilia.asynctask.LoadBleedsAsyncTask;
import us.idinfor.hemophilia.backend.bleedApi.model.Bleed;
import us.idinfor.hemophilia.decorator.SimpleDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BleedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BleedFragment extends Fragment {

    RecyclerView mRecyclerView;
    BleedAdapter mAdapter;
    ProgressBar mProgressBar;

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

        mProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.bleedRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BleedAdapter(new ArrayList<Bleed>());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBleeds();
    }

    private void loadBleeds(){
        new LoadBleedsAsyncTask(Utils.getUDID(getActivity())) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(List<Bleed> bleeds) {
                super.onPostExecute(bleeds);
                mProgressBar.setVisibility(View.GONE);
                if (bleeds != null && !bleeds.isEmpty()) {
                    mAdapter.clear();
                    mAdapter.addAll(bleeds);
                }
            }
        }.execute();
    }
}
