package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements LoaderCallbacks<ArrayList<MovieItem>> {

    RecyclerView listView;
    MovieCardAdapter movieAdapter;
    EditText editTitle;
    Context context;
    Button buttonSearch;
    private List<MovieItem> movieList;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_search, container, false);
        listView = rootView.findViewById(R.id.listView_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        movieAdapter = new MovieCardAdapter(getActivity());
        listView.setLayoutManager(linearLayoutManager);
        movieAdapter.notifyDataSetChanged();
        listView.setAdapter(movieAdapter);

        editTitle = (EditText) rootView.findViewById( R.id.edt_keyword );
        buttonSearch = (Button) rootView.findViewById( R.id.btn_search );

        String buttonText = String.format( getResources().getString( R.string.search ) );
        String editHint = String.format( getString( R.string.movie_name ) );

        editTitle.setHint( editHint );
        buttonSearch.setText( buttonText );
        buttonSearch.setOnClickListener( myListener );


//        String title = "Iron Man 3";
//        Bundle bundle = new Bundle();
//        bundle.putString( EXTRAS_MOVIE, title );

//        getLoaderManager().initLoader( 0, bundle, this );

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader( 0, bundle, this );

    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle bundle) {
        String movieList = "";
        if (bundle != null) {
            movieList = bundle.getString( EXTRAS_MOVIE );
        }
//        return null;
        return new MovieAsyncLoader( getActivity(), movieList, "SearchMovie" );

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> movieItems) {
        movieAdapter.setData( movieItems );
        movieAdapter.notifyDataSetChanged();
        Log.d(EXTRAS_MOVIE, "onLoadFinished: " + movieItems.size());
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieAdapter.setData( null );
    }


   View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = editTitle.getText().toString();

            if (TextUtils.isEmpty(title)) return ;

            Bundle bundle = new Bundle();
            bundle.putString( EXTRAS_MOVIE, title );
            getLoaderManager().restartLoader( 0, bundle, SearchFragment.this );
        }
    };
}