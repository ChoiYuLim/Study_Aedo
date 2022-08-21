package com.aedo.my_heaven.util.local

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

object Local {
    private const val LANGUAGE_KEY = "language_key"

    @JvmStatic
    fun setNewLocale(context: Context, language: String) {
        persistLanguage(context, language)
        updateResources(context, language)
    }

    private fun persistLanguage(context: Context, language: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        pref.edit().putString(LANGUAGE_KEY, language).apply()
    }

    private fun updateResources(context: Context, language: String?): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        context = context.createConfigurationContext(config)
        return context
    }
}