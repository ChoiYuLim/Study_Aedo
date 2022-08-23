package com.aedo.my_heaven.view.main.detail.shop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.adapter.MyOrderAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityMyOrderDetailBinding
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_CREATED
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_ITEM
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PLACE_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PLACE_NUMBER
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PRICE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_RECEIVER_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_RECEIVER_PHONE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_SENDER_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_SENDER_PHONE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_COMPANY
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_WORD
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_WORDSECOND
import com.aedo.my_heaven.util.base.BaseActivity


class MyOrderDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMyOrderDetailBinding
    private lateinit var apiServices: APIService
    private var readapter: MyOrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_order_detail)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        inStatusBar()
        initRecyclerView()
    }

    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        val place_name = intent.getStringExtra(MY_ORDER_PLACE_NAME)
        val place_number = intent.getStringExtra(MY_ORDER_PLACE_NUMBER)
        val item = intent.getStringExtra(MY_ORDER_ITEM)
        val price = intent.getStringExtra(MY_ORDER_PRICE)
        val receiver_name = intent.getStringExtra(MY_ORDER_RECEIVER_NAME)
        val receiver_phone = intent.getStringExtra(MY_ORDER_RECEIVER_PHONE)
        val sender_name = intent.getStringExtra(MY_ORDER_SENDER_NAME)
        val sender_phone = intent.getStringExtra(MY_ORDER_SENDER_PHONE)
        val company = intent.getStringExtra(MY_ORDER_WORD_COMPANY)
        val word = intent.getStringExtra(MY_ORDER_WORD_WORD)
        val word_second = intent.getStringExtra(MY_ORDER_WORD_WORDSECOND)
        val created = intent.getStringExtra(MY_ORDER_CREATED)

        mBinding.myorderFlower.text = item.toString()
        mBinding.tvMyorderCreate.text = created.toString()
        mBinding.tvMyorderPrice.text = price.toString()
        mBinding.tvMyorderReceiverName.text = receiver_name.toString()
        mBinding.tvMyorderReceiverPhone.text = receiver_phone.toString()
        mBinding.tvMyorderSendName.text = sender_name.toString()
        mBinding.tvMyorderSendPhone.text = sender_phone.toString()
        mBinding.tvMyorderPlace.text = place_name.toString()
        mBinding.tvMyorderPlaceNumber.text = place_number.toString()
        mBinding.tvMyorderCompany.text = company.toString()
        mBinding.tvMyorderWord.text = word.toString()
        mBinding.tvMyorderWordStright.text = word_second.toString()

    }

    fun onBackClick(v: View) {
        moveMyOrder()
    }

    fun onMainClick(v: View) {
        moveMain()
    }

    override fun onBackPressed() {
        moveMyOrder()
    }
}