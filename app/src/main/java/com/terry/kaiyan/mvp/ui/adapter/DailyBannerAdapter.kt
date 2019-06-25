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
 * 作者：Terry.CHen
 * 创建日期：2019/06/24
 * 邮箱：herewinner@163.com
 * 描述：TODO
 */
class DailyBannerAdapter : BaseQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    private lateinit var mImageLoader: ImageLoader

    constructor(context: Context) : super(R.layout.item_banner_image) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
    }


    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.HomeItem?) {
        mImageLoader.loadImage(
            helper?.itemView?.context,
            ImageConfigImpl.builder()
                .url(item?.data?.cover?.feed)
                .placeholder(R.drawable.placeholder_banner)
                .imageView(helper?.getView(R.id.bannerImage))
                .isCenterCrop(true)
                .build()
        )
    }
}
