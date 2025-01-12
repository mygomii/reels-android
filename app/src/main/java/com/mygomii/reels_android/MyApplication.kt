package com.mygomii.reels_android

import android.app.Application
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

class MyApplication : Application() {

    lateinit var exoPlayerCache: SimpleCache
        private set

    override fun onCreate() {
        super.onCreate()

        val cacheDirectory = File(cacheDir, "ExoPlayer")
        val cacheEvictor = LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024)
        exoPlayerCache = SimpleCache(cacheDirectory, cacheEvictor)
    }

    override fun onTerminate() {
        super.onTerminate()

        if (this::exoPlayerCache.isInitialized) {
            exoPlayerCache.release()
        }
    }
}