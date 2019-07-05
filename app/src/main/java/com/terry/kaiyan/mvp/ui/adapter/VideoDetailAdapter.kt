package com.terry.kaiyan.mvp.ui.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.utils.durationFormat

/**
 * Author:ChenXinming
 * Date:2019/07/05
 * Description:
 */
class VideoDetailAdapter : BaseMultiItemQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    var imageLoader:ImageLoader ?= null

    constructor(context: Context) : super(null) {
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
        addItemType(1, R.layout.item_detail_header)
        addItemType(0, R.layout.item_detail_list)
    }

    override fun convert(helper: BaseViewHolder?,
                         item: HomeBean.Issue.HomeItem?) {
        when (item?.itemTYpe) {
            1 -> {
                helper?.setText(R.id.detailHeadTv, item.data.text)
            }
            0 -> {
                imageLoader?.loadImage(helper?.itemView?.context,
                    ImageConfigImpl
                        .builder()
                        .url(item.data.cover.feed)
                        .isCenterCrop(true)
                        .imageView(helper?.getView(R.id.detailItemCoverIv))
                        .build())
                helper?.setText(R.id.detailItemTitleTv, item.data.title)
                helper?.setText(R.id.detailItemTagTv, "#${item.data.category}")
                helper?.setText(R.id.detailTimeTv, "#${durationFormat(item.data.duration)}")
                helper?.addOnClickListener(R.id.detailItemRootView)
            }
        }
    }
}
