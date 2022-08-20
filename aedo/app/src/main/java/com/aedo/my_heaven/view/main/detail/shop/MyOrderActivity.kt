package com.aedo.my_heaven.view.main.detail.shop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aedo.my_heaven.R
import com.aedo.my_heaven.adapter.MyOrderAdapter
import com.aedo.my_heaven.adapter.RecyclerAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityMyOrderBinding
import com.aedo.my_heaven.model.shop.MyOrder
import com.aedo.my_heaven.model.shop.MyOrders
import com.aedo.my_heaven.util.alert.LoadingDialog
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.main.MainActivity
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrderActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMyOrderBinding
    private lateinit var apiServices: APIService
    private var readapter: MyOrderAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        dialog = LoadingDialog(this)
        KakaoSdk.init(this, getString(R.string.kakao_key))
        inStatusBar()
        inGetOrderAPI()
    }

    private fun inGetOrderAPI() {
        LLog.e("내 주문목록 API")
        val vercall: Call<MyOrder> = apiServices.getMyOrder(prefs.myaccesstoken)
        vercall.enqueue(object : Callback<MyOrder> {
            override fun onResponse(call: Call<MyOrder>, response: Response<MyOrder>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"getMyOrder response SUCCESS -> $result")
                    setAdapter(result.order)
                }
                else {
                    Log.d(LLog.TAG,"getMyOrder response ERROR -> $result")
                    otherAPI()
                }
            }
            override fun onFailure(call: Call<MyOrder>, t: Throwable) {
                Log.d(LLog.TAG, "getMyOrder fail -> $t")
            }
        })
    }

    private fun otherAPI() {
        LLog.e("내 주문목록 두번째 API")
        val vercall: Call<MyOrder> = apiServices.getMyOrder(prefs.newaccesstoken)
        vercall.enqueue(object : Callback<MyOrder> {
            override fun onResponse(call: Call<MyOrder>, response: Response<MyOrder>) {

                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"getMyOrder second response SUCCESS -> $result")
                    setAdapter(result.order)

                }
                else {
                    Log.d(LLog.TAG,"getMyOrder second response ERROR -> $result")
                }
            }
            override fun onFailure(call: Call<MyOrder>, t: Throwable) {
                Log.d(LLog.TAG, "getMyOrder second fail -> $t")
            }
        })
    }

    private fun setAdapter(order: List<MyOrders>?) {
        val mAdapter = order?.let {
            MyOrderAdapter(it,this)
        }
        mBinding.recyclerViewOrder.adapter = mAdapter
        mBinding.recyclerViewOrder.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerViewOrder.setHasFixedSize(true)
        mBinding.recyclerViewOrder.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        return
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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