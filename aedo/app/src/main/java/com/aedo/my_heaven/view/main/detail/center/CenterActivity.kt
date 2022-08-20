package com.aedo.my_heaven.view.main.detail.center

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityCenterBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity

class CenterActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCenterBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_center)
        mBinding.activity=this@CenterActivity
        apiServices = ApiUtils.apiService
        inStatusBar()
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onNoticeClick(v: View) {
        moveNotice()
    }

    fun onFAQClick(v: View) {
        moveFAQ()
    }

    fun onCounCLick(v: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Xuvxeb/chat"))
        startActivity(intent)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}