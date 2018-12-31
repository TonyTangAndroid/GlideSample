package com.bumptech.glide.samples.giphy;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

/**
 * The primary context in the Giphy sample that allows users to view trending animated GIFs from
 * Giphy's api.
 */
public class MainActivity extends Activity implements Api.Monitor {

    private GifAdapter adapter;
    private GlideRequests glideRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLogo();
        glideRequests = GlideApp.with(this);
        RecyclerView gifList = initRecyclerView();
        addPreloader(gifList);
    }

    private void addPreloader(RecyclerView recyclerView) {
        RecyclerViewPreloader<Api.GifResult> preloader = constructPreloader(recyclerView);
        recyclerView.addOnScrollListener(preloader);
        recyclerView.setRecyclerListener(this::onViewHolderRecycled);
    }

    private void onViewHolderRecycled(RecyclerView.ViewHolder holder) {
        // This is an optimization to reduce the memory usage of RecyclerView's recycled view pool
        // and good practice when using Glide with RecyclerView.
        GifViewHolder gifViewHolder = (GifViewHolder) holder;
        glideRequests.clear(gifViewHolder.gifView);
    }

    @NonNull
    private RecyclerViewPreloader<Api.GifResult> constructPreloader(RecyclerView gifList) {
        RequestBuilder<Drawable> gifItemRequest = glideRequests.asDrawable();
        ViewPreloadSizeProvider<Api.GifResult> provider = new ViewPreloadSizeProvider<>();
        adapter = new GifAdapter(this, gifItemRequest, provider);
        gifList.setAdapter(adapter);
        return new RecyclerViewPreloader<>(glideRequests, adapter, provider, 4);
    }

    @NonNull
    private RecyclerView initRecyclerView() {
        RecyclerView gifList = findViewById(R.id.gif_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gifList.setLayoutManager(layoutManager);
        return gifList;
    }

    private void initLogo() {
        ImageView giphyLogoView = findViewById(R.id.giphy_logo_view);
        GlideApp.with(this).load(R.raw.large_giphy_logo).into(giphyLogoView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api.get().addMonitor(this);
        if (adapter.getItemCount() == 0) {
            Api.get().getTrending();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Api.get().removeMonitor(this);
    }

    @Override
    public void onSearchComplete(Api.SearchResult result) {
        adapter.setResults(result.data);
    }

}
