package com.aedo.my_heaven.view.side.information

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityInforMationBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.SideMenuActivity

class InforMationActivity : BaseActivity() {
    private lateinit var mBinding: ActivityInforMationBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_infor_mation)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        inStatusBar()
    }

    fun onBackClick(v: View) {
        moveSide()
    }

    override fun onBackPressed() {
        moveSide()
    }
}