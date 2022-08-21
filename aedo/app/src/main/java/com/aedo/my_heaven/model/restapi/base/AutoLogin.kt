package com.aedo.my_heaven.model.restapi.base

import com.google.gson.annotations.SerializedName

data class AutoLogin(
    val status: String? = null,
    var id: String? = null,
    @SerializedName("Accesstoken")
    val accesstoken: String? = null
)
