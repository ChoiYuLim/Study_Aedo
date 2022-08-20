package com.aedo.my_heaven.model.restapi.base

data class TremModel (
    val terms : Terms?=null
)

data class Terms(
    val title: String? = null,
    val sub_title : String? = null,
    val first : String? = null,
    val second : String? = null,
    val third : String? = null,
    val fourth : String? = null,
    val fifth : String? = null
)