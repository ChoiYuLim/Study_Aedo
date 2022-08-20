package com.aedo.my_heaven.view.notice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityNoticeDetailBinding
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_CONTENT
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_CREATED
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_TITLE
import com.aedo.my_heaven.util.base.BaseActivity

class NoticeDetailActivity : BaseActivity() {
    private lateinit var mBinding: ActivityNoticeDetailBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        inStatusBar()
        inRecycler()
    }

    private fun inRecycler() {
        val title = intent.getStringExtra(NOTICE_TITLE)
        val content = intent.getStringExtra(NOTICE_CONTENT)
        val created = intent.getStringExtra(NOTICE_CREATED)

        mBinding.tvNoticeDetailTitle.text = title.toString()
        mBinding.tvNoticeDetail.text = content.toString()
        mBinding.tvNoticeTimestamp.text = created.toString()
    }

    fun onBackPressed(v: View) {
        moveMain()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, NoticeActivity::class.java))
        finish()
    }
}