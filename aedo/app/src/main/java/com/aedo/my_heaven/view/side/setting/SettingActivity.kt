package com.aedo.my_heaven.view.side.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.BuildConfig
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivitySettingBinding
import com.aedo.my_heaven.model.restapi.login.LogOut
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : BaseActivity() {
    private lateinit var mBinding: ActivitySettingBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        mBinding.activity = this@SettingActivity
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        inStatusBar()
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val versionNames = BuildConfig.VERSION_NAME
        mBinding.tvVersion.text = "v $versionNames"
    }


    fun onLogOutClick(v: View) {
        logOutAPI()
    }

    private fun logOutAPI() {
        LLog.e("로그아웃_첫번째 API")
        apiServices.getLogOut(MyApplication.prefs.myaccesstoken).enqueue(object :
            Callback<LogOut> {
            override fun onResponse(call: Call<LogOut>, response: Response<LogOut>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "GetUser API SUCCESS -> $result")
                    moveLogins()
                } else {
                    Log.d(LLog.TAG, "GetUser API ERROR -> ${response.errorBody()}")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<LogOut>, t: Throwable) {
                Log.d(LLog.TAG, "GetUser ERROR -> $t")

            }
        })
    }

    private fun otherAPI() {
        LLog.e("로그아웃_두번째 API")
        apiServices.getLogOut(MyApplication.prefs.newaccesstoken).enqueue(object :
            Callback<LogOut> {
            override fun onResponse(call: Call<LogOut>, response: Response<LogOut>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "GetUser API SUCCESS -> $result")
                    moveLogins()
                } else {
                    Log.d(LLog.TAG, "GetUser API ERROR -> ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<LogOut>, t: Throwable) {
                Log.d(LLog.TAG, "GetUser ERROR -> $t")

            }
        })
    }

    fun onOutClick(v: View) {
        logOutAPI()
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onTermsClick(v: View) {
        moveTerms()
    }

    fun ontermsinforClick(v: View) {
        moveTerms()
    }

    fun ontermsotherClick(v: View) {
        moveTerms()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}