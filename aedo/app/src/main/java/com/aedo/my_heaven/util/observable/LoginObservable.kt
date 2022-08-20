package com.aedo.my_heaven.util.observable

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.aedo.my_heaven.util.encrypt.AESAdapter
import com.aedo.my_heaven.util.preference.PreferenceManager

class LoginObservable(pref: PreferenceManager) : BaseObservable() {
    private val pref: PreferenceManager = pref
    private var phoneNum: String? = null
    internal var authNum: String? = null
    private var yearArray: List<String>? = null
    private var monthArray: List<String>? = null
    private var dayArray: List<String>? = null
    private var birthday: String? = null

    @Bindable
    fun getAuthNum(): String? {
        return authNum
    }

    fun setAuthNum(authNum: String?) {
        this.authNum = authNum
        notifyChange()
    }

    @get:Bindable
    var loginProcess = 0
        set(loginProcess) {
            field = loginProcess
            notifyChange()
        }

    var selectedGender: String? = null
    var isTermsAgree = false
    var isJoin = false

    @Bindable
    fun getPhoneNum(): String? {
        return phoneNum
    }

    fun setPhoneNum(phoneNum: String?) {
        this.phoneNum = phoneNum
        notifyChange()
    }

    val encPhoneNum: String?
        get() = AESAdapter.encAES(pref.myeniv.toString(), pref.myenkey.toString(), phoneNum)


    fun getYearArray(): List<String?>? {
        return yearArray
    }

    fun getMonthArray(): List<String?>? {
        return monthArray
    }

    fun getDayArray(): List<String?>? {
        return dayArray
    }

    fun getBirthday(): String? {
        return birthday
    }

    val encBirthday: String
        get() = AESAdapter.encAES(pref.getEncIv()!!, pref.getEncKey()!!, birthday)!!

    @JvmName("setYearArray1")
    fun setYearArray(yearArray: List<String?>?) {
        this.yearArray = yearArray as List<String>?
    }

    @JvmName("setMonthArray1")
    fun setMonthArray(monthArray: List<String?>?) {
        this.monthArray = monthArray as List<String>?
    }

    @JvmName("setDayArray1")
    fun setDayArray(dayArray: List<String?>?) {
        this.dayArray = dayArray as List<String>?
    }

    @JvmName("setBirthday1")
    fun setBirthday(birthday: String?) {
        this.birthday = birthday
    }

    @JvmName("getLoginProcess1")
    @Bindable
    fun getLoginProcess(): Int {
        return loginProcess
    }

    @JvmName("setLoginProcess1")
    fun setLoginProcess(loginProcess: Int) {
        this.loginProcess = loginProcess
        notifyChange()
    }

    @JvmName("setSelectedGender1")
    fun setSelectedGender(selectedGender: String?) {
        this.selectedGender = selectedGender
    }


}