package com.istiabudi.cataloguemoviedb;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.istiabudi.cataloguemoviedb.db.DatabaseContract.CONTENT_URI;

public class LoaderDatabase extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mData = new ArrayList<>();


    public LoaderDatabase(Context context) {
        super(context);
        onContentChanged();
        Log.d(getClass().getSimpleName(), "LoaderDatabase: "+"LOAD");
    }

    @Override
    protected void onStartLoading() {
        Log.d(getClass().getSimpleName(), "onStartLoading: " +"EXECUTE");
        super.onStartLoading();
        if (takeContentChanged())
            forceLoad();

    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {

        ArrayList<MovieItem> list = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                MovieItem movie = new MovieItem( (JSONObject) cursor );
                list.add(movie);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }

        cursor.close();
        Log.d(getClass().getSimpleName(), "loadInBackground: "+list.size());
        return list;

    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        this.mData = data;
        super.deliverResult(data);

    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

    }

    protected void onReleaseResources(ArrayList<MovieItem> data) {
        //nothing to do.
    }
}

