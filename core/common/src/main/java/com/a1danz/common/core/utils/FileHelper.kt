package com.a1danz.common.core.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FileHelper {
}

fun Uri.toFile(context: Context): File {
    val contentResolver = context.contentResolver
    val file = createTempFile(context, contentResolver.getType(this))
    (contentResolver.openInputStream(this))?.use { input ->
        val outputStream: OutputStream = FileOutputStream(file)
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file
}

private fun createTempFile(context: Context, mimeType: String?): File {
    val tempDir = context.cacheDir
    val fileExtension = when(mimeType) {
        "image/jpeg" -> ".jpg"
        "image/png" -> ".png"
        "image/pjpeg" -> ".jpeg"
        else -> ".tmp"
    }
    return File.createTempFile("img_${System.currentTimeMillis()}", fileExtension, tempDir)
}