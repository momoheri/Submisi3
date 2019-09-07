package com.momo.moviecatalogapi.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.momo.moviecatalogapi.Adapter.TvShowAdapter;
import com.momo.moviecatalogapi.DetailActivity;
import com.momo.moviecatalogapi.R;
import com.momo.moviecatalogapi.Support.ItemClickSupport;
import com.momo.moviecatalogapi.model.DataTvShow;
import com.momo.moviecatalogapi.model.TvShow;
import com.momo.moviecatalogapi.service.APIService;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private final static String TV_STATE = "tv_state";
    private TvShowAdapter adapter;
    private RecyclerView rvCatalogue;
    private GridLayoutManager grid;
    private ProgressBar progressBar;
    private ArrayList<DataTvShow> listData = new ArrayList<>();

    private APIService apiService;

    public TvShowFragment() {
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
        View v = inflater.inflate(R.layout.fragment_tv_show, container, false);

        rvCatalogue = v.findViewById(R.id.rv_catalogue);
        progressBar = v.findViewById(R.id.progressBar);

        adapter = new TvShowAdapter(getContext());


        grid = new GridLayoutManager(getContext(), 2);
        rvCatalogue.setLayoutManager(grid);

        rvCatalogue.setHasFixedSize(true);
        rvCatalogue.setAdapter(adapter);
        if (savedInstanceState == null) {
            showLoading(true);
            loadData();
        } else {
            listData = savedInstanceState.getParcelableArrayList(TV_STATE);
            adapter.addAll(listData);
        }

        itemClick();
        return v;
    }


    private void loadData() {
        apiService = new APIService();
        apiService.getTvShow(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                TvShow tvShow = (TvShow) response.body();

                if (tvShow != null) {
                    if (adapter != null) {
                        adapter.addAll(tvShow.getResults());
                        listData.addAll(tvShow.getResults());
                        showLoading(false);
                    }
                } else {
                    showLoading(false);
                    Toast.makeText(getContext(), "Data not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Request time out.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSelectedItem(DataTvShow data) {
        DataTvShow d = new DataTvShow();
        d.setTitle(data.getTitle());
        d.setFirst_air_date(data.getFirst_air_date());
        d.setOverview(data.getOverview());
        d.setPoster_path(data.getPoster_path());
        d.setVote_average(data.getVote_average());

        Intent in = new Intent(this.getContext(), DetailActivity.class);
        in.putExtra(DetailActivity.EXTRA_DATA, d);
        in.putExtra(DetailActivity.EXTRA_MODE, "TvShow");
        startActivity(in);
    }

    private void itemClick() {
        ItemClickSupport.addTo(rvCatalogue).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView rv, int i, View v) {
                showSelectedItem(adapter.getItem(i));
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TV_STATE, listData);
    }
}
