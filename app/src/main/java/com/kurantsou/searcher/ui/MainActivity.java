package com.kurantsou.searcher.ui;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kurantsou.searcher.R;
import com.kurantsou.searcher.api.GithubSearchApi;
import com.kurantsou.searcher.api.SearchApi;
import com.kurantsou.searcher.api.WikipediaSearchApi;
import com.kurantsou.searcher.models.SearchResult;
import com.kurantsou.searcher.ui.fragments.ListFragment;
import com.kurantsou.searcher.ui.fragments.SearchFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchFragmentListner {

    SearchApi mApi;
    TabLayout tabLayout;
    String searchText;

    ListFragment listFragment;
    SearchFragment searchFragment;

    private static final String KEY_SELECTED_TAB = "selected_tab";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_TAB, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.searchFragment);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.github_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.wkipedia_tab));

        tabLayout.setOnTabSelectedListener(mTabSelectedListner);

        if (savedInstanceState != null) {
            int selectedIndex = savedInstanceState.getInt(KEY_SELECTED_TAB, 0);
            tabLayout.getTabAt(selectedIndex).select();
        }
        else
            mApi = new GithubSearchApi();
        ArrayList<SearchResult> results = (ArrayList<SearchResult>) getLastCustomNonConfigurationInstance();
        listFragment.setResultList(results);


    }

    private TabLayout.OnTabSelectedListener mTabSelectedListner = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    mApi = new GithubSearchApi();
                    break;
                case 1:
                    mApi = new WikipediaSearchApi();
                    break;
            }
            listFragment.clearResults();
            searchFragment.resubmitText();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onSearchTextSubmitted(final String searchText) {
        this.searchText = searchText;
        SearchResultLoader task = new SearchResultLoader();
        task.execute(searchText);
    }

    private class SearchResultLoader extends AsyncTask<String, Void, ArrayList<SearchResult>> {

        private String errorMessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listFragment.setLoading(true);
        }

        @Override
        protected ArrayList<SearchResult> doInBackground(String... strings) {
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

        @Override
        protected void onPostExecute(ArrayList<SearchResult> searchResults) {
            super.onPostExecute(searchResults);
            listFragment.setResultList(searchResults);
            listFragment.setLoading(false);
            if (errorMessage != null) {
                Log.e("Search result loader", "onPostExecute: " + errorMessage);
            }
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (listFragment != null)
            return listFragment.getResults();
        return null;
    }
}
