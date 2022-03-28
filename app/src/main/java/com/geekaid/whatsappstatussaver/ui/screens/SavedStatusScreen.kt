package com.geekaid.whatsappstatussaver.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.geekaid.whatsappstatussaver.Constants
import com.geekaid.whatsappstatussaver.MainViewModel

@ExperimentalFoundationApi
@Composable
fun SavedStatusScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    var fabTabIndex by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = true ){
        mainViewModel.getSavedStatus()
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = fabTabIndex) {
                Constants.topRow.forEachIndexed { index, screens ->
                    Tab(
                        selected = index == fabTabIndex,
                        onClick = {
                            fabTabIndex = index
                        },
                        text = { Text(text = screens.title) },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }
    ) {

        when (fabTabIndex) {

            0 -> ImageStatusListScreen(imageStatusList = mainViewModel.imageListSaved, navController = navController)
            1 -> VideoStatusListScreen(videoStatusList = mainViewModel.videoListSaved, navController = navController)
        }

    }
}