package com.aedo.my_heaven.model.restapi.login

import com.google.gson.annotations.SerializedName

data class LoginSend(
    val phone: String? = null,
    val smsnumber: String? = null,
    val status: String? = null,

    @SerializedName("Accesstoken")
    val accesstoken: String? = null
)