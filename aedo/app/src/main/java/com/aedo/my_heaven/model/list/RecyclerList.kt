package com.aedo.my_heaven.model.list

data class RecyclerList(
    var obituary: List<Obituaray>? = null,
)

data class Obituaray(
    var id: String? = null,
    var imgName: String? = null,
    var resident: Resident? = null,
    var place: String? = null,
    var deceased: Deceased? = null,
    var eod: String? = null,
    var coffin: String? = null,
    var dofp: String? = null,
    var buried: String? = null,
    var word: String? = null
)


data class Resident(
    var relation: String? = null,
    var name: String? = null,
    var phone: String? = null
)

data class Deceased(
    var name: String? = null,
    var age: String? = null
)

