package com.aedo.my_heaven.view.intro.permission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.ActivityPermissionBinding
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.view.intro.SplashActivity
import java.util.*

class PermissionActivity : BaseActivity() {
    private lateinit var binding: ActivityPermissionBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission)
        binding.activity=this@PermissionActivity
        binding.lifecycleOwner = this

        inStatusBar()

    }

    fun onConfirmClick(v: View?) {
        if (!PermissionManager.getAllPermissionGranted(this)) {
            PermissionManager.requestAllPermissions(this)
        } else {
            prefs.setBool(Constant.PREF_PERMISSION_GRANTED, true)
            moveActivity(b = true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.ALL_PERMISSION_REQUEST_CODE) {
            if (PermissionManager.getAllPermissionGranted(this)) {
                prefs.setBool(Constant.PREF_PERMISSION_GRANTED, true)
                moveActivity(b = true)
            }
            else {
                if (getPermissionAllGranted()) {
                    alert!!.showDialog(getString(R.string.permission_error_all_agree)) { }
                }
                else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
    }

    private fun getPermissionAllGranted(): Boolean {
        var permissonNotShowDenied = true
        for (permission in Constant.MUTILE_PERMISSION) {
            if (!permission?.let {
                    shouldShowRequestPermissionRationale(it)
                }!!) {
                permissonNotShowDenied = false
            }
        }
        return permissonNotShowDenied
    }

    private fun moveActivity(b: Boolean) {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }
}