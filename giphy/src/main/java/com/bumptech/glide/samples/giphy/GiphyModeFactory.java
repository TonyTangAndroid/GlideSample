package com.bumptech.glide.samples.giphy;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * The default factory for {@link GiphyModelLoader}s.
 */
public final class GiphyModeFactory implements ModelLoaderFactory<Api.GifResult, InputStream> {

    @NonNull
    @Override
    public ModelLoader<Api.GifResult, InputStream> build(MultiModelLoaderFactory multiFactory) {
        return new GiphyModelLoader(multiFactory.build(GlideUrl.class, InputStream.class));
    }

    @Override
    public void teardown() {
        // Do nothing.
    }
}
