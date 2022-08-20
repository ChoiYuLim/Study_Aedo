package com.aedo.my_heaven.model.enumeration

enum class Language(
    val cd: String,
    val msgKey: String,
    val languageCodeName: String
) {
    korean("한국어", "ko", "message"), english("English", "en", "message_en");

    companion object {
        fun getLanguage(cd: String): Language {
            for (value in values()) {
                if (value.cd == cd) {
                    return value
                }
            }
            return korean
        }

        fun getLanguageByName(name: String?): Language {
            for (value in values()) {
                if (value.name.endsWith(name!!)) {
                    return value
                }
            }
            return korean
        }
    }
}