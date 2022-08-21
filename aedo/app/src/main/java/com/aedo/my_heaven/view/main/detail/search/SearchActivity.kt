package com.aedo.my_heaven.view.main.detail.search

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
import com.aedo.my_heaven.R.layout.activity_search
import com.aedo.my_heaven.adapter.SearchAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivitySearchBinding
import com.aedo.my_heaven.model.restapi.base.CreateName
import com.aedo.my_heaven.model.restapi.base.CreateSearch
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity() {
    private lateinit var mBinding: ActivitySearchBinding
    private lateinit var apiServices: APIService
    private var searchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, activity_search)
        mBinding.activity = this@SearchActivity
        apiServices = ApiUtils.apiService
        inStatusBar()
    }

    private fun initSearchAPI() {
        LLog.e("검색_첫번째 API")
        val search = mBinding.etSearch.text.toString()
        val vercall: Call<CreateName> = apiServices.getCreateName(search, prefs.myaccesstoken)
        vercall.enqueue(object : Callback<CreateName> {
            override fun onResponse(call: Call<CreateName>, response: Response<CreateName>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "Search response SUCCESS -> $result")
                    setAdapter(result.result)
                } else {
                    Log.d(LLog.TAG, "Search response ERROR -> $result")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<CreateName>, t: Throwable) {
                Log.d(LLog.TAG, "Search Fail -> $t")
            }
        })
    }

    private fun otherAPI() {
        LLog.e("검색_두번째 API")
        val search = mBinding.etSearch.text.toString()
        val vercall: Call<CreateName> = apiServices.getCreateName(search, prefs.newaccesstoken)
        vercall.enqueue(object : Callback<CreateName> {
            override fun onResponse(call: Call<CreateName>, response: Response<CreateName>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "Search Second response SUCCESS -> $result")
                    setAdapter(result.result)

                } else {
                    Log.d(LLog.TAG, "Search Second response ERROR -> $result")
                }
            }

            override fun onFailure(call: Call<CreateName>, t: Throwable) {
                Log.d(LLog.TAG, "Search Second Fail -> $t")
            }
        })
    }

    private fun setAdapter(search: List<CreateSearch>?) {
        val adapter = search?.let {
            SearchAdapter(it, this)
        }
        val rv = findViewById<View>(R.id.search_recyclerView) as RecyclerView
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onSearchClick(v: View) {
        val search = mBinding.etSearch.text.toString()
        if (search.isEmpty()) {
            mBinding.etSearch.error = "미입력"
        } else {
            initSearchAPI()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        searchAdapter?.notifyDataSetChanged()
    }
}