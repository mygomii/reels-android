package com.mygomii.reels_android

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ReelsScreen(reelsUrlArray: List<String>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(
            count = reelsUrlArray.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(9f / 16f)
                    .clickable {
                        val intent = Intent(context, FullscreenVideoActivity::class.java).apply {
                            putExtra("VIDEO_URL", reelsUrlArray[page])
                        }
                        context.startActivity(intent)
                    }
            ) {
                VideoPlayer(
                    url = reelsUrlArray[page],
                    modifier = Modifier.fillMaxSize(),
                    isPlaying = pagerState.currentPage == page,
                    onDoubleTap = {
                        // TODO;
                    }
                )
                OverlayUI(
                    onLike = {
                        // TODO;
                    },
                    onComment = {
                        // TODO;
                    },
                    onShare = {
                        // TODO;
                    },
                    onProfileClick = {
                        // TODO;
                    }
                )
            }
        }
    }
}