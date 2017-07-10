package com.kurantsou.searcher.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by artem on 09.07.2017.
 */

public class SearchResult implements Parcelable {
    private String header;
    private String text;
    private String imageUrl;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

    public SearchResult() {
    }

    public SearchResult(String header, String text, String imageUrl) {
        this.header = header;
        this.text = text;
        this.imageUrl = imageUrl;
        bitmap = null;
    }

    public SearchResult(Parcel in) {
        header = in.readString();
        text = in.readString();
        imageUrl = in.readString();
        bitmap = null;
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeString(text);
        parcel.writeString(imageUrl);
    }
}
