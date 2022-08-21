package com.aedo.my_heaven.model.firebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDTO(
    var spinner_text: String? = null,
    var make_person: String? = null,
    var make_phone: String? = null,
    var place: String? = null,
    var deceased_name: String? = null,
    var deceased_age: String? = null,
    var eod_date: String? = null,
    var eod_time: String? = null,
    var coffin_date: String? = null,
    var coffin_time: String? = null,
    var dofp_date: String? = null,
    var dofp_time: String? = null,
    var buried: String? = null,
    var timestamp: Long? = null
) : Parcelable
