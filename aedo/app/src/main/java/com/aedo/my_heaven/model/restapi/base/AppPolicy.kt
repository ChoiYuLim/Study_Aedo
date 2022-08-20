package com.aedo.my_heaven.model.restapi.base

import io.realm.RealmObject

data class AppPolicy (
    var id: String?=null,
    var result : String?= null,
    var policy : List<Policy>?= null,
    var code : List<Code>?= null,
    var app_menu : List<AppMenu>?= null,
    var coordinates : List<Coordinates>?=null
)

open class Policy : RealmObject() {
    var id : String?= null
    var name : String?= null
    var value: String?=null
}

open class Code : RealmObject() {
    var cd : String?= null
    var name : String? = null
    var name_en : String? = null
    var category_cd : String?= null
    var category_name : String?= null

    open fun getLocalizeName(langCd: String): String? {
        return if ("en" == langCd) {
            name_en
        } else {
            name
        }
    }
}

open class AppMenu : RealmObject() {
    var cd : String?= null
    var name : String?= null
    var name_en : String?= null
    var category_cd : String?= null
    var category_name : String?= null
    var icon_name : String?= null
    var use_yn : String?= null
}

open class Coordinates : RealmObject() {
    var id : String? = null
    var name : String? = null
    var xvalue : Double? = null
    var yvalue : Double? = null
    var address : String? = null
}