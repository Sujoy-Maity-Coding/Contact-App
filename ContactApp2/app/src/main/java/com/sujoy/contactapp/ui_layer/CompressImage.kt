package com.sujoy.contactapp.ui_layer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun compressImage(imageData: ByteArray, maxSize: Int = 1000): ByteArray {
    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    val outputStream = ByteArrayOutputStream()

    // Compress the image
    var quality = 100
    var compressedSize: Int
    do {
        outputStream.reset()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val byteArray = outputStream.toByteArray()
        compressedSize = byteArray.size
        quality -= 5
    } while (compressedSize > maxSize && quality > 0)

    return outputStream.toByteArray()
}
