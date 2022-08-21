package com.aedo.my_heaven.model.restapi.login

data class GetUser(
    val user: UserGet? = null
)

data class UserGet(
    val id: String? = null,
    val phone: String? = null,
    val birth: String? = null,
    val name: String? = null,
    val admin: String? = null,
    val terms: String? = null
)
