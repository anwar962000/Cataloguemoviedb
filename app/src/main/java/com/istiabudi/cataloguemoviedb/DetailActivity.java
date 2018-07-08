package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_desc ;
    private ImageView imageView;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );


        ButterKnife.bind(this);

        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");
        String desc = getIntent().getStringExtra("desc");
        String image = getIntent().getStringExtra("image");

        tv_name = findViewById(R.id.tv_title_detail);
        tv_date = findViewById(R.id.tv_date_detail);
        tv_desc = findViewById(R.id.tv_desc_detail);
        imageView = findViewById(R.id.img_list_detail);

        tv_name.setText(name);
        tv_date.setText(date);
        tv_desc.setText(desc);

        Glide.with(this)
                .load(image)
                .override(120,120)
                .crossFade()
                .into(imageView);
    }
}
