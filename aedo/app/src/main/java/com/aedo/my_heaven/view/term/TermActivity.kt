package com.aedo.my_heaven.view.term

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityTermBinding
import com.aedo.my_heaven.model.restapi.base.Terms
import com.aedo.my_heaven.model.restapi.base.TremModel
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.MainActivity
import com.aedo.my_heaven.view.side.setting.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermActivity : BaseActivity() {
    private lateinit var mBinding: ActivityTermBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_term)
        mBinding.activity = this
        mBinding.lifecycleOwner = this

        apiServices = ApiUtils.apiService
        inStatusBar()
        initTermAPI()
    }

    private fun initTermAPI() {
        LLog.e("이용약관 API")
        apiServices.getTerms().enqueue(object : Callback<TremModel> {
            override fun onResponse(call: Call<TremModel>, response: Response<TremModel>) {
                val result = response.body()
                if(response.isSuccessful&& result!= null) {
                    Log.d(LLog.TAG,"TremModel API SUCCESS -> $result")
                    terms(result.terms)
                }
                else {
                    Log.d(LLog.TAG,"TremModel API ERROR -> ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TremModel>, t: Throwable) {
                Log.d(LLog.TAG,"TremModel ERROR -> $t")

            }
        })
    }

    private fun terms(terms: Terms?) {
        mBinding.tvFirst.text = terms?.title
        mBinding.tvSubTitle.text = terms?.sub_title
        mBinding.tvDetailTermsFirst.text = terms?.first
        mBinding.tvDetailTermsSecond.text = terms?.second
        mBinding.tvDetailTermsThird.text = terms?.third
        mBinding.tvDetailTermsFour.text = terms?.fourth
        mBinding.tvDetailTermsFive.text = terms?.fifth
    }

    fun onBackClick(v: View) {
        moveSetting()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SettingActivity::class.java))
        finish()
    }
}