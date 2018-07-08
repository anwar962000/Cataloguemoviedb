package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    Context context;
    ListView listView;
    MovieAdapter movieAdapter;

    public UpcomingFragment() {

    }

    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        String title = "Iron Man 3";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, null);

        getLoaderManager().initLoader(0, bundle, this);
        listView = (ListView) rootView.findViewById(R.id.listView_upcomming);
        listView.setAdapter(movieAdapter);

        return rootView;
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle bundle) {
        String movieList = "";
        if (bundle != null){
            movieList = bundle.getString(EXTRAS_MOVIE);
        }
//        return null;
        return new MovieAsyncLoader(getActivity(), movieList,"upcoming");

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> movieItems) {
        movieAdapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieAdapter.setData(null);
    }
}

