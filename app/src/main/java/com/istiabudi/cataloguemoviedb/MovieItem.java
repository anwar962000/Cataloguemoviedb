package com.istiabudi.cataloguemoviedb;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;


public class MovieItem implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String dateRelease;
    private String image;

    public MovieItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String name = object.getString("title");
            String description = object.getString("overview");
            String dateRelease = object.getString("release_date");
            String image = "https://image.tmdb.org/t/p/w500" + object.getString("poster_path");

            this.id = id;
            this.title = name;
            this.description = description;
            this.dateRelease = dateRelease;
            this.image = image;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return dateRelease;
    }
    public void setDate(String dateRelease) {
        this.dateRelease = dateRelease;
    }
    public String getDesc() {
        return description;
    }
    public void setDesc(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.title );
        dest.writeString( this.description );
        dest.writeString( this.dateRelease );
        dest.writeString( this.image );
    }

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.dateRelease = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem( source );
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}