package com.terry.kaiyan.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.glide.GlideArms
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase

/**
 * Author:ChenXinming
 * Date:2019/08/27
 * Description:
 */
class HideAdapter : BaseQuickAdapter<DouyinBeanBase.ResultBean.DouyinBean, BaseViewHolder>(R.layout.item_hide) {

    override fun convert(helper: BaseViewHolder?, item: DouyinBeanBase.ResultBean.DouyinBean?) {
        GlideArms.with(helper?.itemView?.context!!)
            .load(item?.VideoImg)
            .into(helper.getView(R.id.hideCoverIv))
        helper.setText(R.id.hideTitleTv, item?.Title)
        helper.setText(R.id.hideTimeTv, item?.CreateTime)
    }
}
