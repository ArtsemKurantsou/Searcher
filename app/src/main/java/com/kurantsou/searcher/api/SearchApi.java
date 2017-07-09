package com.kurantsou.searcher.api;

import com.kurantsou.searcher.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 09.07.2017.
 */

public abstract class SearchApi {
    public List<SearchResult> getSearchResult(String searchText) throws MalformedURLException, IOException, JSONException {
        URL url = new URL(getRequestUrl(searchText));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        JSONObject jsonObject = new JSONObject(response.toString());

        return getDeserializedAnswer(jsonObject);
    }

    protected abstract List<SearchResult> getDeserializedAnswer(JSONObject jsonObject) throws JSONException;

    public abstract String getRequestUrl(String searchText);
}
