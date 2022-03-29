package com.geekaid.whatsappstatussaver

import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geekaid.whatsappstatussaver.model.Status
import com.geekaid.whatsappstatussaver.model.StatusType
import com.google.android.gms.ads.interstitial.InterstitialAd
import java.io.File

class MainViewModel: ViewModel() {

    private var filesList: MutableList<File> = mutableListOf()
    var imageList: MutableList<Status> = mutableListOf()
    var videoList: MutableList<Status> = mutableListOf()

    private var filesListSaved: MutableList<File> = mutableListOf()
    var imageListSaved: MutableList<Status> = mutableListOf()
    var videoListSaved: MutableList<Status> = mutableListOf()

    var tabIndex: MutableState<Int> = mutableStateOf(0)
    var tabIndexSaved: MutableState<Int> = mutableStateOf(0)

    //Ads
    var mInterstitialAdSave: InterstitialAd? = null
    var mInterstitialAdDelete: InterstitialAd? = null
    var mInterstitialAdPreview: InterstitialAd? = null


    fun getStatus() {

        imageList.clear()
        videoList.clear()

        val path = "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/.Statuses/"
        val files = File(path)

        if (files.listFiles() != null) {
            filesList = files.listFiles().toMutableList()

            filesList.iterator().forEach {

                when (it.extension) {
                    "jpg" -> imageList.add(
                        Status(
                            it.absolutePath,
                            StatusType.IMAGE
                        )
                    )

                    "mp4" -> videoList.add(
                        Status(
                            it.absolutePath,
                            StatusType.VIDEO
                        )
                    )
                }
            }
        }
    }

    fun getSavedStatus() {

        imageListSaved.clear()
        videoListSaved.clear()

        val path = "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/"
        val savedStatus = File(path)

        if (savedStatus.listFiles() != null) {

            filesListSaved = savedStatus.listFiles().toMutableList()
            filesListSaved.iterator().forEach {

                when (it.extension) {
                    "jpg" -> imageListSaved.add(
                        Status(
                            it.absolutePath,
                            StatusType.IMAGE
                        )
                    )

                    "mp4" -> videoListSaved.add(
                        Status(
                            it.absolutePath,
                            StatusType.VIDEO
                        )
                    )

                }
            }
        }
    }


}