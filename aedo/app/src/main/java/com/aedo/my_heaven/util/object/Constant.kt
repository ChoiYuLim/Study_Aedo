package com.aedo.my_heaven.util.`object`

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.aedo.my_heaven.api.APIService
import com.naver.maps.geometry.LatLng
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

object  Constant {
    const val TAG = "My_Heaven"
    const val BASE_URL="https://www.aedo.co.kr/"
    const val BASE_URL_TEST="http://118.67.128.124:8080/"
    const val PREF_KEY_APP_TOKEN = "myAppToken"
    const val PREF_KEY_ENCTYPT_IV = "myEncryptIv"
    const val PREF_KEY_ENCTYPT_KEY = "myEncryptKey"
    const val PREF_KEY_AUTH_TOKEN ="myAuthToken"
    const val PREF_KEY_LANG = "myLang"
    const val PREF_KEY_LANG_CODE = "myLangCode"
    const val PREF_KEY_TOKEN = "myTOKEN"
    const val RESULT_TRUE = "true"
    const val PREF_PERMISSION_GRANTED = "PREF_PERMISSION_GRANTED"
    const val PREF_EMERGENCY_NOTICE_NOT_SHOW = "PREF_EMERGENCY_NOTICE_NOT_SHOW"
    const val PREF_KEY_LANGUAGE = "PREF_KEY_LANGUAGE"
    const val PREF_KEY_LANGUAGE_CODE = "PREF_KEY_LANGUAGE_CODE"
    const val PREF_SMS = "PREF_SMS"
    const val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"
    const val PREF_NEW_ACCESS_TOKEN = "PREF_NEW_ACCESS_TOKEN"
    const val PREF_HASH_KEY = "PREF_HASH_KEY"
    const val PREF_LIST_ID = "PREF_LIST_ID"
    const val PREF_IMG_URI = "PREF_IMG_URI"
    const val PREF_TEST_URI = "PREF_TEST_URI"

    const val PREF_KEY_ENC_KEY = "PREF_KEY_ENC_KEY"
    const val PREF_KEY_ENC_IV = "PREF_KEY_ENC_IV"

    const val ONE_PERMISSION_REQUEST_CODE = 1
    const val ALL_PERMISSION_REQUEST_CODE = 2

    const val BACKPRESS_CLOSE_TIME = 1500

    const val ALBUM_REQUEST_CODE = 302

    const val DEFATULT_TIMEOUT = 20000

    const val RESIDENT_NAME = "RESIDENT_NAME"

    const val PLACE_NAME = "PLACE_NAME"

    const val DECEASED_NAME = "DECEASED_NAME"
    const val DECEASED_AGE = "DECEASED_AGE"

    const val LLIST_ID = "LLIST_ID"
    const val MESSAGE_LLIST_ID = "MESSAGE_LLIST_ID"
    const val MESSAGE_DETAIL_LLIST_ID = "MESSAGE_DETAIL_LLIST_ID"
    const val LIST_IMG = "LIST_IMG"
    const val LIST_FRAGMENT_IMG = "LIST_FRAGMENT_IMG"

    const val EOD_NAME = "EOD_NAME"
    const val EOD_DATE = "EOD_DATE"

    const val COFFIN_DATE = "COFFIN_DATE"
    const val COFFIN_TIME = "COFFIN_TIME"

    const val DOFP_DATE = "DOFP_DATE"
    const val DOFP_TIME = "DOFP_TIME"

    const val BURIED = "BURIED"

    //Notice
    const val NOTICE_TITLE = "NOTICE_TITLE"
    const val NOTICE_CONTENT = "NOTICE_CONTENT"
    const val NOTICE_CREATED = "NOTICE_CREATED"

    //Coun
    const val COUN_NAME = "COUN_NAME"
    const val COUN_TITLE = "COUN_TITLE"
    const val COUN_CONTENT = "COUN_CONTENT"
    const val COUN_CREATED = "COUN_CREATED"

    const val PERMISSION_REQUEST_CODE = 1000
    const val GPS_ENABLE_REQUEST_CODE = 2001

    const val APP_UPDATE = 700


    //Search
    const val SEARCH_RELATION_NAME = "SEARCH_RELATION_NAME"
    const val SEARCH_EOD_DATA = "SEARCH_EOD_DATA"
    const val SEARCH_DECEASED_NAME = "SEARCH_DECEASED_NAME"
    const val SEARCH_COFFIN_DATA = "SEARCH_COFFIN_DATA"
    const val SEARCH_DOFP_DATA = "SEARCH_DOFP_DATA"
    const val SEARCH_BURIED = "SEARCH_BURIED"
    const val SEARCH_PLACE = "SEARCH_PLACE"

    //Shop
    const val SHOP_FIRST = "SHOP_FIRST"
    const val SHOP_SECOND = "SHOP_SECOND"
    const val SHOP_THIRD = "SHOP_THIRD"
    const val SHOP_FOUR = "SHOP_FOUR"

    const val SHOP_FIVE = "SHOP_FIVE"
    const val SHOP_SIX = "SHOP_SIX"
    const val SHOP_SEVEN = "SHOP_SEVEN"

    const val SHOP_EIGHT = "SHOP_EIGHT"
    const val SHOP_NINE = "SHOPT_NIE"

    const val SHOP_FIRST_PAY = "SHOP_FIRST_PAY"
    const val SHOP_SECOND_PAY = "SHOP_SECOND_PAY"
    const val SHOP_THIRD_PAY = "SHOP_THIRD_PAY"
    const val SHOP_FOUR_PAY = "SHOP_FOUR_PAY"

    const val SHOP_FIVE_PAY= "SHOP_FIVE_PAY"
    const val SHOP_SIX_PAY = "SHOP_SIX_PAY"
    const val SHOP_SEVEN_PAY = "SHOP_SEVEN_PAY"

    const val SHOP_EIGHT_PAY = "SHOP_EIGHT_PAY"
    const val SHOP_NINE_PAY = "SHOPT_NIE_PAY"

    //My Order
    const val MY_ORDER_PLACE_NAME = "MY_ORDER_PLACE_NAME"
    const val MY_ORDER_PLACE_NUMBER = "MY_ORDER_PLACE_NUMBER"
    const val MY_ORDER_ITEM = "MY_ORDER_ITEM"
    const val MY_ORDER_PRICE = "MY_ORDER_PRICE"
    const val MY_ORDER_RECEIVER_NAME = "MY_ORDER_RECEIVER_NAME"
    const val MY_ORDER_RECEIVER_PHONE = "MY_ORDER_RECEIVER_NAME"
    const val MY_ORDER_SENDER_NAME = "MY_ORDER_SENDER_NAME"
    const val MY_ORDER_SENDER_PHONE = "MY_ORDER_SENDER_PHONE"
    const val MY_ORDER_WORD_COMPANY = "MY_ORDER_WORD_COMPANY"
    const val MY_ORDER_WORD_WORD = "MY_ORDER_WORD_WORD"
    const val MY_ORDER_WORD_WORDSECOND = "MY_ORDER_WORD_WORDSECOND"
    const val MY_ORDER_CREATED = "MY_ORDER_CREATED"



    // Splash
    const val SPLASH_WAIT = 2500
    val MUTILE_PERMISSION: ArrayList<String?> = object : ArrayList<String?>() {
        init {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
        , Manifest.permission.ACCESS_COARSE_LOCATION
    )

}