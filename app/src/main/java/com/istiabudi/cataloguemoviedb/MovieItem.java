package com.istiabudi.cataloguemoviedb;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.istiabudi.cataloguemoviedb.db.DatabaseContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;


public class MovieItem implements Parcelable {
    private int _id;
    public String title;
    public int tmdbid;
    public String poster;
    public String releaseDate;
    public String description;
//    public String image;

    public MovieItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String name = object.getString("title");
            String description = object.getString("overview");
            String releaseDate = object.getString("release_date");
            String image = "https://image.tmdb.org/t/p/w500" + object.getString("poster_path");

            this.tmdbid = id;
            this.title = name;
            this.description = description;
            this.releaseDate = releaseDate;
            this.poster = image;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MovieItem(Cursor cursor) {
        this._id = DatabaseContract.getColumnInt(cursor, _ID);
        this.tmdbid = DatabaseContract.getColumnInt(cursor, DatabaseContract.FaveColumns.TMDBID);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.TITLE);
        this.description = DatabaseContract.getColumnString( cursor, DatabaseContract.FaveColumns.DESCRIPTION );
        this.releaseDate = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.RELEASEDATE);
        this.poster = DatabaseContract.getColumnString(cursor, DatabaseContract.FaveColumns.POSTER);
    }

    public MovieItem(int tmdbid, String title, String description,
                     String releaseDate, String poster) {
        this.tmdbid = tmdbid;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }
    public MovieItem(int id, int tmdbid, String title, String description,
                     String releaseDate, String poster) {
        this(tmdbid, title, description, releaseDate, poster);
        this._id = id;

    }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    public int getTmdbid() {
        return tmdbid;
    }
    public void setTmdbid(int tmdbid) {
        this.tmdbid = tmdbid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return releaseDate;
    }
    public void setDate(String dateRelease) {
        this.releaseDate = dateRelease;
    }

    public String getDesc() {
        return description;
    }
    public void setDesc(String description) {
        this.description = description;
    }

    public String getImage() {
        return poster;
    }
    public void setImage(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this._id );
        dest.writeInt( this.tmdbid );
        dest.writeString( this.title );
        dest.writeString( this.description );
        dest.writeString( this.releaseDate );
        dest.writeString( this.poster );
    }

    protected MovieItem(Parcel in) {
        this._id = in.readInt();
        this.tmdbid = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
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