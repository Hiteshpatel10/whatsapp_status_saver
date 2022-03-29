package com.geekaid.whatsappstatussaver.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.geekaid.whatsappstatussaver.MainViewModel
import com.geekaid.whatsappstatussaver.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


@Composable
fun BannerAdComposable(bannerAdSize: AdSize = AdSize.LARGE_BANNER) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 24.dp),
        factory = { context ->
            AdView(context).apply {
                adSize = bannerAdSize
                adUnitId = context.getString(R.string.banner_ad)
                loadAd(AdRequest.Builder().build())
            }
        }
    )

}


object InterstitialAdShow {

    fun showInterstitialAd(
        activity: Activity,
        mainViewModel: MainViewModel,
        adUnitId: String,
    ) {

        when (adUnitId) {
            activity.getString(R.string.interstitial_save) -> {
                if (mainViewModel.mInterstitialAdSave != null)
                    mainViewModel.mInterstitialAdSave?.show(activity)

                loadInterstitialSave(
                    context = activity,
                    mainViewModel = mainViewModel,
                    adUnitId = adUnitId
                )
            }

            activity.getString(R.string.interstitial_delete) -> {
                if (mainViewModel.mInterstitialAdDelete != null)
                    mainViewModel.mInterstitialAdDelete?.show(activity)

                loadInterstitialDelete(
                    context = activity,
                    mainViewModel = mainViewModel,
                    adUnitId = adUnitId
                )
            }

            activity.getString(R.string.interstitial_preview) -> {
                if (mainViewModel.mInterstitialAdPreview != null)
                    mainViewModel.mInterstitialAdPreview?.show(activity)

                loadInterstitialPreview(
                    context = activity,
                    mainViewModel = mainViewModel,
                    adUnitId = adUnitId
                )
            }
        }
    }
}


fun loadInterstitialSave(
    context: Context,
    mainViewModel: MainViewModel,
    adUnitId: String
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mainViewModel.mInterstitialAdSave = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mainViewModel.mInterstitialAdSave = interstitialAd
            }
        }
    )
}

fun loadInterstitialDelete(
    context: Context,
    mainViewModel: MainViewModel,
    adUnitId: String
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mainViewModel.mInterstitialAdDelete = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mainViewModel.mInterstitialAdDelete = interstitialAd
            }
        }
    )
}

fun loadInterstitialPreview(
    context: Context,
    mainViewModel: MainViewModel,
    adUnitId: String
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mainViewModel.mInterstitialAdPreview = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mainViewModel.mInterstitialAdPreview = interstitialAd
            }
        }
    )
}

