package com.terry.kaiyan.widget

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.util.AttributeSet
import android.widget.TextView
import android.widget.ViewAnimator
import com.jess.arms.utils.LogUtils
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Author:ChenXinming
 * Date:2019/07/04
 * Description:
 */


class TyperTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {


    private var contentLength = 0
    private var currentIndex = 0
    private var content: String? = null
    private var mTypeTimer: Timer? = null

    init {
    }

    fun startStr(content: String?) {
        if (content.isNullOrEmpty()) {
            return
        }
        text = ""
        currentIndex = 0
        this.content = content
        contentLength = content.length
        startTypeTimer()
    }

    private fun startTypeTimer() {
        stopTimer()
        mTypeTimer = Timer()
        mTypeTimer?.schedule(timerTask {
            post(Runnable {
                if (text.length < contentLength) {
                    text = content?.substring(0, text.length + 1)
                    startTypeTimer()
                } else {
                    stopTimer()
                }
            })
        }, 20)
    }

    private fun stopTimer() {
        mTypeTimer?.cancel()
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTimer()
    }

}

