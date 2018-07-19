package com.istiabudi.favoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.istiabudi.favoritemovie.DatabaseContract.FaveColumns.DESCRIPTION;
import static com.istiabudi.favoritemovie.DatabaseContract.FaveColumns.POSTER;
import static com.istiabudi.favoritemovie.DatabaseContract.FaveColumns.RELEASEDATE;
import static com.istiabudi.favoritemovie.DatabaseContract.FaveColumns.TITLE;
import static com.istiabudi.favoritemovie.DatabaseContract.getColumnString;

public class FaveAdapter extends CursorAdapter {
    public FaveAdapter(Context context, Cursor c, boolean autoRequery) {
        super( context, c, autoRequery );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from( context ).inflate( R.layout.movie_card_item, viewGroup, false );
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView title, releaseDate, remarks;
            ImageView poster;
            Button btnDetail;

            title = view.findViewById( R.id.tv_item_name );
            releaseDate = view.findViewById( R.id.tv_date );
            remarks = view.findViewById( R.id.tv_item_remarks );
            poster = view.findViewById( R.id.img_item_photo );
            btnDetail = view.findViewById( R.id.btn_detail );

            Glide.with( context ).load( getColumnString( cursor, POSTER ) ).override( 640, 480 ).into( poster );

            title.setText( getColumnString(cursor, TITLE) );
            releaseDate.setText( getColumnString( cursor,RELEASEDATE ) );
            remarks.setText( getColumnString( cursor, DESCRIPTION ) );
        }

    }
}
