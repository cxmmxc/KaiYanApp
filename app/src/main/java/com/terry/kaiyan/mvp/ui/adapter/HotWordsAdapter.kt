package com.terry.kaiyan.mvp.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.terry.kaiyan.R

/**
 * Author:ChenXinming
 * Date:2019/07/02
 * Description:
 */
class HotWordsAdapter(layoutResId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.hotWordTv, item)
        var layoutParams = helper?.getView<TextView>(R.id.hotWordTv)?.layoutParams
        if (layoutParams is FlexboxLayoutManager.LayoutParams) {
            layoutParams.flexGrow = 1.0f
        }
        helper?.addOnClickListener(R.id.hotWordTv)
    }
}
