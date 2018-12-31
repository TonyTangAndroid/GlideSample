package com.bumptech.glide.samples.giphy;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;

class GifAdapter extends RecyclerView.Adapter<GifViewHolder>
        implements ListPreloader.PreloadModelProvider<Api.GifResult> {

    private static final Api.GifResult[] EMPTY_RESULTS = new Api.GifResult[0];

    private final Activity activity;
    private final RequestBuilder<Drawable> requestBuilder;
    private final ViewPreloadSizeProvider<Api.GifResult> preloadSizeProvider;

    private Api.GifResult[] results = EMPTY_RESULTS;
    private final GlideRequests glideRequests;

    public ListPreloader.PreloadSizeProvider<Api.GifResult> getPreloadSizeProvider() {
        return preloadSizeProvider;
    }

    GifAdapter(Activity activity,
               GlideRequests glideRequests,
               ViewPreloadSizeProvider<Api.GifResult> preloadSizeProvider) {
        this.activity = activity;
        this.glideRequests = glideRequests;
        this.requestBuilder = glideRequests.asDrawable();
        this.preloadSizeProvider = preloadSizeProvider;
    }

    void setResults(Api.GifResult[] results) {
        if (results != null) {
            this.results = results;
        } else {
            this.results = EMPTY_RESULTS;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.gif_list_item, parent, false);
        return new GifViewHolder(view, activity, requestBuilder, preloadSizeProvider);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        holder.bind(results[position]);
    }

    @DebugLog
    @Override
    public void onViewRecycled(@NonNull GifViewHolder holder) {
        super.onViewRecycled(holder);
        glideRequests.clear(holder.gifView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull GifViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull GifViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    @NonNull
    @Override
    public List<Api.GifResult> getPreloadItems(int position) {
        return Collections.singletonList(results[position]);
    }

    @Nullable
    @Override
    public RequestBuilder<Drawable> getPreloadRequestBuilder(@NonNull Api.GifResult item) {
        return requestBuilder.load(item);
    }
}
