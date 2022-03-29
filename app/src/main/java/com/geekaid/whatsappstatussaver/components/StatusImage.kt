package com.geekaid.whatsappstatussaver.components

import android.app.Activity
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DownloadForOffline
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.geekaid.whatsappstatussaver.MainViewModel
import com.geekaid.whatsappstatussaver.R
import com.geekaid.whatsappstatussaver.model.Status
import com.geekaid.whatsappstatussaver.model.StatusType
import com.geekaid.whatsappstatussaver.navigation.Screens
import com.geekaid.whatsappstatussaver.util.saveStatus
import java.io.File

@Composable
fun StatusImage(status: Status, navController: NavHostController, mainViewModel: MainViewModel) {

    val activity = LocalContext.current as Activity
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var navigatedFromDashboard by remember { mutableStateOf(false) }
    navigatedFromDashboard =
        navBackStackEntry?.destination?.route == Screens.DashboardScreenNav.route
    Log.i("statusImage", navigatedFromDashboard.toString())

    val constraintSet = ConstraintSet {
        val statusImage = createRefFor("statusImage")
        val statusDownloadIcon = createRefFor("statusDownloadIcon")
        val statusPlayIcon = createRefFor("statusPlayIcon")

        constrain(statusImage) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }

        constrain(statusPlayIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }


        constrain(statusDownloadIcon) {
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier
            .padding(4.dp)
            .size(150.dp)
    ) {

        when (status.type) {
            StatusType.IMAGE -> {

                AsyncImage(
                    model = status.path,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .layoutId("statusImage")
                        .clickable(enabled = true) {
                            try {
                                val file = File(status.path)
                                navController
                                    .navigate("${Screens.ImageStatusPreviewScreen.route}/${file.name}/${navigatedFromDashboard}")
                                    .also {
                                        InterstitialAdShow.showInterstitialAd(
                                            activity = activity,
                                            mainViewModel = mainViewModel,
                                            adUnitId = activity.getString(R.string.interstitial_preview)
                                        )
                                    }
                            } catch (e: Exception) {
                                Log.i("StatusImage", e.message.toString())
                            }
                        }
                )

            }

            StatusType.VIDEO -> {

                val bitmap2 = ThumbnailUtils.createVideoThumbnail(
                    status.path,
                    MediaStore.Images.Thumbnails.FULL_SCREEN_KIND
                )

                AsyncImage(
                    model = bitmap2,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .layoutId("statusImage")
                        .clickable(enabled = true) {
                            try {
                                val file = File(status.path)
                                navController
                                    .navigate("${Screens.VideoStatusPreviewScreen.route}/${file.name}/${navigatedFromDashboard}")
                                    .also {
                                        InterstitialAdShow.showInterstitialAd(
                                            activity = activity,
                                            mainViewModel = mainViewModel,
                                            adUnitId = activity.getString(R.string.interstitial_preview)
                                        )
                                    }
                            } catch (e: Exception) {
                                Log.i("StatusImage", e.message.toString())
                            }
                        }
                )

                Icon(
                    Icons.Rounded.PlayCircleFilled,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .size(24.dp)
                        .layoutId("statusPlayIcon")
                )
            }
        }

        IconButton(
            onClick = {
                InterstitialAdShow.showInterstitialAd(
                    activity = activity,
                    adUnitId = "ca-app-pub-3940256099942544/8691691433",
                    mainViewModel = mainViewModel
                )
                saveStatus(status = status)
            }, modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
                .layoutId("statusDownloadIcon")
        ) {
            Icon(
                Icons.Rounded.DownloadForOffline,
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant
            )
        }

    }


}
