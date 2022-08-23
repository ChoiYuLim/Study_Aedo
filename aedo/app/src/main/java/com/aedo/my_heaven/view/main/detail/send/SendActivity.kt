package com.aedo.my_heaven.view.main.detail.send

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivitySendBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity

class SendActivity : BaseActivity() {
    private lateinit var mBinding: ActivitySendBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_send)
        mBinding.activity = this@SendActivity
        apiServices = ApiUtils.apiService
        inStatusBar()
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    override fun onBackPressed() {
        moveMain()
    }
}