package com.aedo.my_heaven.util.base

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import java.util.*

abstract class BaseActivityAdditional : BaseActivity() {
    open fun onStationDeploymentClick(stationInfo: Map<String?, String?>?) {
    }

    open fun onRedeployPageClick(stationInfo: HashMap<String?, String?>) {
    }

    fun getDrawableByName(name: String?): Drawable? {
        val resources = resources
        val resourceId = resources.getIdentifier(name, "drawable", packageName)
        return AppCompatResources.getDrawable(this, resourceId)
    }


}