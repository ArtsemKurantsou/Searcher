package com.kurantsou.searcher.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurantsou.searcher.R;


public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private OnSearchFragmentListner mListener;

    private SearchView searchView;

    private boolean isTextSubmitted;

    private static final String KEY_TEXT = "text";
    private static final String KEY_IS_SUBMITTED = "is_submitted";


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putCharSequence(KEY_TEXT, searchView.getQuery());
        Log.d(TAG, "onSaveInstanceState: " + searchView.getQuery());
        outState.putBoolean(KEY_IS_SUBMITTED, isTextSubmitted);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (SearchView) v.findViewById(R.id.searchView);
        setRetainInstance(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mListener != null) {
                    mListener.onSearchTextSubmitted(query);
                    isTextSubmitted = true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                isTextSubmitted = false;
                return false;
            }
        });

        if (savedInstanceState != null){
            Log.d(TAG, "onCreateView: ");
            boolean isSubmitted = savedInstanceState.getBoolean(KEY_IS_SUBMITTED);
            searchView.setQuery(savedInstanceState.getCharSequence(KEY_TEXT), false);
            Log.d(TAG, "onCreateView: " + searchView.getQuery());
            isTextSubmitted = isSubmitted;
        }


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentListner)
            mListener = (OnSearchFragmentListner) context;
    }

    public void resubmitText(){
        if (isTextSubmitted && !searchView.getQuery().toString().isEmpty() && mListener != null)
            mListener.onSearchTextSubmitted(searchView.getQuery().toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSearchFragmentListner {
        void onSearchTextSubmitted(String serachText);
    }

    private void setSeachText(String text){
        searchView.setQuery(text, false);
        isTextSubmitted = false;
    }

    public static SearchFragment newInstance(String searchText) {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        fragment.setSeachText(searchText);
        return fragment;
    }
}
