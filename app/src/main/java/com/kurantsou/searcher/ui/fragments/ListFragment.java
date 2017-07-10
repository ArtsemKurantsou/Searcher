package com.kurantsou.searcher.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kurantsou.searcher.R;
import com.kurantsou.searcher.models.SearchResult;
import com.kurantsou.searcher.ui.adapters.SearchResultListAdapter;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    public ListFragment() {
    }

    RecyclerView recyclerView;
    ProgressBar progressBar;

    SearchResultListAdapter adapter;

    ArrayList<SearchResult> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = v.findViewById(R.id.searchResultsList);
        progressBar = v.findViewById(R.id.progressBar);

        adapter = new SearchResultListAdapter();
        adapter.setResults(results);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        setRetainInstance(true);

        return v;
    }

    public void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    public void clearResults() {
        results = null;
        adapter.setResults(null);
    }

    public void setResultList(ArrayList<SearchResult> newResults) {
        results = newResults;
        if (adapter != null)
            adapter.setResults(newResults);
    }

    public ArrayList<SearchResult> getResultList() {
        return results;
    }

    public ArrayList<SearchResult> getResults() {
        return results;
    }
}
