package com.terry.kaiyan.mvp.ui.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean

/**
 * Author:ChenXinming
 * Date:2019/06/24
 * Description:
 */
class DailyHomeAdapter : BaseQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    private lateinit var imageLoader: ImageLoader

    constructor(context: Context) : super(R.layout.item_daily_list) {
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.HomeItem?) {
        item?.data?.cover?.let {
            imageLoader.loadImage(
                helper?.itemView?.context, ImageConfigImpl.builder()
                    .url(item.data.cover.feed)
                    .imageView(helper!!.getView(R.id.cover_iv))
                    .build())
        }

    }
}
