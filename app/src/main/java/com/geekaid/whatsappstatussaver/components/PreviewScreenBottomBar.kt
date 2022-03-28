package com.geekaid.whatsappstatussaver.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.geekaid.whatsappstatussaver.model.Status
import java.io.File

@Composable
fun PreviewScreenBottomBar(statusPath: String) {

    val file = File(statusPath)
    val isDeleteButtonVisible by remember { mutableStateOf(file.exists()) }
    Log.i("preview","$isDeleteButtonVisible")

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

            }
        )


            BottomNavigationItem(

                label = {
                    Text(text = "Save")
                },

                icon = {
                    Icon(imageVector = Icons.Filled.Download, contentDescription = "Save")
                },

                selected = true,

                onClick = {

                }
            )


            BottomNavigationItem(

                label = {
                    Text(text = "Delete")
                },

                icon = {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                },

                selected = true,

                onClick = {

                }
            )
    }

}
