package com.istiabudi.favoritemovie;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private FaveAdapter faveAdapter;
    private final int LOAD_MOVIES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ListView listView = findViewById( R.id.list_view );
        faveAdapter = new FaveAdapter( this, null, true );
        listView.setAdapter( faveAdapter );
        getSupportLoaderManager().initLoader( LOAD_MOVIES_ID, null, this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader( LOAD_MOVIES_ID, null, this );
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader( this, DatabaseContract.contentUri(), null,null,null,null );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        faveAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        faveAdapter.swapCursor( null );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader( LOAD_MOVIES_ID );
    }

}
