package com.aedo.my_heaven.view.side.coun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityUploadCounselingBinding
import com.aedo.my_heaven.model.coun.CounPost
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.viewmodel.AgreeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class UploadCounselingActivity : BaseActivity() {
    private lateinit var mBinding: ActivityUploadCounselingBinding
    private lateinit var apiServices: APIService
    private val mViewModel: AgreeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload_counseling)
        mBinding.activity = this
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this
        apiServices = ApiUtils.apiService
        inStatusBar()
        initView()
    }

    private fun initView() {
        val onlyDate: LocalDate = LocalDate.now()
        mBinding.tvTime.text = onlyDate.toString()
    }

    fun onBackClick(v: View) {
        moveCounseling()
    }

    fun onMainClick(v: View) {
        moveMain()
    }

    fun onDataSendClick(v: View) {
        val name = mBinding.etName.text.toString()
        val phone = mBinding.etEmail.text.toString()
        val title = mBinding.etConTitle.text.toString()
        val detail = mBinding.etConDetail.text.toString()
        val time = mBinding.tvTime
        when {
            name.isEmpty() -> {
                mBinding.etName.error = "미입력"
            }
            phone.isEmpty() -> {
                mBinding.etEmail.error = "미입력"
            }
            title.isEmpty() -> {
                mBinding.etConTitle.error = "미입력"
            }
            detail.isEmpty() -> {
                mBinding.etConDetail.error = "미입력"
            }
            else -> {
                dialog?.show()
                counSelingAPI()
            }
        }
    }

    private fun counSelingAPI() {
        val name = mBinding.etName.text.toString()
        val title = mBinding.etConTitle.text.toString()
        val content = mBinding.etConDetail.text.toString()
        val time = mBinding.tvTime.text.toString()
        val data = CounPost(name, title, content, time)

        LLog.e("1:1문의 작성_첫번째 API")
        apiServices.getCounPost(prefs.myaccesstoken, data).enqueue(object :
            Callback<CounPost> {
            override fun onResponse(call: Call<CounPost>, response: Response<CounPost>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "CounPost  API SUCCESS -> $result")
                    moveCounseling()
                } else {
                    Log.d(LLog.TAG, "CounPost  API ERROR -> ${response.errorBody()}")
                    otherAPI()
                }
            }

            override fun onFailure(call: Call<CounPost>, t: Throwable) {
                Log.d(LLog.TAG, "CounPost  Fail -> $t")
            }
        })
    }

    private fun otherAPI() {
        val name = mBinding.etName.text.toString()
        val title = mBinding.etConTitle.text.toString()
        val content = mBinding.etConDetail.text.toString()
        val time = mBinding.tvTime.text.toString()
        val data = CounPost(name, title, content, time)

        LLog.e("1:1문의 작성_두번째 API")
        apiServices.getCounPost(prefs.newaccesstoken, data).enqueue(object :
            Callback<CounPost> {
            override fun onResponse(call: Call<CounPost>, response: Response<CounPost>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG, "CounPost Second API SUCCESS -> $result")
                    moveCounseling()
                } else {
                    Log.d(LLog.TAG, "CounPost Second API ERROR -> ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<CounPost>, t: Throwable) {
                Log.d(LLog.TAG, "CounPost Second Fail -> $t")
            }
        })
    }

    override fun onBackPressed() {
        moveCounseling()
    }
}