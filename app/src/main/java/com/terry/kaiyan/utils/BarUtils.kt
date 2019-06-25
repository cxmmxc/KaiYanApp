package com.terry.kaiyan.utils

import android.content.Context

/**
 * Author:ChenXinming
 * Date:2019/06/25
 * Description:
 */

const val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"

fun getStatusBarHeight(context: Context?): Int {
    return getInternalDimensionSize(context!!, STATUS_BAR_HEIGHT_RES_NAME)
}

fun getInternalDimensionSize(context: Context, key: String): Int {
    var result: Int = 0
    val resouceId = context.resources.getIdentifier(key, "dimen", "android")
    if (resouceId > 0) {
        result = context.resources.getDimensionPixelSize(resouceId)
    }
    return result
}
