package com.aedo.my_heaven.view.faq

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityFaqactivityBinding
import com.aedo.my_heaven.util.alert.LoadingDialog
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity

class FAQActivity : BaseActivity() {
    private lateinit var mBinding: ActivityFaqactivityBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_faqactivity)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        dialog = LoadingDialog(this)
        inStatusBar()
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onFirstClick(v: View) {
        if (mBinding.clThanksFirst.visibility == View.GONE) {
            mBinding.clThanksFirst.visibility = View.VISIBLE
        } else {
            mBinding.clThanksFirst.visibility = View.GONE
        }
    }

    fun onSecondClick(v: View) {
        if (mBinding.clThanksSecond.visibility == View.GONE) {
            mBinding.clThanksSecond.visibility = View.VISIBLE
        } else {
            mBinding.clThanksSecond.visibility = View.GONE
        }
    }

    fun onThridClick(v: View) {
        if (mBinding.clThanksThrid.visibility == View.GONE) {
            mBinding.clThanksThrid.visibility = View.VISIBLE
        } else {
            mBinding.clThanksThrid.visibility = View.GONE
        }
    }

    fun onFourClick(v: View) {
        if (mBinding.clThanksFour.visibility == View.GONE) {
            mBinding.clThanksFour.visibility = View.VISIBLE
        } else {
            mBinding.clThanksFour.visibility = View.GONE
        }
    }

    fun onFiveClick(v: View) {
        if (mBinding.clThanksFive.visibility == View.GONE) {
            mBinding.clThanksFive.visibility = View.VISIBLE
        } else {
            mBinding.clThanksFive.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}