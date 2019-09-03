package com.momo.moviecatalogapi.service;

public interface AsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}
