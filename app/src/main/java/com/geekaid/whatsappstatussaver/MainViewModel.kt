package com.geekaid.whatsappstatussaver

import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import com.geekaid.whatsappstatussaver.model.Status
import com.geekaid.whatsappstatussaver.model.StatusType
import java.io.File

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context by lazy { application.applicationContext }

    private var filesList: MutableList<File> = mutableListOf()
    var imageList: MutableList<Status> = mutableListOf()
    var videoList: MutableList<Status> = mutableListOf()

    private var filesListSaved: MutableList<File> = mutableListOf()
    var imageListSaved: MutableList<Status> = mutableListOf()
    var videoListSaved: MutableList<Status> = mutableListOf()


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

        val path = context.getExternalFilesDir(null).toString()
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