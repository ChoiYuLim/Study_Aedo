package com.aedo.my_heaven.model.shop

data class MyOrder (
    val order : List<MyOrders>? = null
)

data class MyOrders(
    val place : MyPlace? = null,
    val item : String? = null,
    val price : String? = null,
    val receiver : MyReceiver? = null,
    val sender : MySender? = null,
    val word : MyWord? = null,
    val created : String? = null,
    val id : String? = null
)

data class MyPlace(
    val name : String? = null,
    val number : String? = null
)

data class MyReceiver(
    val name : String? = null,
    val phone : String? = null
)

data class MySender(
    val name : String? = null,
    val phone : String? = null,
)

data class MyWord(
    val company : String? = null,
    val word : String? = null,
    val wordsecond : String? = null
)
