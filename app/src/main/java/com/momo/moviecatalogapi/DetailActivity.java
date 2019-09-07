package com.momo.moviecatalogapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.momo.moviecatalogapi.model.DataMovie;
import com.momo.moviecatalogapi.model.DataTvShow;
import com.momo.moviecatalogapi.service.AsyncCallback;
import com.momo.moviecatalogapi.service.Path;

import java.lang.ref.WeakReference;

public class DetailActivity extends AppCompatActivity implements AsyncCallback {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_MODE = "extra_mode";
    private static final String STATE_RESULT = "state_result";
    private static final String STATE_MODE = "state_mode";
    ImageView poster, imgFavorite, imgRate, imgWatch, imgShare;
    TextView tvTitle, tvYear, tvRate, tvDesc;
    private String image_url;
    private ProgressBar progressBar;
    private DataMovie dataMovie;
    private DataTvShow dataTvShow;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);
        showLoading(true);
        poster = findViewById(R.id.kenBurnsView);
        imgFavorite = findViewById(R.id.img_favorite);
        imgRate = findViewById(R.id.img_rate);
        imgWatch = findViewById(R.id.img_watch);
        imgShare = findViewById(R.id.img_share);
        tvTitle = findViewById(R.id.tv_title);
        tvYear = findViewById(R.id.tv_year);
        tvRate = findViewById(R.id.tv_text_rate);
        tvDesc = findViewById(R.id.tv_text_desc);


        mode = getIntent().getStringExtra(EXTRA_MODE);
        if (savedInstanceState != null) {

            mode = savedInstanceState.getString(STATE_MODE);
            if (mode.equals("Movie")) {
                dataMovie = savedInstanceState.getParcelable(STATE_RESULT);
                tvTitle.setText(dataMovie.getTitle());
                tvYear.setText(dataMovie.getReleaseDate());
                tvRate.setText(Double.toString(dataMovie.getVoteAverage()));
                tvDesc.setText(dataMovie.getOverview());
                image_url = Path.IMG_URL_LIST + dataMovie.getPosterPath();
                Glide.with(this)
                        .load(image_url)
                        .into(poster);

                showLoading(false);
            } else {
                dataTvShow = savedInstanceState.getParcelable(STATE_RESULT);
                tvTitle.setText(dataTvShow.getTitle());
                tvYear.setText(dataTvShow.getFirst_air_date());
                tvRate.setText(Double.toString(dataTvShow.getVote_average()));
                tvDesc.setText(dataTvShow.getOverview());
                image_url = Path.IMG_URL_LIST + dataTvShow.getPoster_path();
                Glide.with(this)
                        .load(image_url)
                        .into(poster);

                showLoading(false);
            }

        } else {
            DataAsync dataAsync = new DataAsync(this);
            dataAsync.execute("Background");
        }

    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPreExecute() {
        showLoading(true);
    }

    @Override
    public void onPostExecute(String text) {

        if (mode.equals("Movie")) {
            dataMovie = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(dataMovie.getTitle());
            tvYear.setText(dataMovie.getReleaseDate());
            tvRate.setText(Double.toString(dataMovie.getVoteAverage()));
            tvDesc.setText(dataMovie.getOverview());
            image_url = Path.IMG_URL_LIST + dataMovie.getPosterPath();
            Glide.with(this)
                    .load(image_url)
                    .into(poster);

            showLoading(false);
        } else {
            dataTvShow = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(dataTvShow.getTitle());
            tvYear.setText(dataTvShow.getFirst_air_date());
            tvRate.setText(Double.toString(dataTvShow.getVote_average()));
            tvDesc.setText(dataTvShow.getOverview());
            image_url = Path.IMG_URL_LIST + dataTvShow.getPoster_path();
            Glide.with(this)
                    .load(image_url)
                    .into(poster);

            showLoading(false);
        }

    }

    private static class DataAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "DataAsync";
        WeakReference<AsyncCallback> myListener;

        DataAsync(AsyncCallback myListener) {
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "onPreExecute");
            AsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOG_ASYNC, "doInBackground");
            String output = null;
            try {
                output = strings[0];
                Thread.sleep(2000);

            } catch (Exception ex) {
                Log.d(LOG_ASYNC, ex.getMessage());
            }

            return output;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "onPostExecute");
            AsyncCallback mylistener = this.myListener.get();
            if (mylistener != null) {
                mylistener.onPostExecute(result);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mode.equals("Movie")) {
            outState.putParcelable(STATE_RESULT, dataMovie);
            outState.putString(STATE_MODE, mode);
        } else {
            outState.putParcelable(STATE_RESULT, dataTvShow);
            outState.putString(STATE_MODE, mode);
        }
    }
}
