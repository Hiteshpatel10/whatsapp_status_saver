package com.geekaid.whatsappstatussaver.util

import android.os.Environment
import android.util.Log
import com.geekaid.whatsappstatussaver.model.Status
import java.io.*

fun saveStatus(status: Status){
    try {
        val file = File(status.path)
        val dir =
            File("${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/")
        val dest =
            File("${Environment.getExternalStorageDirectory()}/WhatsApp/Media/Saved Statuses/${file.name}")

        if (!dir.isDirectory)
            dir.mkdir()

        val inputStream: InputStream = FileInputStream(status.path)
        val outputStream: OutputStream = FileOutputStream(dest)

        val buf = ByteArray(1024)
        var len: Int = inputStream.read(buf)

        Thread {
            while (len > 0) {
                outputStream.write(buf, 0, len)
                len = inputStream.read(buf)
            }
            inputStream.close()
            outputStream.close()
        }.start()

    } catch (e: Exception) {
        Log.i("unique", e.message.toString())
    }
}