package com.aedo.my_heaven.model.shop

data class ShopModel(
    val order: Orders? = null
)

data class Orders(
    val place: Place? = null,
    val item: String? = null,
    val price: String? = null,
    val receiver: Receiver? = null,
    val sender: Sender? = null,
    val word: Word? = null,
    val created: String? = null,
    val id: String? = null
)

data class Place(
    val name: String? = null,
    val number: String? = null
)

data class Receiver(
    val name: String? = null,
    val phone: String? = null
)

data class Sender(
    val name: String? = null,
    val phone: String? = null,
)

data class Word(
    val company: String? = null,
    val word: String? = null,
    val wordsecond: String? = null
)
