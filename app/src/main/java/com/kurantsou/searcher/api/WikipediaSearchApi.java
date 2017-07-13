package com.kurantsou.searcher.api;

import android.text.Html;
import android.util.Log;

import com.kurantsou.searcher.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 10.07.2017.
 */

public class WikipediaSearchApi extends SearchApi {

    private static final String URL_STRING = "https://en.wikipedia.org/w/api.php?format=json&action=query&list=search&srsearch=%s";

    private static final int TRIMMED_TEXT_LENTH = 60;

    @Override
    protected ArrayList<SearchResult> getDeserializedAnswer(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;
        Log.d("WikipediaSearchApi", "getDeserializedAnswer: " + jsonObject.toString());
        JSONArray jsonArray = jsonObject.getJSONObject("query").getJSONArray("search");

        if (jsonArray == null)
            return null;


        ArrayList<SearchResult> results = new ArrayList<>();
        for (int i =0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            SearchResult result = new SearchResult();
            result.setHeader(object.getString("title"));
            result.setText(getTrimmedTextFromHtml(object.getString("snippet")));
            results.add(result);
        }

        return results;
    }

    private String getTrimmedTextFromHtml(String text){
        text = Html.fromHtml(text).toString();
        if (text.length() > TRIMMED_TEXT_LENTH)
            text = text.substring(0, TRIMMED_TEXT_LENTH - 3) + "...";
        return text;
    }

    @Override
    public String getRequestUrl(String searchText) {
        try {
            return String.format(URL_STRING, URLEncoder.encode(searchText, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
