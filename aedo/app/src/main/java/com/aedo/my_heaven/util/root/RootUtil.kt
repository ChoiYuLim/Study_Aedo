package com.aedo.my_heaven.util.root

import android.os.Build
import com.aedo.my_heaven.util.log.LLog.e
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

object RootUtil {
    val isDeviceRooted: Boolean
        get() = checkRootMethod1() || checkRootMethod2() || checkRootMethod3()

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        var `in`: BufferedReader? = null
        var inputStreamReader: InputStreamReader? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            inputStreamReader = InputStreamReader(process.inputStream)
            `in` = BufferedReader(inputStreamReader)
            val read = `in`.readLine()
            read != null
        } catch (e: IOException) {
            false
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e(e.localizedMessage)
                }
            }
            process?.destroy()
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close()
                } catch (e: IOException) {
                    e("inputStreamReader: IOException")
                }
            }
        }
    }
}