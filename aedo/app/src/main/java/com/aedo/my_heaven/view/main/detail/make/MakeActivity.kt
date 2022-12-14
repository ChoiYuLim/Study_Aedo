package com.aedo.my_heaven.view.main.detail.make

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityMakeBinding
import com.aedo.my_heaven.model.restapi.base.*
import com.aedo.my_heaven.util.`object`.Constant.ONE_PERMISSION_REQUEST_CODE
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.file.FileUtil
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.util.log.LLog.TAG
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread


class MakeActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMakeBinding
    private lateinit var apiServices: APIService
    private var mViewModel: MakeViewModel? = null
    private val fileUtil = FileUtil()
    private var files4: MutableList<Uri> = ArrayList()
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_make)
        mBinding.activity = this@MakeActivity
        apiServices = ApiUtils.apiService
        mBinding.viewModel = mViewModel

        val onlyDate: LocalDate = LocalDate.now()
        mBinding.tvMakeData.text = onlyDate.toString()
        mBinding.imgPake.clipToOutline = true

        inStatusBar()
        initClickListener()

        makeTop()
        setupSpinnerHandler()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.setIsMainNoticeViewed(false)
    }

    // spinner ????????? ?????? text ??????
    private fun setupSpinnerHandler() {
        mBinding.makeTxSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mBinding.spinnerText.text =
                        mBinding.makeTxSpinner.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        mBinding.makeTxSpinnerInfor.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mBinding.spinnerInfoTextTt.text =
                        mBinding.makeTxSpinnerInfor.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    // spinner setting
    private fun makeTop() {
        val person = resources.getStringArray(R.array.spinner_relationship)
        val place = resources.getStringArray(R.array.spinner_place)

        val perAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, person)
        val placeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, place)

        mBinding.makeTxSpinner.adapter = perAdapter
        mBinding.makeTxSpinnerInfor.adapter = placeAdapter

    }

    fun initClickListener() {
        mBinding.apply {
            eodImage.setOnClickListener {
                onDataClick("eod")
            }
            eodImageTime.setOnClickListener {
                onTimeClick("eod")
            }
            coffinImage.setOnClickListener {
                onDataClick("coffin")
            }
            coffinImageTime.setOnClickListener {
                onTimeClick("coffin")
            }
            dofpImage.setOnClickListener {
                onDataClick("dofp")
            }
            dofpImageTime.setOnClickListener {
                onTimeClick("dofp")
            }
        }
    }

    fun onOkClick(v: View) {
        val spinner_text = mBinding.spinnerText.text.toString()
        val make_person = mBinding.makeTxName.text.toString()
        val make_phone = mBinding.makeTxPhone.text.toString()
        val place = mBinding.spinnerInfoTextTt.text.toString()
        val deceased_name = mBinding.makeTxPersonName.text.toString()
        val deceased_age = mBinding.makeTxAge.text.toString()
        val eod_date = mBinding.eodText.text.toString()
        val eod_time = mBinding.eodTextTime.text.toString()
        val coffin_date = mBinding.coffinText.text.toString()
        val coffin_time = mBinding.coffinTextTime.text.toString()
        val dofp_date = mBinding.dofpText.text.toString()
        val dofp_time = mBinding.dofpTextTime.text.toString()
        val buried = mBinding.makeTxPlace.text.toString()
        val word = mBinding.makeTxTex.text.toString()

        when {
            spinner_text.isEmpty() -> {
                mBinding.spinnerText.error = "?????????"
            }
            make_person.isEmpty() -> {
                mBinding.makeTxName.error = "?????????"
            }
            make_phone.isEmpty() -> {
                mBinding.makeTxPhone.error = "?????????"
            }
            place.isEmpty() -> {
                mBinding.spinnerInfoTextTt.error = "?????????"
            }
            deceased_name.isEmpty() -> {
                mBinding.makeTxPersonName.error = "?????????"
            }
            deceased_age.isEmpty() -> {
                mBinding.makeTxAge.error = "?????????"
            }
            eod_date.isEmpty() -> {
                mBinding.eodText.error = "?????????"
            }
            eod_time.isEmpty() -> {
                mBinding.eodTextTime.error = "?????????"
            }
            coffin_date.isEmpty() -> {
                mBinding.coffinText.error = "?????????"
            }
            coffin_time.isEmpty() -> {
                mBinding.coffinTextTime.error = "?????????"
            }
            dofp_date.isEmpty() -> {
                mBinding.dofpText.error = "?????????"
            }
            dofp_time.isEmpty() -> {
                mBinding.dofpTextTime.error = "?????????"
            }
            buried.isEmpty() -> {
                mBinding.makeTxPlace.error = "?????????"
            }
            word.isEmpty() -> {
                mBinding.makeTxTex.error = "?????????"
            }
            else -> {
                dialog?.show()
                testAPI()
            }
        }
    }

    private fun testAPI() {
        val img: MutableList<MultipartBody.Part?> = ArrayList()
        for (uri: Uri in files4) {
            uri.path?.let { Log.i("img", it) }
            img.add(prepareFilePart("img", uri))
        }

        val resident = Resident(
            mBinding.spinnerText.text.toString(),
            mBinding.makeTxName.text.toString(), mBinding.makeTxPhone.text.toString()
        )
        val place = mBinding.spinnerInfoTextTt.text.toString()
        val deceased = Deceased(
            mBinding.makeTxPersonName.text.toString(),
            mBinding.makeTxAge.text.toString()
        )
        val eod = mBinding.eodText.text.toString()
        val coffin = mBinding.coffinText.text.toString()
        val dofp = mBinding.dofpText.text.toString()
        val buried = mBinding.makeTxPlace.text.toString()
        val word = mBinding.makeTxTex.text.toString()
        val created = mBinding.tvMakeData.text.toString()
        val requestHashMap: HashMap<String, RequestBody> = HashMap()

        requestHashMap["relation"] =
            resident.relation!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["residentName"] =
            resident.name!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["residentphone"] =
            resident.phone!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["deceasedName"] =
            deceased.name!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["deceasedAge"] =
            deceased.age!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["place"] = place.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["eod"] = eod.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["coffin"] = coffin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["dofp"] = dofp.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["buried"] = buried.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["word"] = word.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["created"] = created.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        LLog.e("????????? API")
        apiServices.getImgCreate(prefs.myaccesstoken, img, requestHashMap)
            .enqueue(object : Callback<CreateModel> {
                override fun onResponse(call: Call<CreateModel>, response: Response<CreateModel>) {
                    val result = response.body()
                    if (response.isSuccessful && result != null) {
                        Log.d(TAG, "testAPI  API SUCCESS -> $result")
                        thread(start = true) {
                            for (i in 1..4) {
                                mBinding.progress.progress = 25 * i
                                handler.post {
                                    showProgress(mBinding.progress, true)
                                }
                                Thread.sleep(500)
                            }
                            handler.post {
                                showProgress(mBinding.progress, false)
                                moveList()
                            }
                        }
                    } else {
                        Log.d(TAG, "testAPI  API ERROR -> ${response.errorBody()}")
                        testOtherAPI()
                    }
                }

                override fun onFailure(call: Call<CreateModel>, t: Throwable) {
                    Log.d(TAG, "testAPI Fail -> $t")
                    Toast.makeText(this@MakeActivity, "?????? ????????? ?????????", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // prefs.newaccesstoken ??? ?????? ?????? ?????? ??????
    private fun testOtherAPI() {
        val img: MutableList<MultipartBody.Part?> = ArrayList()
        for (uri: Uri in files4) {
            uri.path?.let { Log.i("img", it) }
            img.add(prepareFilePart("img", uri))
        }

        val resident = Resident(
            mBinding.spinnerText.text.toString(),
            mBinding.makeTxName.text.toString(), mBinding.makeTxPhone.text.toString()
        )
        val place = mBinding.spinnerInfoTextTt.text.toString()
        val deceased = Deceased(
            mBinding.makeTxPersonName.text.toString(),
            mBinding.makeTxAge.text.toString()
        )
        val eod = mBinding.eodText.text.toString()
        val coffin = mBinding.coffinText.text.toString()
        val dofp = mBinding.dofpText.text.toString()
        val buried = mBinding.makeTxPlace.text.toString()
        val word = mBinding.makeTxTex.text.toString()
        val created = mBinding.tvMakeData.text.toString()
        val requestHashMap: HashMap<String, RequestBody> = HashMap()

        requestHashMap["relation"] =
            resident.relation!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["residentName"] =
            resident.name!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["residentphone"] =
            resident.phone!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["deceasedName"] =
            deceased.name!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["deceasedAge"] =
            deceased.age!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["place"] = place.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["eod"] = eod.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["coffin"] = coffin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["dofp"] = dofp.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["buried"] = buried.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["word"] = word.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["created"] = created.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        LLog.e("?????????_????????? API")
        apiServices.getImgCreate(prefs.newaccesstoken, img, requestHashMap)
            .enqueue(object : Callback<CreateModel> {
                override fun onResponse(call: Call<CreateModel>, response: Response<CreateModel>) {
                    val result = response.body()
                    if (response.isSuccessful && result != null) {
                        Log.d(TAG, "testAPI Second API SUCCESS -> $result")
                        thread(start = true) {
                            for (i in 1..4) {
                                mBinding.progress.progress = 25 * i
                                handler.post {
                                    showProgress(mBinding.progress, true)
                                }
                                Thread.sleep(500)
                            }
                            handler.post {
                                showProgress(mBinding.progress, false)
                                moveList()
                            }
                        }
                    } else {
                        Log.d(TAG, "testAPI Second API ERROR -> ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<CreateModel>, t: Throwable) {
                    Log.d(TAG, "testAPI Second Fail -> $t")
                    Toast.makeText(this@MakeActivity, "?????? ????????? ?????????", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path!!)
        Log.i("here is error", file.absolutePath)
        val requestFile: RequestBody = file
            .asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    @SuppressLint("SetTextI18n")
    fun onDataClick(text: String) {
        var dateString = ""
        val cal = Calendar.getInstance() //????????? ???
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            dateString = "${year}??? ${month + 1}??? ${dayOfMonth}???"
            when (text) {
                "eod" -> mBinding.eodText.text = dateString
                "coffin" -> mBinding.coffinText.text = dateString
                "dofp" -> mBinding.dofpText.text = dateString
            }
        }
        DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun onTimeClick(textView: String) {
        var timeString = ""
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            var hourString = "${hour}"
            var minuteString = "${minute}"
            if (hour < 10) hourString = "0${hour}"
            if (minute < 10) minuteString = "0${minute}"
            timeString = hourString + ":" + minuteString
            when (textView) {
                "eod" -> mBinding.eodTextTime.text = timeString
                "coffin" -> mBinding.coffinTextTime.text = timeString
                "dofp" -> mBinding.dofpTextTime.text = timeString
            }
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    fun onPickClick(v: View) {
        requestPermission()
    }

    private fun requestPermission() {
        when {
            // ?????? ?????? ????????? ?????? ??????
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            -> getAlbum()

            // ?????? ????????? ??????????????? ????????? ??????
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
            -> Toast.makeText(this, "?????? ?????? ?????? ????????? ????????????", Toast.LENGTH_SHORT).show()

            // ?????? ????????? ?????? ?????????, ?????? ?????? ????????? ????????? ?????? ??????????????? ??????????????? ?????? ??????
            else -> requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), ONE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startForResult.launch(intent)
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val img = result.data?.data
                    mBinding.imgPake.setImageURI(img)
                    val imgPath = img.let {
                        fileUtil.getPath(this, it!!)
                    }

                    files4.add(Uri.parse(imgPath))
                    if (imgPath != null) {
                        Log.e("image", imgPath)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    override fun onBackPressed() {
        moveMain()
    }
}