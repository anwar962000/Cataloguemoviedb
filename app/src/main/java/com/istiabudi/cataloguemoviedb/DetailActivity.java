package com.istiabudi.cataloguemoviedb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.istiabudi.cataloguemoviedb.db.DatabaseContract;
import com.istiabudi.cataloguemoviedb.db.FaveHelper;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_desc;
    private ImageView imageView, btnFav;
    public boolean faved = false;
    private FaveHelper faveHelper;
    private MovieItem movie;
    public static String MOVIE_ITEM = "movie_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();

        final int tmdbid = intent.getIntExtra("tmdbid", 0);

        ButterKnife.bind( this );

        final String title = getIntent().getStringExtra( "name" );
        final String date = getIntent().getStringExtra( "date" );
        final String desc = getIntent().getStringExtra( "desc" );
        final String image = getIntent().getStringExtra( "image" );
        Log.d( "Isi Intent", "onCreate: " + String.valueOf( tmdbid ) + " " + title + " " + date + " " + desc + " " + image);
        tv_name = findViewById(R.id.tv_title_detail);
        tv_date = findViewById(R.id.tv_date_detail);
        tv_desc = findViewById(R.id.tv_desc_detail );
        imageView = findViewById( R.id.img_list_detail );
        btnFav = findViewById( R.id.bt_fav );

        tv_name.setText( title );
        tv_date.setText( date );
        tv_desc.setText( desc );

        this.movie = new MovieItem(tmdbid, title, desc, date, image);
        this.faveHelper = new FaveHelper( this );

        Cursor cursor = this.getContentResolver().query( DatabaseContract.contentUriWithId( this.movie.tmdbid ), null, null, null, null );

        if (cursor != null && cursor.getCount() > 0) {
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star );
            this.faved = true;
        } else
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star_border );
        if (cursor != null) cursor.close();

        Glide.with(this).load(image).override(120, 120).crossFade().into(imageView);

        btnFav.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        this.faveHelper.open();
        if (this.faved) {
            getContentResolver().delete( DatabaseContract.contentUriWithId(movie.tmdbid), null, null );
            Toast.makeText( DetailActivity.this, "Removed To Favorite", Toast.LENGTH_LONG ).show();

            this.faved = false;
        } else {
            Log.d( "tag", "onClick: log" );
            ContentValues contentValues = new ContentValues();

            contentValues.put( DatabaseContract.FaveColumns.TMDBID, movie.tmdbid);
            contentValues.put( DatabaseContract.FaveColumns.TITLE, movie.title );
            contentValues.put( DatabaseContract.FaveColumns.RELEASEDATE, movie.release_date );
            contentValues.put( DatabaseContract.FaveColumns.DESCRIPTION, movie.description );
            contentValues.put( DatabaseContract.FaveColumns.POSTER, movie.poster);
            Log.d( "contentValues", "onClick: " + contentValues );
            getContentResolver().insert( DatabaseContract.contentUri(),contentValues );
            Toast.makeText( DetailActivity.this, "Added To Favorite", Toast.LENGTH_LONG ).show();

            this.faved = true;
        }
        this.faveHelper.close();
        if (this.faved)
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star );
        else
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star_border );

    }
}

