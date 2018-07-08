package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.CardViewViewHolder> {

    private Context context;
    private ArrayList<MovieItem> mData;
    public MovieCardAdapter(FragmentActivity context) {
        this.context = context;
    }

    public void addItem(final MovieItem item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<MovieItem> mData){
        this.mData=mData;
    }

    ArrayList<MovieItem> getmData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate( R.layout.movie_card_item, parent, false);
        return new CardViewViewHolder(itemRow);
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        }else{
            return mData.size();
        }
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {
//        Log.d("C", "onBindViewHolder: " + getmData().get(position).getTitle());
        holder.textViewTitle.setText(getmData().get(position).getTitle());
        holder.textViewDesc.setText(getmData().get(position).getDesc());
//        holder.textViewDate.setText(getmData().get(position).getDate());
//
        Glide.with(context)
                .load(getmData().get(position).getImage())
                .override(55, 55)
                .crossFade()
                .into(holder.imageView);
//    }
        holder.baseLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent detail = new Intent( context, DetailActivity.class );
                detail.putExtra( "name", mData.get( position ).getTitle() );
                detail.putExtra( "date", mData.get( position ).getDate() );
                detail.putExtra( "desc", mData.get( position ).getDesc() );
                detail.putExtra( "image", mData.get( position ).getImage() );
                context.startActivity( detail );
//                Toast.makeText( context, mData.get( position ).getTitle(), Toast.LENGTH_LONG ).show();
            }
        });

    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewDate;
        ImageView imageView;
        RelativeLayout baseLayout;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.tv_item_name);
            textViewDate = (TextView)itemView.findViewById(R.id.tv_date_detail);
            textViewDesc = (TextView)itemView.findViewById(R.id.tv_item_remarks);
            imageView = (ImageView)itemView.findViewById(R.id.img_item_photo);
            baseLayout =(RelativeLayout)itemView.findViewById( R.id.base_card_layout);
        }
    }
}
