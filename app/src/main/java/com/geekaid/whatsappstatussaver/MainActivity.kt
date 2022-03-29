package com.geekaid.whatsappstatussaver

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekaid.whatsappstatussaver.components.BottomNavigationBar
import com.geekaid.whatsappstatussaver.components.loadInterstitialDelete
import com.geekaid.whatsappstatussaver.components.loadInterstitialPreview
import com.geekaid.whatsappstatussaver.components.loadInterstitialSave
import com.geekaid.whatsappstatussaver.navigation.Navigation
import com.geekaid.whatsappstatussaver.navigation.Screens
import com.geekaid.whatsappstatussaver.ui.theme.WhatsappStatusSaverTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.ads.MobileAds

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsappStatusSaverTheme {

                val navHostController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()

                MobileAds.initialize(this)

                loadInterstitialDelete(
                    context = applicationContext,
                    mainViewModel = mainViewModel,
                    adUnitId = this.getString(R.string.interstitial_delete)
                )

                loadInterstitialSave(
                    context = applicationContext,
                    mainViewModel = mainViewModel,
                    adUnitId = this.getString(R.string.interstitial_save)
                )

                loadInterstitialPreview(
                    context = applicationContext,
                    mainViewModel = mainViewModel,
                    adUnitId = this.getString(R.string.interstitial_preview)
                )

                val permissionsStateMultiple = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    )
                )

                val lifecycleOwner = LocalLifecycleOwner.current

                DisposableEffect(key1 = lifecycleOwner, effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        permissionsStateMultiple.launchMultiplePermissionRequest()

                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                })


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (bottomNavVisibility(navController = navHostController))
                                BottomNavigationBar(navController = navHostController)
                        }
                    ) {
                        Navigation(navController = navHostController, mainViewModel = mainViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun bottomNavVisibility(navController: NavController): Boolean {

    var isBottomNavVisible by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        "${Screens.ImageStatusPreviewScreen.route}/{file}/{navigatedFromDashboard}" -> isBottomNavVisible =
            false
        "${Screens.VideoStatusPreviewScreen.route}/{file}/{navigatedFromDashboard}" -> isBottomNavVisible =
            false
        Screens.DashboardScreenNav.route -> isBottomNavVisible = true
        Screens.SavedStatusScreenNav.route -> isBottomNavVisible = true
    }
    return isBottomNavVisible
}

