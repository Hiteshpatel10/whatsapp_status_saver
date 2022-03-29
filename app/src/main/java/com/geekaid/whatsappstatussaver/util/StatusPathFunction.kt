package com.geekaid.whatsappstatussaver.util

import android.os.Environment

fun statusPath(file: String, navigatedFromDashboard: Boolean): String {

    val path = when (navigatedFromDashboard) {
        true -> "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/.Statuses/$file"
        false -> "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/$file"
        else -> "${Environment.getExternalStorageDirectory()}/WhatsApp/Media/.Statuses/$file"
    }

    return path
}