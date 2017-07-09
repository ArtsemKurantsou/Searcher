package com.kurantsou.searcher.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kurantsou.searcher.R;
import com.kurantsou.searcher.api.GithubSearchApi;
import com.kurantsou.searcher.api.SearchApi;
import com.kurantsou.searcher.models.SearchResult;
import com.kurantsou.searcher.ui.fragments.ListFragment;
import com.kurantsou.searcher.ui.fragments.SearchFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchFragmentListner {

    SearchApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.searchFragmentContainer, SearchFragment.newInstance())
                .replace(R.id.listFragmentContainer, ListFragment.newInstance(null))
                .commit();
        mApi = new GithubSearchApi();
    }

    @Override
    public void onSearchTextSubmitted(final String searchText) {
        AsyncTask<String, Void, List<SearchResult>> task = new AsyncTask<String, Void, List<SearchResult>>() {

            private String errorMessage;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ListFragment listFragment = (ListFragment) (getSupportFragmentManager().findFragmentById(R.id.listFragmentContainer));
                listFragment.setLoading(true);
            }

            @Override
            protected void onPostExecute(List<SearchResult> searchResults) {
                super.onPostExecute(searchResults);
                ListFragment listFragment = (ListFragment) (getSupportFragmentManager().findFragmentById(R.id.listFragmentContainer));
                listFragment.setResultList(searchResults);
                listFragment.setLoading(false);
                if (errorMessage != null)
                    Log.e("Search result loader", "onPostExecute: " + errorMessage);
            }

            @Override
            protected List<SearchResult> doInBackground(String... strings) {
                if (strings == null || strings.length == 0 || mApi == null) return null;
                try {
                    return mApi.getSearchResult(strings[0]);
                } catch (IOException e) {
                    errorMessage = "problems with connection";
                    e.printStackTrace();
                } catch (JSONException e) {
                    errorMessage = "problems with deserialization";
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute(searchText);
    }
}
