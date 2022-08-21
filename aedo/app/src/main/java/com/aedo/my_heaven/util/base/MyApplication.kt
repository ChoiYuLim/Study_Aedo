package com.aedo.my_heaven.util.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.aedo.my_heaven.R
import com.aedo.my_heaven.util.common.CommonData
import com.aedo.my_heaven.util.preference.PreferenceManager
import com.iamport.sdk.domain.core.Iamport
import com.kakao.sdk.common.KakaoSdk
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceManager
        private var isMainNoticeViewed = false

        @Synchronized
        fun setIsMainNoticeViewed(viewed: Boolean) {
            isMainNoticeViewed = viewed
        }

        @Synchronized
        fun isIsMainNoticeViewed(): Boolean {
            return isMainNoticeViewed
        }
    }

    override fun onCreate() {
        super.onCreate()
        Iamport.create(this)
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("test-data")
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        prefs = PreferenceManager(applicationContext)
        val commonData: CommonData = CommonData().getInstance()
        commonData.numStarted = 0

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                if (commonData.numStarted == 0) {
                    commonData.isMainRefresh = true
                    commonData.isForeground = true
                }
                commonData.numStarted++
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {
                commonData.numStarted--
                if (commonData.numStarted == 0) {
                    commonData.isForeground = false
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
        KakaoSdk.init(this, getString(R.string.kakao_key))
    }
}