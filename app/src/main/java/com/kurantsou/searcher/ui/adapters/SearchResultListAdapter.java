package com.kurantsou.searcher.ui.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kurantsou.searcher.R;
import com.kurantsou.searcher.api.BitmapLoader;
import com.kurantsou.searcher.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 09.07.2017.
 */

public class SearchResultListAdapter extends RecyclerView.Adapter<SearchResultListAdapter.SearchResultViewHolder> {

    private ArrayList<SearchResult> results;

    public SearchResultListAdapter() {
    }

    public ArrayList<SearchResult> getResults() {
        return results;
    }

    public SearchResultListAdapter(ArrayList<SearchResult> results) {
        this.results = results;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_item_left_to_right, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_item_right_to_left, parent, false);
                break;
            default:
                break;
        }

        if (v == null)
            return null;

        SearchResultViewHolder vh = new SearchResultViewHolder(v);
        return vh;

    }

    @Override
    public int getItemViewType(int position) {
        return (position + 1) % 2;
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResult result = results.get(position);
        holder.getTvHeader().setText(result.getHeader());
        holder.getTvText().setText(result.getText());
        if (result.getBitmap() != null)
            holder.getIvImage().setImageBitmap(result.getBitmap());
        else {
            holder.getIvImage().setImageResource(R.drawable.ic_broken_image_black_24dp);
            AsyncBitmapLoader task = new AsyncBitmapLoader();
            task.execute(new BitmapLoadParams(result.getImageUrl(), position));
        }
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public void setResults(ArrayList<SearchResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }


    public class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvHeader;
        private TextView tvText;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }

        public ImageView getIvImage() {
            return ivImage;
        }

        public TextView getTvHeader() {
            return tvHeader;
        }

        public TextView getTvText() {
            return tvText;
        }
    }

    private class BitmapLoadParams {
        private String loadUrl;
        private int position;

        public BitmapLoadParams(String loadUrl, int position) {
            this.loadUrl = loadUrl;
            this.position = position;
        }

        public String getLoadUrl() {
            return loadUrl;
        }

        public void setLoadUrl(String loadUrl) {
            this.loadUrl = loadUrl;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private class AsyncBitmapLoader extends AsyncTask<BitmapLoadParams, Void, Bitmap>{
        private int position;

        @Override
        protected Bitmap doInBackground(BitmapLoadParams... bitmapLoadParamses) {
            if (bitmapLoadParamses == null || bitmapLoadParamses.length == 0)
                return null;
            BitmapLoadParams param = bitmapLoadParamses[0];
            position = param.getPosition();
            Bitmap bitmap = BitmapLoader.getBitmapFromURL(param.getLoadUrl());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap == null)
                return;
            if (results != null && results.size() > position) {
                results.get(position).setBitmap(bitmap);
            }
                notifyItemChanged(position);

        }
    }
}
