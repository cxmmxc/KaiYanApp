package com.terry.kaiyan.mvp.ui.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.utils.durationFormat

/**
 * Author:ChenXinming
 * Date:2019/07/02
 * Description:
 */
class HotDataAdapter : BaseQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    var imageLoader:ImageLoader ?= null

    constructor(context: Context, @LayoutRes layoutId:Int) : super(layoutId){
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.HomeItem?) {
        var coverUrl = item?.data?.cover?.feed ?: ""
        imageLoader?.loadImage(helper?.itemView?.context, ImageConfigImpl.builder()
                .url(coverUrl)
                .placeholder(R.drawable.placeholder_banner)
                .isCenterCrop(true)
                .imageView(helper?.getView(R.id.hotDataIv))
                .build())
        helper?.setText(R.id.hotTitleTv, item?.data?.title?:"")
        val duration = durationFormat(item?.data?.duration!!)
        helper?.setText(R.id.hotTagDurationTv, "#${item.data.category}/$duration")
    }
}
