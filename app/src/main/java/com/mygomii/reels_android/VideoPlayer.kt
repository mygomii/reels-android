package com.mygomii.reels_android

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

@SuppressLint("OpaqueUnitKey")
@Composable
fun VideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    onDoubleTap: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val application = context.applicationContext as MyApplication
    val exoPlayerCache = application.exoPlayerCache

    val exoPlayer = remember(url, exoPlayerCache) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(CacheDataSource.Factory().apply {
                setCache(exoPlayerCache)
                setUpstreamDataSourceFactory(
                    com.google.android.exoplayer2.upstream.DefaultDataSource.Factory(context)
                )
                setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
            }))
            .build().apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(url))
                setMediaItem(mediaItem)
                addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                        isLoading = !isPlayingNow
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        isLoading = false
                        Toast.makeText(context, "비디오 재생에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
                prepare()
                playWhenReady = isPlaying
            }
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
        if (isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    AnimatedVisibility(
        visible = isPlaying,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        DisposableEffect(
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                modifier = modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                onDoubleTap()
                            }
                        )
                    }
            )
        ) {

            onDispose {
                exoPlayer.release()
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}