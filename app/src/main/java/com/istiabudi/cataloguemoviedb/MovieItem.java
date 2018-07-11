package com.istiabudi.cataloguemoviedb;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.istiabudi.cataloguemoviedb.db.DatabaseContract;

import org.json.JSONObject;


public class MovieItem implements Parcelable {
    private int id;
    public String title;
    public int tmdbid;
    public String originalTitle;
    public String overview;
    public String poster;
    public String releaseDate;
    public String description;
    public String dateRelease;
    public String image;

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

    public MovieItem(Cursor cursor) {
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.FaveColumns.ID);
        this.tmdbid = DatabaseContract.getColumnInt(cursor, DatabaseContract.FaveColumns.TMDBID);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.TITLE);
        this.originalTitle = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.ORIGINALTITLE);
        this.overview = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.OVERVIEW);
        this.releaseDate = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.RELEASEDATE);
        this.poster = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.POSTER);
    }

    public MovieItem(int getId, String name, String date, String desc, String image) {
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