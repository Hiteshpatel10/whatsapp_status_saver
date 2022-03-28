package com.geekaid.whatsappstatussaver.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String, val icon: ImageVector) {

    object DashboardScreenNav : Screens(
        route = "DashboardScreenNav",
        title = "Status",
        icon = Icons.Filled.Dashboard
    )


    object ImageStatusScreenNav : Screens(
        route = "ImageStatusScreenNav",
        title = "Image Status",
        icon = Icons.Filled.Image
    )

    object VideoStatusScreenNav : Screens(
        route = "VideoStatusScreenNav",
        title = "Video Status",
        icon = Icons.Filled.VideoLibrary
    )

    object SavedStatusScreenNav : Screens(
        route = "SavedStatusScreenNav",
        title = "Saved",
        icon = Icons.Rounded.Download
    )

    object ImageStatusPreviewScreen : Screens(
        route = "ImageStatusPreviewScreenNav",
        title = "Image Status Preview",
        icon = Icons.Rounded.Image
    )

    object VideoStatusPreviewScreen : Screens(
        route = "VideoStatusPreviewScreenNav",
        title = "Video Status Preview",
        icon = Icons.Rounded.PlayArrow
    )


}