package com.momo.moviecatalogapi.service;


import com.momo.moviecatalogapi.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    private API api;

    public APIService() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Path.BASE_URL)
                    .client(builder())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(API.class);



    }

    private OkHttpClient builder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        okHttpClient.readTimeout(90, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(interceptor());
        }

        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", Path.API_KEY)
                        .addQueryParameter("language", Path.LANG)
                        .build();

                request = request.newBuilder().url(url).build();
                return chain.proceed(request);

            }
        });
        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    public void getMovie(Callback callback) {
        api.Movies().enqueue(callback);
    }

    public void getTvShow(Callback callback){
        api.TvShows().enqueue(callback);
    }
}
