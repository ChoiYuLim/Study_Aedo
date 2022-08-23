package com.aedo.my_heaven.view.main.detail.search

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivitySearchDetailBinding
import com.aedo.my_heaven.model.restapi.base.Coordinates
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.view.main.MainActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class SearchDetailActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivitySearchDetailBinding
    private lateinit var apiServices: APIService
    private var locationSource: FusedLocationSource? = null
    private var mMap: NaverMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_detail)
        mBinding.activity = this
        apiServices = ApiUtils.apiService
        inStatusBar()
        initDetail()
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
            LocationManager.NETWORK_PROVIDER
        ))
    }

    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하시겠습니까?")
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, Constant.GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }
        builder.setCancelable(false)
        builder.create().show()
    }

    private fun checkRunTimePermission() {
        // 위치 퍼미션 체크
        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Constant.PERMISSIONS[0]
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    Constant.PERMISSIONS,
                    Constant.PERMISSION_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    Constant.PERMISSIONS,
                    Constant.PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.GPS_ENABLE_REQUEST_CODE ->
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d(Constant.TAG, "onActivityResult : GPS 활성화 되있음")
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

    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grandResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == Constant.PERMISSION_REQUEST_CODE && grandResults.size == Constant.PERMISSIONS.size) {
            var check_result = true
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Constant.PERMISSIONS[0]
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Constant.PERMISSIONS[1]
                    )
                ) {
                    Toast.makeText(
                        this,
                        "권한이 거부되었습니다. 앱을 다시 실행하여 위치 권한을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "권한이 거부되었습니다. 설정(앱 정보)에서 위치 권한을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun initDetail() {
        val relation_name = intent.getStringExtra(Constant.SEARCH_RELATION_NAME)
        val place = intent.getStringExtra(Constant.SEARCH_PLACE)
        val eod_data = intent.getStringExtra(Constant.SEARCH_EOD_DATA)
        val deceased_name = intent.getStringExtra(Constant.SEARCH_DECEASED_NAME)
        val coffin_data = intent.getStringExtra(Constant.SEARCH_COFFIN_DATA)
        val dofp_data = intent.getStringExtra(Constant.SEARCH_DOFP_DATA)
        val buried = intent.getStringExtra(Constant.SEARCH_BURIED)

        mBinding.txPersonName.text = deceased_name.toString()
        mBinding.txCenterName.text = relation_name.toString()
        mBinding.txInfo.text = place.toString()
        mBinding.txDetailCoffin.text = coffin_data.toString()
        mBinding.txData.text = "$eod_data 별세"
        mBinding.txDetailDofp.text = dofp_data.toString()
        mBinding.txBuried.text = buried.toString()

        initmap(place)

    }

    private fun initmap(place: String?) {
        val place_first =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_FIRST").findFirst()
        val place_second =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Second").findFirst()
        val place_third =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Third").findFirst()
        val place_four =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Four").findFirst()

        when {
            place.equals(place_first?.name) -> {
                mBinding.tvMapDetail.text = place_first?.address.toString()
                val startPosition = CameraPosition(
                    LatLng(
                        place_first?.xvalue!!.toDouble(),
                        place_first.yvalue!!.toDouble()
                    ), 17.2, 0.0, 0.0
                )
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.search_naver_map) as MapFragment?
                        ?: MapFragment.newInstance(options).also {
                            supportFragmentManager.beginTransaction().add(R.id.search_naver_map, it)
                                .commit()
                        }
                mapFragment.getMapAsync(this)

            }

            place.equals(place_second?.name) -> {
                mBinding.tvMapDetail.text = place_second?.address.toString()
                val startPosition = CameraPosition(
                    LatLng(
                        place_second?.xvalue!!.toDouble(),
                        place_second.yvalue!!.toDouble()
                    ), 17.2, 0.0, 0.0
                )
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.search_naver_map) as MapFragment?
                        ?: MapFragment.newInstance(options).also {
                            supportFragmentManager.beginTransaction().add(R.id.search_naver_map, it)
                                .commit()
                        }
                mapFragment.getMapAsync(this)
            }

            place.equals(place_third?.name) -> {
                mBinding.tvMapDetail.text = place_third?.address.toString()
                val startPosition = CameraPosition(
                    LatLng(
                        place_third?.xvalue!!.toDouble(),
                        place_third.yvalue!!.toDouble()
                    ), 17.2, 0.0, 0.0
                )
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.search_naver_map) as MapFragment?
                        ?: MapFragment.newInstance(options).also {
                            supportFragmentManager.beginTransaction().add(R.id.search_naver_map, it)
                                .commit()
                        }
                mapFragment.getMapAsync(this)

            }

            place.equals(place_four?.name) -> {
                mBinding.tvMapDetail.text = place_four?.address.toString()
                val startPosition = CameraPosition(
                    LatLng(
                        place_four?.xvalue!!.toDouble(),
                        place_four.yvalue!!.toDouble()
                    ), 17.2, 0.0, 0.0
                )
                val options = NaverMapOptions()
                    .camera(startPosition)
                    .mapType(NaverMap.MapType.Basic)
                    .enabledLayerGroups(NaverMap.LAYER_GROUP_BICYCLE)
                    .compassEnabled(true)
                    .scaleBarEnabled(true)

                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.search_naver_map) as MapFragment?
                        ?: MapFragment.newInstance(options).also {
                            supportFragmentManager.beginTransaction().add(R.id.search_naver_map, it)
                                .commit()
                        }
                mapFragment.getMapAsync(this)
            }
            else -> {
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        val place_first =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_FIRST").findFirst()
        val place_second =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Second").findFirst()
        val place_third =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Third").findFirst()
        val place_four =
            realm.where(Coordinates::class.java).equalTo("id", "PLACE_Four").findFirst()

        Marker().apply {
            position = LatLng(place_first?.xvalue!!.toDouble(), place_first.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_second?.xvalue!!.toDouble(), place_second.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_third?.xvalue!!.toDouble(), place_third.yvalue!!.toDouble())
            map = naverMap
        }

        Marker().apply {
            position = LatLng(place_four?.xvalue!!.toDouble(), place_four.yvalue!!.toDouble())
            map = naverMap
        }
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onWaringClick(v: View) {

    }

}