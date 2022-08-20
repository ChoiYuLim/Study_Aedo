package com.aedo.my_heaven.util.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson

open class BaseWebInferface {
    var activity: Activity? = null
    var gson: Gson? = null
    var selectLocationLauncher: ActivityResultLauncher<Intent>? = null

    open fun selectLocationSelect() {}

    open fun selectLocationSelect(json: String?) {}

    open fun showLocationPlace(json: String?) {}
}