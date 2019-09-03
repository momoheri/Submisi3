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
    ImageView poster, imgFavorite, imgRate, imgWatch, imgShare;
    TextView tvTitle, tvYear, tvRate, tvDesc;
    private String image_url;
    private String TEXT_TITLE = "text_title";
    private ProgressBar progressBar;

    @Override
    protected void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putString(STATE_RESULT, tvTitle.getText().toString());
        state.putString(STATE_RESULT, tvYear.getText().toString());
        state.putString(STATE_RESULT, tvRate.getText().toString());
        state.putString(STATE_RESULT, tvDesc.getText().toString());
        state.putString(STATE_RESULT, image_url);
    }

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

        DataAsync dataAsync = new DataAsync(this);
        dataAsync.execute("Background");

        if (savedInstanceState != null){
            String result = savedInstanceState.getString(STATE_RESULT);
            tvTitle.setText(result);
            tvYear.setText(result);
            tvRate.setText(result);
            tvDesc.setText(result);
            image_url = result;
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
        String mode = getIntent().getStringExtra(EXTRA_MODE);
        if (mode.equals("Movie") ){
            DataMovie d = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(d.getTitle());
            tvYear.setText(d.getReleaseDate());
            tvRate.setText(Double.toString(d.getVoteAverage()));
            tvDesc.setText(d.getOverview());
            image_url = Path.IMG_URL_LIST + d.getPosterPath();
            Glide.with(this)
                    .load(image_url)
                    .into(poster);
            TEXT_TITLE = d.getTitle();
            showLoading(false);
        } else if(mode.equals("TvShow")){
            DataTvShow d = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(d.getTitle());
            tvYear.setText(d.getFirst_air_date());
            tvRate.setText(Double.toString(d.getVote_average()));
            tvDesc.setText(d.getOverview());
            image_url = Path.IMG_URL_LIST+d.getPoster_path();
            Glide.with(this)
                    .load(image_url)
                    .into(poster);
            TEXT_TITLE = d.getTitle();
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
                Thread.sleep(3000);

            }catch (Exception ex){
                Log.d(LOG_ASYNC, ex.getMessage());
            }

            return output;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "onPostExecute");
            AsyncCallback mylistener = this.myListener.get();
            if(mylistener != null){
                mylistener.onPostExecute(result);
            }
        }
    }


}
