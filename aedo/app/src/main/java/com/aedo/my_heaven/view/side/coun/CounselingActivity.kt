package com.aedo.my_heaven.view.side.coun

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
import com.aedo.my_heaven.adapter.CounSelingAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityCounselingBinding
import com.aedo.my_heaven.model.coun.CounGet
import com.aedo.my_heaven.model.coun.CounList
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.detail.center.CenterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CounselingActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCounselingBinding
    private lateinit var apiServices: APIService
    private var counAdapter: CounSelingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_counseling)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        inStatusBar()
        initRecycler()
    }

    private fun initRecycler() {
        LLog.e("1:1문의_첫번째 API")
        val vercall: Call<CounGet> = apiServices.getCounRequest(prefs.myaccesstoken)
        vercall.enqueue(object : Callback<CounGet> {
            override fun onResponse(call: Call<CounGet>, response: Response<CounGet>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "CounGet response SUCCESS -> $result")
                    setAdapter(result.userRequest!!)
                } else {
                    Log.d(LLog.TAG, "CounGet response ERROR -> $result")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<CounGet>, t: Throwable) {
                Log.d(LLog.TAG, "CounGet Fail -> $t")
            }
        })
    }

    private fun otherAPI() {
        LLog.e("1:1문의_두번째 API")
        val vercall: Call<CounGet> = apiServices.getCounRequest(prefs.newaccesstoken)
        vercall.enqueue(object : Callback<CounGet> {
            override fun onResponse(call: Call<CounGet>, response: Response<CounGet>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "CounGet Second response SUCCESS -> $result")
                    setAdapter(result.userRequest!!)
                } else {
                    Log.d(LLog.TAG, "CounGet Second response ERROR -> $result")
                }
            }

            override fun onFailure(call: Call<CounGet>, t: Throwable) {
                Log.d(LLog.TAG, "CounGet Second Fail -> $t")
            }
        })
    }

    private fun setAdapter(userRequest: List<CounList>) {
        val adapter = CounSelingAdapter(userRequest, this)
        val rv = findViewById<View>(R.id.counSeling_recyclerView) as RecyclerView
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onCounClick(v: View) {
        moveUploadCoun()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CenterActivity::class.java))
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        counAdapter?.notifyDataSetChanged()
    }

}