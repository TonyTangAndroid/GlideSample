package com.bumptech.glide.samples.giphy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

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
        adapter = constructAdapter();
        gifList.setAdapter(adapter);
        gifList.addOnScrollListener(constructPreloader());
    }

    @NonNull
    private GifAdapter constructAdapter() {
        ViewPreloadSizeProvider<Api.GifResult> provider = new ViewPreloadSizeProvider<>();
        return new GifAdapter(this, glideRequests, provider);
    }


    @NonNull
    private RecyclerViewPreloader<Api.GifResult> constructPreloader() {
        return new RecyclerViewPreloader<>(glideRequests, adapter, adapter.getPreloadSizeProvider(), 4);
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
