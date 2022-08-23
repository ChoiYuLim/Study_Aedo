package com.aedo.my_heaven.view.side.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aedo.my_heaven.R
import com.aedo.my_heaven.adapter.RecyclerAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityListBinding
import com.aedo.my_heaven.model.list.Obituaray
import com.aedo.my_heaven.model.list.RecyclerList
import com.aedo.my_heaven.util.alert.LoadingDialog
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.util.log.LLog.TAG
import com.aedo.my_heaven.view.main.MainActivity
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : BaseActivity() {
    private lateinit var mBinding: ActivityListBinding
    private lateinit var apiServices: APIService
    private var readapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        mBinding.activity = this@ListActivity
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this

        dialog = LoadingDialog(this)
        KakaoSdk.init(this, getString(R.string.kakao_key))
        inStatusBar()
        inRecycler()
    }


    private fun inRecycler() {
        LLog.e("부고조회_첫번째 API")
        val vercall: Call<RecyclerList> = apiServices.getCreateGet(prefs.myaccesstoken)
        vercall.enqueue(object : Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(TAG, "List response SUCCESS -> $result")
                    setAdapter(result.obituary)
                } else {
                    Log.d(TAG, "List response ERROR -> $result")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                Log.d(TAG, "List error -> $t")
            }
        })
    }

    private fun otherAPI() {
        LLog.e("부고조회_두번째 API")
        val vercall: Call<RecyclerList> = apiServices.getCreateGet(prefs.newaccesstoken)
        vercall.enqueue(object : Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(TAG, "List Second response SUCCESS -> $result")
                    setAdapter(result.obituary)
                } else {
                    Log.d(TAG, "List Second esponse ERROR -> $result")
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                Log.d(TAG, "List Second Fail -> $t")
            }
        })
    }

    private fun setAdapter(obituary: List<Obituaray>?) {
        val mAdapter = obituary?.let {
            RecyclerAdapter(it, this)
        }
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        return
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onMainClick(v: View) {
        moveMain()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        readapter?.notifyDataSetChanged()
    }


}