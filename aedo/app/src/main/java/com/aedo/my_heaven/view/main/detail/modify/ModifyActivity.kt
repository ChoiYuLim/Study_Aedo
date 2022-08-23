package com.aedo.my_heaven.view.main.detail.modify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityModifyBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity

class ModifyActivity : BaseActivity() {
    private lateinit var mBinding: ActivityModifyBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify)
        mBinding.activity = this@ModifyActivity
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