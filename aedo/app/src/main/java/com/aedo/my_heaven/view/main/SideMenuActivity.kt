package com.aedo.my_heaven.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivitySideMenuBinding
import com.aedo.my_heaven.model.restapi.login.GetUser
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SideMenuActivity : BaseActivity() {
    private lateinit var mBinding: ActivitySideMenuBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_side_menu)
        mBinding.activity = this@SideMenuActivity
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        inStatusBar()
        initAPI()
    }

    private fun initAPI() {
        LLog.e("사이드_회원정보_첫번째 API")
        apiServices.getUser(prefs.myaccesstoken).enqueue(object :
            Callback<GetUser> {
            override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "GetUser API SUCCESS -> $result")
                    mBinding.sideTxName.text = result.user?.name.toString()
                } else {
                    Log.d(LLog.TAG, "GetUser API ERROR -> ${response.errorBody()}")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<GetUser>, t: Throwable) {
                Log.d(LLog.TAG, "GetUser ERROR -> $t")

            }
        })
    }

    private fun otherAPI() {
        LLog.e("사이드_회원정보_두번째 API")
        apiServices.getUser(prefs.newaccesstoken).enqueue(object :
            Callback<GetUser> {
            override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "GetUser Second API SUCCESS -> $result")
                    mBinding.sideTxName.text = result.user?.name.toString()
                } else {
                    Log.d(LLog.TAG, "GetUser Second API ERROR -> ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<GetUser>, t: Throwable) {
                Log.d(LLog.TAG, "GetUser Second ERROR -> $t")

            }
        })
    }

    fun onBackClick(v: View?) {
        moveMain()
    }

    fun onInfoClick(v: View) {
        moveGuide()
    }

    fun onListClick(v: View) {
        moveList()
    }

    fun onOrderClick(v: View) {
        moveMyOrder()
    }

    fun onCompany(v: View) {
        moveInfor()
    }

    fun onThanksClick(v: View) {
        moveThanks()
    }

    fun onFAQClick(v: View) {
        moveFAQ()
    }

    fun onKAKAOClick(v: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Xuvxeb/chat"))
        startActivity(intent)
    }

    fun onCounClick(v: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_Xuvxeb/chat"))
        startActivity(intent)
    }

    override fun onBackPressed() {
        moveMain()
    }
}