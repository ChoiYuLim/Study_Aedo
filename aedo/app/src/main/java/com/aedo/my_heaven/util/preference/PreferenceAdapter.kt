package com.aedo.my_heaven.util.preference

import android.content.SharedPreferences

abstract class PreferenceAdapter {
    private var share: SharedPreferences? = null
    private val mSharedPreferences: SharedPreferences? = null


    fun setPreference(shared_pref: SharedPreferences?) {
        share = shared_pref
    }

    protected open fun getString(key: String?, def_value: String?): String? {
        var value: String? = ""
        try {
            value = share!!.getString(key, def_value)
        } catch (e: ClassCastException) {
        }
        return value
    }

    protected fun getBoolean(key: String?, def_value: Boolean): Boolean {
        var value = false
        try {
            value = share!!.getBoolean(key, def_value)
        } catch (e: ClassCastException) {
        }
        return value
    }

    protected fun setString(key: String?, value: String?): Boolean {
        val edit = share!!.edit()
        edit.putString(key, value)
        return edit.commit()
    }

    protected fun setBoolean(key: String?, value: Boolean): Boolean {
        val edit = share!!.edit()
        edit.putBoolean(key, value)
        return edit.commit()
    }

    protected open fun setInt(key: String?, value: Int): Boolean {
        val edit: SharedPreferences.Editor = mSharedPreferences!!.edit()
        edit.putInt(key, value)
        return edit.commit()
    }

    companion object {
        private var instance: PreferenceManager? = null
    }
}