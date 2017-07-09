package com.kurantsou.searcher.models;

/**
 * Created by artem on 09.07.2017.
 */

public class SearchResult {
    private String header;
    private String text;
    private String imageUrl;

    public SearchResult() {
    }

    public SearchResult(String header, String text, String imageUrl) {
        this.header = header;
        this.text = text;
        this.imageUrl = imageUrl;
    }

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
}
