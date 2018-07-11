package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.istiabudi.cataloguemoviedb.db.DatabaseContract;
import com.istiabudi.cataloguemoviedb.db.FaveHelper;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_desc;
    private ImageView imageView, btnFav;
    private LayoutInflater mInflater;
    public boolean faved = false;
    private FaveHelper faveHelper;
    private MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        Intent intent = this.getIntent();

        int id = intent.getIntExtra( "id", 0 );

        ButterKnife.bind( this );

        String name = getIntent().getStringExtra( "name" );
        String date = getIntent().getStringExtra( "date" );
        String desc = getIntent().getStringExtra( "desc" );
        String image = getIntent().getStringExtra( "image" );

        tv_name = findViewById( R.id.tv_title_detail );
        tv_date = findViewById( R.id.tv_date_detail );
        tv_desc = findViewById( R.id.tv_desc_detail );
        imageView = findViewById( R.id.img_list_detail );
        btnFav = findViewById( R.id.bt_fav );

        tv_name.setText( name );
        tv_date.setText( date );
        tv_desc.setText( desc );

        this.movie = new MovieItem( id, name, date, desc, image );


        Cursor cursor = this.getContentResolver().query( DatabaseContract.contentUriWithId( this.movie.tmdbid ), null, null, null, null );

        if (cursor != null && cursor.getCount() > 0) {
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star );
            this.faved = true;
        } else
            ((ImageView) this.findViewById( R.id.bt_fav )).setImageResource( R.drawable.ic_baseline_star_border );
        if (cursor != null) cursor.close();

        Glide.with( this ).load( image ).override( 120, 120 ).crossFade().into( imageView );

        btnFav.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "tag", "onClick: log" );
//                this.getContentResolver().insert( DatabaseContract.contentUri(), values );
                Toast.makeText( DetailActivity.this, "Added To Favorite", Toast.LENGTH_LONG ).show();
            }

 //           private StringBuffer getContentResolver() {
//                return null;
 //           }
        } );
    }
}

