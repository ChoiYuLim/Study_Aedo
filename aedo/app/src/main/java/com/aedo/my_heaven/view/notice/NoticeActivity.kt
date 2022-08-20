package com.aedo.my_heaven.view.notice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.adapter.NoticeAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityNoticeBinding
import com.aedo.my_heaven.model.notice.Announcement
import com.aedo.my_heaven.model.notice.NoticeModel
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.MainActivity
import com.aedo.my_heaven.view.main.detail.center.CenterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : BaseActivity() {
    private lateinit var mBinding: ActivityNoticeBinding
    private lateinit var apiServices: APIService
    private var noticeAdapter : NoticeAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notice)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        inStatusBar()
        inNoticeAPI()
    }

    private fun inNoticeAPI() {
        LLog.e("공지사항_첫번째 API")
        val vercall: Call<NoticeModel> = apiServices.getNoti(prefs.myaccesstoken)
        vercall.enqueue(object : Callback<NoticeModel> {
            override fun onResponse(call: Call<NoticeModel>, response: Response<NoticeModel>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"NoticeModel response SUCCESS -> $result")
                    setAdapter(result.announcement!!)
                }
                else {
                    Log.d(LLog.TAG,"NoticeModel response ERROR -> $result")
                    otherAPI()
                }
            }
            override fun onFailure(call: Call<NoticeModel>, t: Throwable) {
                Log.d(LLog.TAG, "NoticeModel Fail -> $t")
            }
        })
    }

    private fun otherAPI() {
        LLog.e("공지사항_두번째 API")
        val vercall: Call<NoticeModel> = apiServices.getNoti(prefs.newaccesstoken)
        vercall.enqueue(object : Callback<NoticeModel> {
            override fun onResponse(call: Call<NoticeModel>, response: Response<NoticeModel>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"NoticeModel Second response SUCCESS -> $result")
                    setAdapter(result.announcement!!)
                }
                else {
                    Log.d(LLog.TAG,"NoticeModel Second response ERROR -> $result")
                }
            }
            override fun onFailure(call: Call<NoticeModel>, t: Throwable) {
                Log.d(LLog.TAG, "NoticeModel Second Fail -> $t")
            }
        })
    }

    private fun setAdapter(result: List<Announcement>) {
        val adapter = NoticeAdapter(result,this)
        val rv = findViewById<View>(R.id.medical_recyclerView) as RecyclerView
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CenterActivity::class.java))
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        noticeAdapter?.notifyDataSetChanged()
    }

}