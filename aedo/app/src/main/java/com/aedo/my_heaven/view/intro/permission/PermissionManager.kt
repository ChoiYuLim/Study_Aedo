package com.aedo.my_heaven.view.intro.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aedo.my_heaven.util.`object`.Constant

object PermissionManager {

    fun getPermissionGranted(context: Context?, permissionCode: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            permissionCode!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getAllPermissionGranted(context: Context?): Boolean {
        var allGranted = true
        for (permissionCode in Constant.MUTILE_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    permissionCode!!
                ) == PackageManager.PERMISSION_DENIED
            ) {
                allGranted = false
            }
        }
        return allGranted
    }

    fun requestAllPermissions(context: Context?) {
        val arraySize: Int = Constant.MUTILE_PERMISSION.size
        ActivityCompat.requestPermissions(
            (context as Activity?)!!,
            Constant.MUTILE_PERMISSION.toArray(arrayOfNulls<String>(arraySize)),
            Constant.ALL_PERMISSION_REQUEST_CODE
        )
    }
}