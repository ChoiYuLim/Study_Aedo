package com.aedo.my_heaven.util.log

import android.util.Log
import com.aedo.my_heaven.BuildConfig
import com.aedo.my_heaven.util.`object`.Constant

object LLog {
    const val TAG = Constant.TAG

    // Log Level Error
    @JvmStatic
    fun e(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, buildLogMessage(message)!!)
        }
    }

    // Log Level Warning
    fun w(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, buildLogMessage(message)!!)
        }
    }

    // Log Level Information
    fun i(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, buildLogMessage(message)!!)
        }
    }

    // Log Level Debug
    fun d(message: String?, s: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, buildLogMessage(message)!!)
        }
    }

    // Log Level Verbose
    @JvmStatic
    fun v(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, buildLogMessage(message)!!)
        }
    }

    private fun buildLogMessage(message: String?): String? {
        return message
    }

}