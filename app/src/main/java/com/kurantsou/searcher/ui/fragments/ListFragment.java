package com.kurantsou.searcher.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kurantsou.searcher.R;
import com.kurantsou.searcher.models.SearchResult;
import com.kurantsou.searcher.ui.adapters.SearchResultListAdapter;

import java.util.List;


public class ListFragment extends Fragment {
    public ListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ProgressBar progressBar;

    SearchResultListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = v.findViewById(R.id.searchResultsList);
        progressBar = v.findViewById(R.id.progressBar);
        adapter = new SearchResultListAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return v;
    }
    public void setLoading(boolean isLoading){
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
    public void setResultList(List<SearchResult> newResults){
        adapter.setResults(newResults);
    }
    public static ListFragment newInstance(List<SearchResult> resultList) {
        
        Bundle args = new Bundle();
        
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
