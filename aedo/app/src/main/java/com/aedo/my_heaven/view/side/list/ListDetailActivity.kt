package com.aedo.my_heaven.view.side.list

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityListdetailBinding
import com.aedo.my_heaven.model.naverMap.ResultPath
import com.aedo.my_heaven.model.restapi.base.Coordinates
import com.aedo.my_heaven.util.`object`.Constant.BURIED
import com.aedo.my_heaven.util.`object`.Constant.COFFIN_DATE
import com.aedo.my_heaven.util.`object`.Constant.DECEASED_NAME
import com.aedo.my_heaven.util.`object`.Constant.DOFP_DATE
import com.aedo.my_heaven.util.`object`.Constant.EOD_DATE
import com.aedo.my_heaven.util.`object`.Constant.GPS_ENABLE_REQUEST_CODE
import com.aedo.my_heaven.util.`object`.Constant.LIST_IMG
import com.aedo.my_heaven.util.`object`.Constant.LLIST_ID
import com.aedo.my_heaven.util.`object`.Constant.MESSAGE_LLIST_ID
import com.aedo.my_heaven.util.`object`.Constant.PERMISSIONS
import com.aedo.my_heaven.util.`object`.Constant.PERMISSION_REQUEST_CODE
import com.aedo.my_heaven.util.`object`.Constant.PLACE_NAME
import com.aedo.my_heaven.util.`object`.Constant.RESIDENT_NAME
import com.aedo.my_heaven.util.`object`.Constant.TAG
import com.aedo.my_heaven.util.alert.LoadingDialog
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.side.list.detail.MessageActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.view_item_img.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.lang.Exception


class ListDetailActivity : BaseActivity(),OnMapReadyCallback {
    private lateinit var mBinding: ActivityListdetailBinding
    private lateinit var apiServices: APIService

    private var locationSource: FusedLocationSource? = null
    private var mMap: NaverMap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_listdetail)
        mBinding.activity=this@ListDetailActivity
        apiServices = ApiUtils.apiService
        mBinding.lifecycleOwner = this
        dialog = LoadingDialog(this)
        inStatusBar()
        inRecycler()
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            checkRunTimePermission()
        }
    }

    override fun onPause() {
        super.onPause()
        removeGps()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.setIsMainNoticeViewed(false)
    }

    private fun removeGps() {
        setMapTrackingMode(LocationTrackingMode.None)
        locationSource = null
        mMap?.locationSource = null
        mMap?.onMapClickListener = null
    }

    private fun setMapTrackingMode(trackingMode: LocationTrackingMode?) {
        mMap?.locationTrackingMode = trackingMode!!
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER))
    }

    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하시겠습니까?")
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }
        builder.setCancelable(false)
        builder.create().show()
    }

    private fun checkRunTimePermission() {
        // 위치 퍼미션 체크
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    PERMISSIONS[0]
                )) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
            }
            else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                //result.getContents 를 이용 데이터 재가공
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grandResults: IntArray) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == PERMISSION_REQUEST_CODE && grandResults.size == PERMISSIONS.size) {
            var check_result = true
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
            }
            else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSIONS[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSIONS[1])) {
                    Toast.makeText(this, "권한이 거부되었습니다. 앱을 다시 실행하여 위치 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Toast.makeText(this, "권한이 거부되었습니다. 설정(앱 정보)에서 위치 권한을 허용해야 합니다. ", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }


    private fun initView() {
        val place_name = intent.getStringExtra(PLACE_NAME)
        val place_first = realm.where(Coordinates::class.java).equalTo("id","PLACE_FIRST").findFirst()
        val place_second  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Second").findFirst()
        val place_third  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Third").findFirst()
        val place_four  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Four").findFirst()

        when {

            place_name.equals(place_first?.name) -> {
                mBinding.tvMapDetail.text = place_first?.address.toString()
                val startPosition = CameraPosition(LatLng(place_first?.xvalue!!.toDouble(),place_first.yvalue!!.toDouble()), 17.2, 0.0, 0.0)
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment = supportFragmentManager.findFragmentById(R.id.naver_map) as MapFragment?
                    ?: MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.naver_map, it).commit()
                    }
                mapFragment.getMapAsync(this)

            }

            place_name.equals(place_second?.name) -> {
                mBinding.tvMapDetail.text = place_second?.address.toString()
                val startPosition = CameraPosition(LatLng(place_second?.xvalue!!.toDouble(),place_second.yvalue!!.toDouble()), 17.2, 0.0, 0.0)
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment = supportFragmentManager.findFragmentById(R.id.naver_map) as MapFragment?
                    ?: MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.naver_map, it).commit()
                    }
                mapFragment.getMapAsync(this)
            }

            place_name.equals(place_third?.name) -> {
                mBinding.tvMapDetail.text = place_third?.address.toString()
                val startPosition = CameraPosition(LatLng(place_third?.xvalue!!.toDouble(),place_third.yvalue!!.toDouble()), 17.2, 0.0, 0.0)
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment = supportFragmentManager.findFragmentById(R.id.naver_map) as MapFragment?
                    ?: MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.naver_map, it).commit()
                    }
                mapFragment.getMapAsync(this)

            }

            place_name.equals(place_four?.name) -> {
                mBinding.tvMapDetail.text = place_four?.address.toString()
                val startPosition = CameraPosition(LatLng(place_four?.xvalue!!.toDouble(),place_four.yvalue!!.toDouble()), 17.2, 0.0, 0.0)
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment = supportFragmentManager.findFragmentById(R.id.naver_map) as MapFragment?
                    ?: MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.naver_map, it).commit()
                    }
                mapFragment.getMapAsync(this)
            }
            else -> {
                serverDialog()
                return
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun inRecycler() {
        val decase_name = intent.getStringExtra(DECEASED_NAME)
        val img = intent.getStringExtra(LIST_IMG)
        val place_name = intent.getStringExtra(PLACE_NAME)
        val tx_data = intent.getStringExtra(EOD_DATE)
        val tv_center_name = intent.getStringExtra(RESIDENT_NAME)
        val coffin = intent.getStringExtra(COFFIN_DATE)
        val dofp = intent.getStringExtra(DOFP_DATE)
        val buried = intent.getStringExtra(BURIED)

        mBinding.txPersonName.text = decase_name.toString()
        mBinding.textInfo.text = place_name.toString()
        mBinding.txData.text = "$tx_data 별세"
        mBinding.txCenterName.text = tv_center_name.toString()
        mBinding.txInfo.text = place_name.toString()
        mBinding.txDetailCoffin.text = coffin.toString()
        mBinding.txDetailDofp.text = dofp.toString()
        mBinding.txBuried.text = buried.toString()

        Thread {
            getImg(img)
        }.start()
    }

    private fun getImg(img: String?) {
        LLog.e("이미지 API")
        val vercall: Call<ResponseBody> = apiServices.getImg(img,prefs.myaccesstoken)
        vercall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"ListImg  response SUCCESS -> $result")

                    Thread {
                        try {
                            val imgs = result.byteStream()
                            val bit = BitmapFactory.decodeStream(imgs)
                            mBinding.imgPerson.setImageBitmap(bit)
                            mBinding.imgPerson.setOnClickListener {
                                val myLayout = layoutInflater.inflate(R.layout.view_item_img, null)
                                val build = AlertDialog.Builder(this@ListDetailActivity).apply {
                                    setView(myLayout)
                                }
                                val textView : ImageView = myLayout.findViewById(R.id.data_detail_img)
                                textView.setImageBitmap(bit)
                                val dialog = build.create()
                                dialog.show()
                                myLayout.img_finish.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                        }
                        catch (e: Exception) {
                        }
                    }.start()

                }
                else {
                    Log.d(LLog.TAG,"ListImg  esponse ERROR -> $result")
                    otherAPI(img)
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(LLog.TAG, "ListImg  Fail -> ${t.localizedMessage}")
            }
        })
    }

    private fun otherAPI(img: String?) {
        LLog.e("이미지_두번째 API")
        val vercall: Call<ResponseBody> = apiServices.getImg(img,prefs.newaccesstoken)
        vercall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Log.d(LLog.TAG,"ListImg Second response SUCCESS -> $result")
                    Thread {
                        try {
                            val imgs = result.byteStream()
                            val bit = BitmapFactory.decodeStream(imgs)
                            mBinding.imgPerson.setImageBitmap(bit)
                        }
                        catch (e: Exception) {

                        }
                    }.start()
                }
                else {
                    Log.d(LLog.TAG,"ListImg Second esponse ERROR -> $result")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(LLog.TAG, "ListImg Second Fail -> $t")
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        val place_first = realm.where(Coordinates::class.java).equalTo("id","PLACE_FIRST").findFirst()
        val place_second  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Second").findFirst()
        val place_third  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Third").findFirst()
        val place_four  = realm.where(Coordinates::class.java).equalTo("id","PLACE_Four").findFirst()

        Marker().apply {
            position = LatLng(place_first?.xvalue!!.toDouble(),place_first.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_second?.xvalue!!.toDouble(),place_second.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_third?.xvalue!!.toDouble(),place_third.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_four?.xvalue!!.toDouble(),place_four.yvalue!!.toDouble())
            map = naverMap
        }
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onDataSendClick(v: View) {
        putID()
    }

    fun onMainClick(v: View) {
        moveMain()
    }

    fun onBusClick(v: View) {

    }

    fun onParkingClick(v: View) {

    }

    fun onNaviClick(v: View) {
        moveNavi()
    }

    private fun moveNavi() {
        val APIKEY_ID = getString(R.string.naver_key)
        val APIKEY = getString(R.string.naver_pass_key)
        val retrofit = Retrofit.Builder().
        baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
        addConverterFactory(GsonConverterFactory.create()).
        build()

        val api = retrofit.create(APIService::class.java)
        //근처에서 길찾기
        val callgetPath = api.getPath(APIKEY_ID, APIKEY,"129.089441, 35.231100", "129.084454, 35.228982")
        callgetPath.enqueue(object : Callback<ResultPath>{
            override fun onResponse(call: Call<ResultPath>, response: Response<ResultPath>) {
                val result = response.body()
                if(response.isSuccessful && result != null) {
                    Log.d(TAG,"네이버 길찾기 성공 -> $result")
                    val path_cords = response.body()?.route?.traoptimal
                    val path = PathOverlay()
                    val path_container : MutableList<LatLng> = mutableListOf(LatLng(0.1,0.1))
                    for (path_cordsss in path_cords!!) {
                        for (path_cords_xy in path_cordsss.path) {
                            path_container?.add(LatLng(path_cords_xy[1],path_cords_xy[0]))
                        }
                    }
                    path.coords = path_container?.drop(1)!!
                    path.color = Color.RED
                    path.map = mMap

                }
                else {
                    Log.d(TAG,"네이버 길찾기 실패 -> ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                Log.d(TAG,"네이버 길찾기 서버 연결 실패 -> $t")
            }
        })
    }

    private fun putID() {
        val id = intent.getStringExtra(LLIST_ID)
        val listid = id
        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra(MESSAGE_LLIST_ID,listid.toString())
        startActivity(intent)
    }

}

