package com.aedo.my_heaven.view.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityMainBinding
import com.aedo.my_heaven.util.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var apiServices: APIService
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.activity = this@MainActivity
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        inStatusBar()
    }

    fun onSideClick(v: View) {
        moveSide()
    }

    fun onSetting(v: View) {
        moveSetting()
    }

    fun onMakeClick(v: View) {
        moveMake()
    }

    fun onSendClick(v: View) {
        moveList()
    }

    fun onCenterClick(v: View) {
        moveCenter()
    }

    fun onSearchClick(v: View) {
        moveSearch()
    }

    fun onShopClick(v: View) {
        moveShop()
    }

    fun onNoticeCLick(v: View) {
        moveNotice()
    }

    private fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false
            }
        }
    }


}