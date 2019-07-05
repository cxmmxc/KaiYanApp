package com.terry.kaiyan.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.widget.TextView

/**
 * Author:ChenXinming
 * Date:2019/07/04
 * Description:
 */

const val APPEND_WHAT = 1

class TyperTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var contentLength = 0
    private var currentIndex = 0
    private var content:String ?= null
    private var delayTime:Int = 10

    fun startStr(content: String?) {
        if (content.isNullOrEmpty()) {
            return
        }
        this.content = content
        contentLength = content.length
        delayTime = when {
            contentLength < 15 -> 70
            contentLength < 25 -> 25
            else -> 10
        }
        appendHander.sendEmptyMessage(APPEND_WHAT)
    }

    val appendHander:Handler = object : Handler(Looper.myLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                APPEND_WHAT -> {
                    if (currentIndex >= contentLength) {
                        removeMessages(APPEND_WHAT)
                        currentIndex = 0
                        return
                    }
                    append(content?.subSequence(currentIndex, currentIndex+1))
                    sendEmptyMessageDelayed(APPEND_WHAT, delayTime.toLong())
                    currentIndex++
                }

            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        appendHander.removeMessages(APPEND_WHAT)
    }

}
