package com.istiabudi.cataloguemoviedb;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> mData;
    private boolean mHasResult = false;

    private String mMovieList;
    String purpose;
    public MovieAsyncTaskLoader(final Context context, String movieList, String _purpose) {
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
        String url = null;
        Log.d(getClass().getSimpleName(), "loadInBackground: " + getId());
        switch (getId()) {
            case 0:
                url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US";
                break;
            case 2:

                if (TextUtils.isEmpty(mMovieList)) {
                    url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en";
                } else {
                    url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + mMovieList + "&include_adult=true";
                }
                break;
        }
        Log.d(getClass().getSimpleName(), "loadInBackground: " + url);
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> listMovie = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {

            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }
//        SyncHttpClient client = new SyncHttpClient();

//        final ArrayList<MovieItem> movieItemses = new ArrayList<>();
//        String url = null;
//        if(this.purpose == "search"){
//            url = "https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY + "&language=en-US&query=" + mMovieList;
//        }else if (this.purpose == "upcoming"){
//            url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ API_KEY + "&language=en-US";
//        }else {
//            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ API_KEY +"&language=en-US";
//        }
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                setUseSynchronousMode(true);
//            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        listMovie.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return listMovie;
    }

    protected void onReleaseResources(ArrayList<MovieItem> data) {

    }

}
