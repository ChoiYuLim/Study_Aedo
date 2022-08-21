package com.aedo.my_heaven.view.side.list.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityWaringBinding
import com.aedo.my_heaven.util.base.BaseActivity

class WaringActivity : BaseActivity() {

    private lateinit var mBinding: ActivityWaringBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waring)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_waring)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        inStatusBar()

    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onOkClick(v: View) {

    }
}