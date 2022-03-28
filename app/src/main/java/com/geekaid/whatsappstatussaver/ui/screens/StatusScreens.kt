package com.geekaid.whatsappstatussaver.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.geekaid.whatsappstatussaver.components.StatusImage
import com.geekaid.whatsappstatussaver.model.Status

@ExperimentalFoundationApi
@Composable
fun ImageStatusListScreen(imageStatusList: MutableList<Status>, navController: NavHostController) {

    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
        items(imageStatusList) { statusImage ->
            StatusImage(status = statusImage, navController = navController)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun VideoStatusListScreen(videoStatusList: MutableList<Status>, navController: NavHostController) {

    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
        items(videoStatusList) { statusImage ->
            StatusImage(status = statusImage, navController = navController)
        }
    }
}