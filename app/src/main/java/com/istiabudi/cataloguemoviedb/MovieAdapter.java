package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter{

    private ArrayList<MovieItem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    public final static String EXTRA_MOVIE = "movie";
    public static String EXTRA_REQUEST_CODE = "request code";

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items){
        mData=items;
        notifyDataSetChanged();
    }
    public void addItem(final MovieItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }
    @Override
    public MovieItem getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_item, null);
            holder.textViewTitle= (TextView)convertView.findViewById(R.id.tv_title);
            holder.textViewDesc = (TextView)convertView.findViewById(R.id.tv_desc);
            holder.textViewDate = (TextView)convertView.findViewById(R.id.tv_date);
            holder.imageView = (ImageView)convertView.findViewById(R.id.img_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewTitle.setText(mData.get(position).getTitle());
        holder.textViewDesc.setText(mData.get(position).getDesc());
        holder.textViewDate.setText(mData.get(position).getDate());

        Glide.with(context)
                .load(mData.get(position).getImage())
                .override(120,120)
                .crossFade()
                .into(holder.imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context , DetailActivity.class);
                detail.putExtra( "tmdbid" ,mData.get(position).getTmdbid());
                detail.putExtra("name" , mData.get(position).getTitle());
                detail.putExtra("date" , mData.get(position).getDate());
                detail.putExtra("desc" , mData.get(position).getDesc());
                detail.putExtra("image" , mData.get(position).getImage());
                context.startActivity(detail);
//                Toast.makeText(context, mData.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewDate;
        ImageView imageView;
    }
}
