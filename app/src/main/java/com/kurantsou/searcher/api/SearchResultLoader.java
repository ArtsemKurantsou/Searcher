package com.kurantsou.searcher.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.kurantsou.searcher.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 09.07.2017.
 */

public class SearchResultLoader extends AsyncTaskLoader<List<SearchResult>> {

    public static final String KEY_URL = "KEY_LOAD_URL";

    private String loadUrl;

    public SearchResultLoader(Context context, Bundle args) {
        super(context);
        if (args != null)
            loadUrl = args.getString(KEY_URL, null);
    }

    @Override
    public List<SearchResult> loadInBackground() {
        if (loadUrl == null || loadUrl.isEmpty())
            return null;
        List<SearchResult> searchResult = new ArrayList<>();

        return searchResult;
    }
}
