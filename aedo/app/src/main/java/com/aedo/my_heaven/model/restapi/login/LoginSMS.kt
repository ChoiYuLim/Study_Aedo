package com.aedo.my_heaven.model.restapi.login

data class LoginSMS(
    val phone: String? = null,
    val user_auth_number: String? = null,
    val smsnumber: String? = null
)