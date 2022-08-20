package com.aedo.my_heaven.util.common

class CommonData  {
    private fun CommonData() {
    }

    private object LazyHolder {
        val INSTANCE = CommonData()
    }

    fun getInstance(): CommonData {
        return LazyHolder.INSTANCE
    }

    var defaultPhoneNumber: String? = null

    var numStarted = 0

    var isForeground = false
    var isMainRefresh = false
    var isMainNoticeRefresh = false
    var isBleUnlock = false

    @Volatile
    var userSeq = "-1"

    var rentProcessBikeId: String? = null

}