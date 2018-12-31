package com.bumptech.glide.samples.giphy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

class GifViewHolder extends RecyclerView.ViewHolder {

    final ImageView gifView;
    private final Context context;
    private final RequestBuilder<Drawable> requestBuilder;
    private final ViewPreloadSizeProvider preloadSizeProvider;

    GifViewHolder(View itemView, Context context, RequestBuilder<Drawable> requestBuilder, ViewPreloadSizeProvider preloadSizeProvider) {
        super(itemView);
        gifView = itemView.findViewById(R.id.gif_view);
        this.context = context;
        this.requestBuilder = requestBuilder;
        this.preloadSizeProvider = preloadSizeProvider;
    }

    void bind(Api.GifResult result1) {
        final Api.GifResult result = result1;
        gifView.setOnClickListener(view -> onClick(result));
        // clearOnDetach let's us stop animating GifDrawables that RecyclerView hasn't yet recycled
        // but that are currently off screen.
        this.requestBuilder.load(result).into(this.gifView).clearOnDetach();

        this.preloadSizeProvider.setView(this.gifView);
    }

    private void onClick(Api.GifResult result) {
        ClipboardManager clipboard =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("giphy_url", result.images.fixed_height.url);
        Preconditions.checkNotNull(clipboard).setPrimaryClip(clip);
        Intent fullscreenIntent = FullscreenActivity.getIntent(context, result);
        context.startActivity(fullscreenIntent);
    }
}
