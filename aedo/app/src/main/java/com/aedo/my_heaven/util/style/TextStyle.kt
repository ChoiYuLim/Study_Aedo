package com.aedo.my_heaven.util.style

import android.annotation.SuppressLint
import android.telephony.PhoneNumberUtils
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TextStyle {
    private const val SimpleDateFormat = "SimpleDateFormat"
    fun intToKoreaWon(price: Int, addWonSymbol: Boolean): String {
        //return (addWonSymbol ? "₩" : "") + String.format(Locale.KOREA, "%,d", price);
        return (if (addWonSymbol) "₩" else "") + String.format(Locale.KOREA, "%,d", price)
    }

    fun intToKoreaWon(price: String, addWonSymbol: Boolean): String {
        return (if (addWonSymbol) "₩" else "") + String.format(Locale.KOREA, "%,d", price.toInt())
    }

    fun maskingPhoneNum(phone: String?): String {
        return if (phone != null) {
            val front = phone.substring(0, 3)
            val back = phone.substring(phone.length - 4)
            val mask = if (phone.length == 11) "****" else "***"
            front + mask + back
        } else {
            ""
        }
    }

    fun hypenPhoneNum(phone: String?): String {
        if (phone == null) {
            return ""
        }
        if (phone.length > 11 || phone.length < 8) {
            return phone
        }
        var phoneNum: String? = ""
        phoneNum = PhoneNumberUtils.formatNumber(phone, Locale.getDefault().country)
        if (phoneNum == null) {
            val sb = StringBuilder()
            sb.append(phone.substring(0, 3))
            sb.append("-")
            sb.append(phone.substring(3, 7))
            sb.append("-")
            sb.append(phone.substring(7))
            phoneNum = sb.toString()
        }
        return phoneNum
    }

    fun compareDateAvailable(date: String): Boolean {
        return compareDate(date, "yyyy-MM-dd HH:mm:ss")
    }

    fun compareDateAvailable(date: String, dateFormat: String): Boolean {
        return compareDate(date, dateFormat)
    }

    private fun compareDate(date: String, dateFormat: String): Boolean {
        val endDay = date.replace("T", " ")
        @SuppressLint(SimpleDateFormat) val receivedDateFormat = SimpleDateFormat(dateFormat)
        var receivedDate: Date? = null
        val nowDate = Date()
        try {
            receivedDate = receivedDateFormat.parse(endDay)
        } catch (e: ParseException) {
            e.localizedMessage
        }
        return if (receivedDate != null) {
            val compare = receivedDate.compareTo(nowDate)
            compare > 0
        } else {
            //기간 만료로 인해 출력하지 않음
            false
        }
    }

    fun toString(bytes: ByteArray?): String {
        return String(bytes!!, StandardCharsets.UTF_8)
    }

    @SuppressLint("SimpleDateFormat")
    @JvmOverloads
    fun dateToParsedDate(date: String?, dateFormat: String? = "yyyy.MM.dd HH:mm:ss"): String? {
        return if (date != null) {
            val dateString = date.replace("T", " ")
            //2019-08-14T10:40:21 2021-03-09T15:43:04
            @SuppressLint(SimpleDateFormat) val receivedDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            @SuppressLint(SimpleDateFormat) val convertDateFormat = SimpleDateFormat(dateFormat)
            val receivedDate: Date = try {
                receivedDateFormat.parse(dateString)
            } catch (e: ParseException) {
                //receivedDate = new Date();
                return date
            }
            convertDateFormat.format(Objects.requireNonNull(receivedDate))
        } else {
            null
        }
    }

    fun dateStrToDate(date: String?): Date? {
        return if (date != null) {
            val dateString = date.replace("T", " ")
            //2019-08-14T10:40:21
            @SuppressLint(SimpleDateFormat, "SimpleDateFormat") val receivedDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val receivedDate: Date = try {
                receivedDateFormat.parse(dateString)
            } catch (e: ParseException) {
                //receivedDate = new Date();
                return null
            }
            receivedDate
        } else {
            null
        }
    }
}