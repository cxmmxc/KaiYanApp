package com.terry.kaiyan.utils

import java.io.UnsupportedEncodingException
import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.experimental.and

/**
 * Author:ChenXinming
 * Date:2019/08/27
 * Description:
 */
fun paramsToSign(hashMap:HashMap<String, String>):String{
    var str :String ?= null
    val list = ArrayList<String>()
    for (element in hashMap) {
        val key = element.key
        val value = element.value
        if (value.isNotEmpty() && "Sign" != value && "key" != value) {
            list.add("$key=$value")
        }
    }
    Collections.sort(list, String.CASE_INSENSITIVE_ORDER)
    val sb = StringBuilder()
    for (value in list) {
        sb.append(value).append("&")
    }
    sb.append("key=ZD4417JEFFDDSCC50H3FAE3C787D0E23")
    str =  sb.toString()
    return toMd5(str)
}

fun toMd5(str: String?): String {
    val hash:ByteArray?
    try {
        hash = MessageDigest.getInstance("MD5").digest(str?.toByteArray(Charsets.UTF_8))
    } catch (e : NoSuchAlgorithmException) {
        throw RuntimeException("NoSuchAlgorithmException",e)
    } catch (e : UnsupportedEncodingException) {
        throw RuntimeException("UnsupportedEncodingException", e)
    }
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        if((b.toInt() and 0xFF) < 0x10){
            hex.append("0")
        }
        hex.append(Integer.toHexString(b.toInt() and 0xFF))
    }
    return hex.toString().toUpperCase()
}
