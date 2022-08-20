package com.aedo.my_heaven.view.side.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityGuideBinding
import com.aedo.my_heaven.databinding.ActivityThanksBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity
import com.aedo.my_heaven.view.main.SideMenuActivity

class GuideActivity : BaseActivity() {
    private lateinit var mBinding: ActivityGuideBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        inStatusBar()
    }

    fun onBackClick(v: View) {
        moveSide()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SideMenuActivity::class.java))
        finish()
    }
}