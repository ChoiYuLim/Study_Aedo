package com.aedo.my_heaven.util.activity

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.ToastCustomBinding
import com.aedo.my_heaven.util.`object`.Constant

class BackPressCloseHandler(private val activity: Activity) : ActivityCompat() {
    private var toast: Toast? = null
    private var pressedTime: Long = 0

    fun onBackPressed() {
        if (System.currentTimeMillis() > pressedTime + Constant.BACKPRESS_CLOSE_TIME) {
            pressedTime = System.currentTimeMillis()
            showToast()
        } else if (System.currentTimeMillis() <= pressedTime + Constant.BACKPRESS_CLOSE_TIME) {
            finishAffinity(activity)
            toast!!.cancel()
        }
    }

    fun onBackPressed(listener: BackPressedListener) {
        if (System.currentTimeMillis() > pressedTime + Constant.BACKPRESS_CLOSE_TIME) {
            pressedTime = System.currentTimeMillis()
            showToast()
        } else if (System.currentTimeMillis() <= pressedTime + Constant.BACKPRESS_CLOSE_TIME) {
            listener.onBackPressFinish()
            toast!!.cancel()
        }
    }

    private fun showToast() {
        val binding: ToastCustomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                activity
            ), R.layout.toast_custom, null, false
        )
        binding.tvContent.text = activity.getString(R.string.backpress_exit)
        toast = Toast(activity)
        toast!!.setGravity(
            Gravity.BOTTOM,
            0,
            activity.resources.getDimensionPixelSize(R.dimen.common_toast_bottom_margin)
        )
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.view = binding.root
        toast!!.show()
    }
}