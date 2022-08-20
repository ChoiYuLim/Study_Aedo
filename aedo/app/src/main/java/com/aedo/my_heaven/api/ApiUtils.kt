package com.aedo.my_heaven.api

import com.aedo.my_heaven.util.`object`.Constant.BASE_URL

object ApiUtils {
    val apiService: APIService
    get() = RetrofitClient.getClient(BASE_URL).create(APIService::class.java)

}