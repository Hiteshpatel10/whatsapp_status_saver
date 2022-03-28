package com.geekaid.whatsappstatussaver.ui.screens

import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.geekaid.whatsappstatussaver.components.PreviewScreenBottomBar
import com.geekaid.whatsappstatussaver.navigation.Screens
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun ImageStatusPreviewScreen(file: String?) {

    val path = "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/.Statuses/$file"

    Scaffold(bottomBar = {
        PreviewScreenBottomBar(statusPath = path)
    }) {

        AsyncImage(
            model = path,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        )
    }
}

@Composable
fun VideoStatusPreviewScreen(
    file: String?,
    navController: NavHostController
) {

    val context = LocalContext.current
    val path = "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/.Statuses/$file"
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataFactorySourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context, Util.getUserAgent(context, context.packageName)
            )
            val mediaItem = MediaItem.fromUri(path)
            val source = ProgressiveMediaSource.Factory(dataFactorySourceFactory)
                .createMediaSource(mediaItem)
            this.setMediaSource(source)
            this.prepare()
        }
    }

    if (navBackStackEntry?.destination?.route != "${Screens.VideoStatusPreviewScreen.route}/{file}/{navigatedFromSave}")
        exoPlayer.stop()

    Scaffold(
        bottomBar = {
            PreviewScreenBottomBar(statusPath = file.toString())

        }
    ) {
        Column(modifier = Modifier.padding(top = 4.dp, start = 4.dp, bottom = 60.dp, end = 4.dp)) {
            AndroidView(factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    (player as SimpleExoPlayer).playWhenReady = true
                }
            })
        }
    }
}

