package com.mygomii.reels_android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OverlayUI(
    onLike: () -> Unit,
    onComment: () -> Unit,
    onShare: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onProfileClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "사용자 이름", color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "@username", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "비디오 설명 텍스트",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onLike) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "1.2K", color = Color.White, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = onComment) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Comment",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "300", color = Color.White, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "50", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}