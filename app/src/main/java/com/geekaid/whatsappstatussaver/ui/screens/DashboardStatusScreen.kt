package com.geekaid.whatsappstatussaver.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.geekaid.whatsappstatussaver.MainViewModel
import com.geekaid.whatsappstatussaver.util.Constants

@ExperimentalFoundationApi
@Composable
fun DashboardScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    LaunchedEffect(key1 = true) {
        mainViewModel.getStatus()
        mainViewModel.tabIndex.value = 0
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = mainViewModel.tabIndex.value) {
                Constants.topRow.forEachIndexed { index, screens ->
                    Tab(
                        selected = index == mainViewModel.tabIndex.value,
                        onClick = {
                            mainViewModel.tabIndex.value = index
                        },
                        text = { Text(text = screens.title) },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }
    ) {

        when (mainViewModel.tabIndex.value) {

            0 -> ImageStatusListScreen(
                imageStatusList = mainViewModel.imageList,
                navController = navController,
                mainViewModel = mainViewModel
            )

            1 -> VideoStatusListScreen(
                videoStatusList = mainViewModel.videoList,
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

    }
}