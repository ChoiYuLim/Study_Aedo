package com.aedo.my_heaven.model.restapi.base

data class CreateName(
    val result: List<CreateSearch>? = null
)

data class CreateSearch(
    var id: String? = null,
    var resident: SResident? = null,
    var place: String? = null,
    var deceased: SDeceased? = null,
    var eod: String? = null,
    var coffin: String? = null,
    var dofp: String? = null,
    var buried: String? = null,
    var word: String? = null
)


data class SResident(
    var relation: String? = null,
    var name: String? = null,
    var phone: String? = null
)

data class SDeceased(
    var name: String? = null,
    var age: String? = null
)
