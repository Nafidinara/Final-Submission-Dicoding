package com.nafidinara.threesubmission.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.nafidinara.threesubmission.db.MovieDbContract;

import static com.nafidinara.threesubmission.db.MovieDbContract.getStringMovie;

public class ModelWidget implements Parcelable {
    private String posterPath;

    public String getPosterPath() {
        return posterPath;
    }

    public ModelWidget(Cursor posterPath) {
        this.posterPath = getStringMovie(posterPath, MovieDbContract.MovieColumns.POSTER_PATH);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
    }

    protected ModelWidget(Parcel in) {
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<ModelWidget> CREATOR = new Parcelable.Creator<ModelWidget>() {
        @Override
        public ModelWidget createFromParcel(Parcel source) {
            return new ModelWidget(source);
        }

        @Override
        public ModelWidget[] newArray(int size) {
            return new ModelWidget[size];
        }
    };
}
