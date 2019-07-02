package com.terry.kaiyan.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.terry.kaiyan.R

/**
 * Author:ChenXinming
 * Date:2019/06/27
 * Description:
 */
class ClearEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : EditText(context, attrs, defStyleAttr), View.OnFocusChangeListener, TextWatcher {

    var mRightDrawable:Drawable ?= null
    var mHasFocus:Boolean = false

    init {
        mRightDrawable = compoundDrawables[2]
        if (mRightDrawable == null) {
            mRightDrawable = context.getDrawable(R.drawable.ic_action_clear)
        }
        mRightDrawable?.setBounds(0,0,mRightDrawable!!.intrinsicWidth, mRightDrawable!!.intrinsicHeight)
        visibleClearDrawable(false)
        onFocusChangeListener = this
        addTextChangedListener(this)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        mHasFocus = hasFocus
        if (mHasFocus) {
            visibleClearDrawable(text.isNotEmpty())
        } else {
            visibleClearDrawable(false)
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if (mHasFocus) {
            visibleClearDrawable(text.isNotEmpty())
        }
    }

    private fun visibleClearDrawable(visible : Boolean) {
        var right = if (visible) mRightDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && compoundDrawables[2] != null) {
            val pointX = event.x
            val pointY = event.y
            val rect = compoundDrawables[2].bounds
            val rectHeight = rect.height()
            val distance = (height - rectHeight)/2
            val inX = pointX > width - totalPaddingRight && pointX < width - paddingRight
            val inY = pointY > distance && y < distance + rectHeight
            if (inX && inY) {
                setText("")
            }
        }
        return super.onTouchEvent(event)
    }
}
