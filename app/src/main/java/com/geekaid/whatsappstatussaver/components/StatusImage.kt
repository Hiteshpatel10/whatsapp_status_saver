package com.geekaid.whatsappstatussaver.components

import android.media.ThumbnailUtils
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DownloadForOffline
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.geekaid.whatsappstatussaver.model.Status
import com.geekaid.whatsappstatussaver.model.StatusType
import com.geekaid.whatsappstatussaver.navigation.Screens
import java.io.*

@Composable
fun StatusImage(status: Status, navController: NavHostController) {

    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
                                navController.navigate("${Screens.ImageStatusPreviewScreen.route}/${file.name}")
                            } catch (e: Exception) {
                                Log.i("StatusImage", e.message.toString())
                            }
                        }
                )

            }

            StatusType.VIDEO -> {

                val bitmap2 = ThumbnailUtils.createVideoThumbnail(
                    status.path,
                    MediaStore.Images.Thumbnails.MINI_KIND
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
                                navController.navigate("${Screens.VideoStatusPreviewScreen.route}/${file.name}")
                            } catch (e: Exception) {
                                Log.i("StatusImage", e.message.toString())
                            }
                        }
                )

                Image(
                    Icons.Rounded.PlayCircleFilled,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .layoutId("statusPlayIcon")
                )

            }
        }

        IconButton(
            onClick = {
                try {
                    val file = File(status.path)
                    val dir = File("${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/")

                    if(!dir.isDirectory)
                        dir.mkdir()


                    val dest =
                        File("${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/${file.name}")

                    val inputStream: InputStream = FileInputStream(status.path)
                    val outputStream: OutputStream = FileOutputStream(dest)

                    val buf = ByteArray(1024)
                    var len: Int = inputStream.read(buf)

                    Thread(Runnable {
                        while (len > 0) {
                            outputStream.write(buf, 0, len)
                            len = inputStream.read(buf)
                        }
                        inputStream.close()
                        outputStream.close()
                    }).start()

                } catch (e: Exception) {
                    Log.i("unique", e.message.toString())
                }
            }, modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
                .layoutId("statusDownloadIcon")
        ) {
            Image(Icons.Rounded.DownloadForOffline, contentDescription = null)
        }

    }


}
