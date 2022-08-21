package com.aedo.my_heaven.util.file

import android.content.Context
import android.graphics.*
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.channels.FileChannel
import java.util.*

object FileManager {
    private val defaultGalleryPath =
        Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DCIM

    fun saveImage(context: Context?, bitmap: Bitmap): Boolean {
        val root = defaultGalleryPath
        val mydir = File(root, System.currentTimeMillis().toString() + ".jpg")
        var out: FileOutputStream? = null
        var isDirCreate = false
        try {
            isDirCreate = mydir.createNewFile()
            out = FileOutputStream(mydir)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out)
        } catch (e: IOException) {
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
            }
        }
        if (isDirCreate) {
            MediaScannerConnection.scanFile(context, arrayOf(mydir.toString()), null, null)
        }
        return isDirCreate
    }

    fun cutBitmap(bitmap: Bitmap?, startX: Int, startY: Int, width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(bitmap!!, startX, startY, width, height)
    }

    fun cutBitmapByLicenseSize(bitmap: Bitmap): Bitmap {
        val startXRatio = (bitmap.width / 9f).toInt()
        val startYRatio = (bitmap.height / 3.34).toInt()
        val cameraAreaWidthRatio = (bitmap.width / 1.28571429f).toInt()
        val cameraAredHeightRatio = (bitmap.height / 3.57541899f).toInt()
        return Bitmap.createBitmap(
            bitmap,
            startXRatio,
            startYRatio,
            cameraAreaWidthRatio,
            cameraAredHeightRatio
        )
    }

    fun rotateBitmap(bitmap: Bitmap, rotateDegree: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Matrix()
        matrix.postRotate(rotateDegree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    fun resizeBitmap(bitmap: Bitmap, divisionRatio: Float): Bitmap {
        return Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width / divisionRatio).toInt(), (bitmap.height / divisionRatio).toInt(), true
        )
    }

    fun resizeBitmapFHD(bitmap: Bitmap?): Bitmap {
        return Bitmap.createScaledBitmap(bitmap!!, 1080, 1920, true)
    }

    fun resizeBitmap(bitmap: Bitmap?, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap!!, width, height, true)
    }

    fun resizeBitmap(bitmap: Bitmap?, baseRes: Int): Bitmap? {
        // 1. 가로 세로를 구함
        // 2. 가로 세로 변수를 정의s
        if (bitmap == null) {
            return null
        }
        val isLandscape = Math.max(bitmap.width, bitmap.height) == bitmap.width
        var size1 = Math.max(bitmap.width, bitmap.height)
        var size2 = Math.min(bitmap.width, bitmap.height)
        if (size1 == 0 || size2 == 0) {
            return bitmap
        }
        // 3. 가로 사이즈가 baseRes보다 작으면 return
        if (size1 <= baseRes) {
            return bitmap
        } else {
            // 5. 가로 사이즈에서 baseRes만큼 차를 구함
            // 6. 구한 차에서 가로 사이즈의 비율을 구함
            val ratioRes = size1 - baseRes
            val resizeRatio = size1.toFloat() / ratioRes

            // 7. 가로사이즈를 baseRes 값으로 고정
            size1 = baseRes
            val bigSize2 = BigDecimal(size2.toString())
            val bigRatio = BigDecimal(resizeRatio.toString())
            // 8. 세로 사이즈에서 가로 해상도 차 비율만큼 값읓 뺌
            size2 = bigSize2.subtract(bigSize2.divide(bigRatio, RoundingMode.HALF_UP)).toInt()
        }
        // 9. 가로 세로 사이즈 정립 후 bitmap scale
        return Bitmap.createScaledBitmap(
            bitmap,
            if (isLandscape) size1 else size2,
            if (isLandscape) size2 else size1,
            true
        )
    }

    fun convertToMutable(context: Context, imgIn: Bitmap): Bitmap? {
        val width = imgIn.width
        val height = imgIn.height
        val type = imgIn.config
        var outputFile: File? = null
        val outputDir = context.cacheDir
        var randomAccessFile: RandomAccessFile? = null
        var channel: FileChannel? = null
        try {
            outputFile = File.createTempFile(
                java.lang.Long.toString(System.currentTimeMillis()),
                null,
                outputDir
            )
            outputFile.deleteOnExit()
            randomAccessFile = RandomAccessFile(outputFile, "rw")
            channel = randomAccessFile.channel
            val map =
                channel.map(FileChannel.MapMode.READ_WRITE, 0, (imgIn.rowBytes * height).toLong())
            imgIn.copyPixelsToBuffer(map)
            imgIn.recycle()
            val result = Bitmap.createBitmap(width, height, type)
            map.position(0)
            result.copyPixelsFromBuffer(map)
            channel.close()
            randomAccessFile.close()
            return result
        } catch (e: IOException) {
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close()
                } catch (e: IOException) {
                }
            }
            if (channel != null) {
                try {
                    channel.close()
                } catch (e: IOException) {
                }
            }
        }
        return null
    }

    fun convertCornerRound(bitmap: Bitmap?, round: Float): Bitmap? {
        if (bitmap == null) {
            return null
        }
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun rotateImage(context: Context, selectedImageUri: Uri): Bitmap? {
        try {
            val bm = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImageUri)
            val exif = ExifInterface(Objects.requireNonNull(selectedImageUri.path)!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            var angle = 0
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270
                else -> {
                }
            }
            val mat = Matrix()
            if (angle == 0 && bm.width > bm.height) {
                mat.postRotate(90f)
            } else {
                mat.postRotate(angle.toFloat())
            }
            return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, mat, true)
        } catch (e: IOException) {
            Log.e("", "-- Error in setting image")
        } catch (oom: OutOfMemoryError) {
            Log.e("", "-- OOM Error in setting image")
        }
        return null
    }

    fun rotateImage(bm: Bitmap, selectedImageUri: Uri): Bitmap? {
        try {
            val exif = ExifInterface(Objects.requireNonNull(selectedImageUri.path)!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            var angle = 0
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270
                else -> {
                }
            }
            val mat = Matrix()
            if (angle == 0 && bm.width > bm.height) {
                mat.postRotate(90f)
            } else {
                mat.postRotate(angle.toFloat())
            }
            return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, mat, true)
        } catch (e: IOException) {
            Log.e("", "-- Error in setting image")
        } catch (oom: OutOfMemoryError) {
            Log.e("", "-- OOM Error in setting image")
        }
        return null
    }
}