package com.geekaid.whatsappstatussaver.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.geekaid.whatsappstatussaver.MainViewModel
import com.geekaid.whatsappstatussaver.R
import com.geekaid.whatsappstatussaver.model.Status
import com.geekaid.whatsappstatussaver.model.StatusType
import com.geekaid.whatsappstatussaver.navigation.Screens
import com.geekaid.whatsappstatussaver.util.saveStatus
import java.io.File

@Composable
fun PreviewScreenBottomBar(
    filePath: String,
    navigatedFromDashboard: Boolean,
    context: Context,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val file = File(filePath)
    val statusType = when (file.extension) {
        "jpg" -> StatusType.IMAGE
        "mp4" -> StatusType.VIDEO
        else -> StatusType.IMAGE
    }

    val activity = LocalContext.current as Activity

    BottomNavigation {

        BottomNavigationItem(

            label = {
                Text(text = "Repost")
            },

            icon = {
                Icon(imageVector = Icons.Filled.Repeat, contentDescription = "Repost")
            },

            selected = true,

            onClick = {

                try {
                    val contentUri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName, file
                    )
                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        setPackage("com.whatsapp")
                        putExtra(Intent.EXTRA_STREAM, contentUri)
                        type = if (statusType == StatusType.IMAGE) "image/jpg" else "video/mp4"
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(shareIntent)
                } catch (e: Exception) {
                    Log.i("PreviewStatus", e.message.toString())

                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = "Share")
            },

            icon = {
                Icon(imageVector = Icons.Filled.Share, contentDescription = "Share")
            },

            selected = true,

            onClick = {
                try {
                    val contentUri: Uri = FileProvider.getUriForFile(
                        context,
                        context.packageName, file
                    )
                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, contentUri)
                        type = if (statusType == StatusType.IMAGE) "image/jpg" else "video/mp4"
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Share Image To"
                        )
                    )
                } catch (e: Exception) {
                    Log.i("PreviewStatus", e.message.toString())

                }
            }
        )


        if (navigatedFromDashboard)
            BottomNavigationItem(

                label = {
                    Text(text = "Save")
                },

                icon = {
                    Icon(imageVector = Icons.Filled.Download, contentDescription = "Save")
                },

                selected = true,

                onClick = {

                    InterstitialAdShow.showInterstitialAd(
                        activity = activity,
                        mainViewModel = mainViewModel,
                        adUnitId = activity.getString(
                            R.string.interstitial_save
                        )
                    )

                    saveStatus(Status(path = filePath, type = statusType)).also {
                        when (navigatedFromDashboard) {
                            true -> navController.navigate(Screens.DashboardScreenNav.route)
                            false -> navController.navigate(Screens.SavedStatusScreenNav.route)
                        }
                    }

                }
            )


        if (!navigatedFromDashboard)
            BottomNavigationItem(

                label = {
                    Text(text = "Delete")
                },

                icon = {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                },

                selected = true,

                onClick = {

                    InterstitialAdShow.showInterstitialAd(
                        activity = activity,
                        mainViewModel = mainViewModel,
                        adUnitId = activity.getString(
                            R.string.interstitial_delete
                        )
                    )
                    if (file.exists()) {
                        file.delete().also {
                            when (navigatedFromDashboard) {
                                true -> navController.navigate(Screens.DashboardScreenNav.route)
                                false -> navController.navigate(Screens.SavedStatusScreenNav.route)
                            }
                        }
                    }
                }
            )
    }

}
