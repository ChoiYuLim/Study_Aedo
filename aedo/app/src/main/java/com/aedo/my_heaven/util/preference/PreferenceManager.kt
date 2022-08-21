package com.aedo.my_heaven.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.`object`.Constant.PREF_ACCESS_TOKEN
import com.aedo.my_heaven.util.`object`.Constant.PREF_HASH_KEY
import com.aedo.my_heaven.util.`object`.Constant.PREF_IMG_URI
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_APP_TOKEN
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_AUTH_TOKEN
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_ENCTYPT_IV
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_ENCTYPT_KEY
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_LANG
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_LANG_CODE
import com.aedo.my_heaven.util.`object`.Constant.PREF_KEY_TOKEN
import com.aedo.my_heaven.util.`object`.Constant.PREF_LIST_ID
import com.aedo.my_heaven.util.`object`.Constant.PREF_NEW_ACCESS_TOKEN
import com.aedo.my_heaven.util.`object`.Constant.PREF_SMS
import com.aedo.my_heaven.util.`object`.Constant.PREF_TEST_URI
import com.securepreferences.SecurePreferences

class PreferenceManager(context: Context) : PreferenceAdapter() {
    private val PREFS_FILENAME: String = "prefs"
    private val prefs = context.getSharedPreferences(PREFS_FILENAME, 0)    // 0 = MODE_PRIVATE
    private var share: SharedPreferences
    private val shareEditor: SharedPreferences.Editor
    private var securePreferences: SecurePreferences? = null
    private var securePrefEditor: SecurePreferences.Editor? = null

    private fun PreferenceManager(context: Context) {
        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        securePreferences = SecurePreferences(context)
        securePrefEditor = securePreferences!!.edit()
        super.setPreference(sharedPref)
    }

    private fun getString(key: String): String? {
        return getString(key, "").apply { }
    }

    internal fun getStr(key: String?, s: String): String? {
        return getString(key.toString())
    }

    internal fun setStr(key: String?, value: String?): Boolean {
        return setString(key, value)
    }

    fun setSecureString(key: String?, value: String?): Boolean {
        shareEditor.putString(key, value)
        return shareEditor.commit()
    }

    fun getSecureString(key: String?): String? {
        return share.getString(key, "")
    }

    fun getBool(key: String?): Boolean {
        return getBoolean(key, false)
    }

    fun setBool(key: String?, value: Boolean): Boolean {
        return setBoolean(key, value)
    }

    fun getLangCd(): String? {
        return getStr(Constant.PREF_KEY_LANGUAGE, "")
    }

    fun setLangCd(value: String?): Boolean {
        return setStr(PREF_KEY_LANG, value)
    }

    fun getLangCdCode(): String? {
        return getStr(Constant.PREF_KEY_LANGUAGE_CODE, "")
    }

    fun setLangCdCode(value: String?): Boolean {
        return setStr(PREF_KEY_LANG_CODE, value)
    }

    fun getEncKey(): String? {
        return securePreferences!!.getString(Constant.PREF_KEY_ENC_KEY, "")
    }

    fun getEncIv(): String? {
        return securePreferences!!.getString(Constant.PREF_KEY_ENC_IV, "")
    }

    var myhashKey: String?
        get() = prefs.getString(PREF_HASH_KEY, "")
        set(value) = prefs.edit().putString(PREF_HASH_KEY, value).apply()

    var myapptoken: String?
        get() = prefs.getString(PREF_KEY_APP_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_APP_TOKEN, value).apply()

    var myeniv: String?
        get() = prefs.getString(PREF_KEY_ENCTYPT_IV, "")
        set(value) = prefs.edit().putString(PREF_KEY_ENCTYPT_IV, value).apply()

    var myenkey: String?
        get() = prefs.getString(PREF_KEY_ENCTYPT_KEY, "")
        set(value) = prefs.edit().putString(PREF_KEY_ENCTYPT_KEY, value).apply()

    var myauthtoken: String?
        get() = prefs.getString(PREF_KEY_AUTH_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_AUTH_TOKEN, value).apply()

    var mylangcode: String?
        get() = prefs.getString(PREF_KEY_LANG_CODE, "")
        set(value) = prefs.edit().putString(PREF_KEY_LANG_CODE, value).apply()

    var mytoken: String?
        get() = prefs.getString(PREF_KEY_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_KEY_TOKEN, value).apply()

    var mysms: String?
        get() = prefs.getString(PREF_SMS, "")
        set(value) = prefs.edit().putString(PREF_SMS, value).apply()

    var myaccesstoken: String?
        get() = prefs.getString(PREF_ACCESS_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_ACCESS_TOKEN, value).apply()

    var newaccesstoken: String?
        get() = prefs.getString(PREF_NEW_ACCESS_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_NEW_ACCESS_TOKEN, value).apply()

    var myListId: String?
        get() = prefs.getString(PREF_LIST_ID, "")
        set(value) = prefs.edit().putString(PREF_LIST_ID, value).apply()

    var imgurls: String?
        get() = prefs.getString(PREF_IMG_URI, "")
        set(value) = prefs.edit().putString(PREF_IMG_URI, value).apply()

    var testurl: String?
        get() = prefs.getString(PREF_TEST_URI, "")
        set(value) = prefs.edit().putString(PREF_TEST_URI, value).apply()

    companion object {
        private var instance: PreferenceManager? = null

        @JvmStatic
        fun getInstance(context: Context): PreferenceManager? {
            if (null == instance) {
                synchronized(PreferenceManager::class.java) {
                    if (null == instance) {
                        instance = PreferenceManager(context)
                    }
                }
            }
            return instance
        }
    }

    init {
        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        share = SecurePreferences(context)
        shareEditor = share.edit()
        super.setPreference(sharedPref)
    }
}