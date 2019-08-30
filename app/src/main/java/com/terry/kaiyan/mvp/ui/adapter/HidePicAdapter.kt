package com.terry.kaiyan.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.glide.GlideArms
import com.terry.kaiyan.R

/**
 * Author:ChenXinming
 * Date:2019/08/29
 * Description:
 */
class HidePicAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hide_pic) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        GlideArms.with(helper?.itemView?.context!!)
            .load(item)
            .into(helper?.getView(R.id.picIv)!!)
    }
}
