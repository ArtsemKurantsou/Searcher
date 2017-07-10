package com.kurantsou.searcher.api;

import com.kurantsou.searcher.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 09.07.2017.
 */

public class GithubSearchApi extends SearchApi {

    private static final String URL_STRING = "https://api.github.com/search/users?q=%s";

    @Override
    protected ArrayList<SearchResult> getDeserializedAnswer(JSONObject jsonObject) throws JSONException {
        ArrayList<SearchResult> results = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject item = jsonArray.getJSONObject(i);
            SearchResult result = new SearchResult();
            result.setHeader(item.getString("login"));
            result.setText(item.getString("url"));
            result.setImageUrl(item.getString("avatar_url"));
            results.add(result);
        }

        return results;
    }

    @Override
    public String getRequestUrl(String searchText) {
        return String.format(URL_STRING, searchText);
    }
}
