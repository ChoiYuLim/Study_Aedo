package com.aedo.my_heaven.model.restapi.base

data class Verification (
    var result : String?= null,
    var app_token : List<Apptoken>?= null,
    var encrypt: List<Encrypt>?= null,
    var policy_ver : List<PolicyVer>?=null
)

data class Apptoken(
    var name: String?= null
)

data class Encrypt(
    var key : String?= null,
    var iv : String?= null
)

data class PolicyVer(
    var version : Long?= null
)