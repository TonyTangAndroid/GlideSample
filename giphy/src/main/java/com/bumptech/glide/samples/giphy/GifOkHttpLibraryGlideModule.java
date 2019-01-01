package com.bumptech.glide.samples.giphy;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ashokvarma.gander.GanderInterceptor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@DebugLog
@GlideModule
public final class GifOkHttpLibraryGlideModule extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client(context)));
    }

    private OkHttpClient client(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new GanderInterceptor(context).showNotification(true))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }


}