package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class MovieAsyncLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> mData;
    private boolean mHasResult = false;

    private String mMovieList;
    String purpose;
    public MovieAsyncLoader(final Context context, String movieList, String _purpose) {
        super(context);

        onContentChanged();
        this.mMovieList = movieList;

        this.purpose = _purpose;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "395f772f2d323e2f968dffc249550219";

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movieItemses = new ArrayList<>();
        String url = null;
        if(this.purpose == "search"){
            url = "https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY + "&language=en-US&query=" + mMovieList;
        }else if (this.purpose == "upcoming"){
            url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ API_KEY + "&language=en-US";
        }else {
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ API_KEY +"&language=en-US";
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        movieItemses.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieItemses;
    }

    protected void onReleaseResources(ArrayList<MovieItem> data) {

    }
}
