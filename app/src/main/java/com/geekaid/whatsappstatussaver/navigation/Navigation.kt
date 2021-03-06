package com.geekaid.whatsappstatussaver.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.geekaid.whatsappstatussaver.MainViewModel
import com.geekaid.whatsappstatussaver.ui.screens.DashboardScreen
import com.geekaid.whatsappstatussaver.ui.screens.ImageStatusPreviewScreen
import com.geekaid.whatsappstatussaver.ui.screens.SavedStatusScreen
import com.geekaid.whatsappstatussaver.ui.screens.VideoStatusPreviewScreen

@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController, mainViewModel: MainViewModel) {

    NavHost(navController = navController, startDestination = Screens.DashboardScreenNav.route) {

        composable(Screens.DashboardScreenNav.route) {
            DashboardScreen(mainViewModel = mainViewModel, navController = navController)
        }

        composable(Screens.SavedStatusScreenNav.route) {
            SavedStatusScreen(mainViewModel = mainViewModel, navController = navController)
        }

        composable("${Screens.ImageStatusPreviewScreen.route}/{file}/{navigatedFromDashboard}",
            arguments = listOf(
                navArgument("file") { type = NavType.StringType },
                navArgument("navigatedFromDashboard") { type = NavType.BoolType }
            )) { navBackStackEntry ->

            ImageStatusPreviewScreen(
                file = navBackStackEntry.arguments?.getString("file"),
                navigatedFromDashboard = navBackStackEntry.arguments?.getBoolean("navigatedFromDashboard"),
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable("${Screens.VideoStatusPreviewScreen.route}/{file}/{navigatedFromDashboard}",
            arguments = listOf(
                navArgument("file") { type = NavType.StringType },
                navArgument("navigatedFromDashboard") { type = NavType.BoolType }
            )) { navBackStackEntry ->
            VideoStatusPreviewScreen(
                file = navBackStackEntry.arguments?.getString("file"),
                navigatedFromDashboard = navBackStackEntry.arguments?.getBoolean("navigatedFromDashboard"),
                navController = navController,
                mainViewModel = mainViewModel,
            )

        }

    }


}