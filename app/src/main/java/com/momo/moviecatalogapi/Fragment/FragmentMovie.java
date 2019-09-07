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

import com.momo.moviecatalogapi.Adapter.MovieAdapter;
import com.momo.moviecatalogapi.DetailActivity;
import com.momo.moviecatalogapi.R;
import com.momo.moviecatalogapi.Support.ItemClickSupport;
import com.momo.moviecatalogapi.model.DataMovie;
import com.momo.moviecatalogapi.model.Movie;
import com.momo.moviecatalogapi.service.APIService;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMovie extends Fragment {
    private final static String MOVIE_STATE = "movie_state";
    private MovieAdapter adapter;
    private RecyclerView rvCatalogue;
    private GridLayoutManager grid;
    private ProgressBar progressBar;
    private ArrayList<DataMovie> listData = new ArrayList<>();


    private APIService apiService;

    public FragmentMovie() {
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
        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        rvCatalogue = v.findViewById(R.id.rv_catalogue);
        progressBar = v.findViewById(R.id.progressBar);

        adapter = new MovieAdapter(getContext());

        grid = new GridLayoutManager(getContext(), 2);
        rvCatalogue.setLayoutManager(grid);

        rvCatalogue.setHasFixedSize(true);
        rvCatalogue.setAdapter(adapter);
        if (savedInstanceState == null) {
            showLoading(true);
            loadData();
        } else {
            listData = savedInstanceState.getParcelableArrayList(MOVIE_STATE);
            adapter.addAll(listData);
        }

        itemClick();
        return v;
    }

    private void loadData() {
        apiService = new APIService();
        apiService.getMovie(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Movie movie = (Movie) response.body();

                if (movie != null) {
                    if (adapter != null) {
                        adapter.addAll(movie.getResults());
                        listData.addAll(movie.getResults());
                        showLoading(false);
                    }
                } else {
                    showLoading(false);
                    Toast.makeText(getContext(), "Data Not Found!", Toast.LENGTH_SHORT).show();
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

    private void showSelectedItem(DataMovie dataMovie) {
        DataMovie d = new DataMovie();
        d.setId(dataMovie.getId());
        d.setTitle(dataMovie.getTitle());
        d.setPosterPath(dataMovie.getPosterPath());
        d.setReleaseDate(dataMovie.getReleaseDate());
        d.setVoteAverage(dataMovie.getVoteAverage());
        d.setOverview(dataMovie.getOverview());

        Intent intent = new Intent(this.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_DATA, d);
        intent.putExtra(DetailActivity.EXTRA_MODE, "Movie");
        startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void itemClick() {
        ItemClickSupport.addTo(rvCatalogue).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView rv, int i, View v) {
                showSelectedItem(adapter.getItem(i));
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_STATE, listData);
    }
}
