package com.aedo.my_heaven.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aedo.my_heaven.util.base.BaseViewModel
import com.aedo.my_heaven.util.network.SingleLiveEvent
import javax.inject.Inject

class AgreeViewModel @Inject constructor() : BaseViewModel() {
    private val _serviceAgree = MutableLiveData(false)
    private val _personalInfoAgree = MutableLiveData(false)
    private val _allAgree = MutableLiveData(false)
    private val _anotherAgree = MutableLiveData(false)
    private val _goPhoneAuth = SingleLiveEvent<Boolean>()

    val serviceAgree: LiveData<Boolean> get() = _serviceAgree
    val personalInfoAgree: LiveData<Boolean> get() = _personalInfoAgree
    val allAgree: LiveData<Boolean> get() = _allAgree
    val anotherAgree: LiveData<Boolean> get() = _anotherAgree
    val goPhoneAuth: LiveData<Boolean> get() = _goPhoneAuth

    fun goPhoneAuth() {
        if (allAgree.value!!) {
            _goPhoneAuth.call()
        }
    }

    fun agreeAll() {
        if (!(serviceAgree.value!! && personalInfoAgree.value!! && anotherAgree.value!!)) { // 모두 동의안 된 경우
            _serviceAgree.value = true
            _personalInfoAgree.value = true
            _anotherAgree.value = true
            _allAgree.value = true
        } else {
            _serviceAgree.value = false
            _personalInfoAgree.value = false
            _anotherAgree.value = false
            _allAgree.value = false
        }
    }

    fun agreeService() {
        _serviceAgree.value = (!serviceAgree.value!!)
        _allAgree.value = serviceAgree.value!! && personalInfoAgree.value!!
    }

    fun agreePersonalInfo() {
        _personalInfoAgree.value = (!personalInfoAgree.value!!)
        _allAgree.value = serviceAgree.value!! && personalInfoAgree.value!!
    }

    fun agreeAnother() {
        _anotherAgree.value = (!anotherAgree.value!!)
        _allAgree.value = serviceAgree.value!! && anotherAgree.value!!
    }


}