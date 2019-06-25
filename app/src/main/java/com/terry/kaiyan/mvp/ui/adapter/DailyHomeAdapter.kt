package com.terry.kaiyan.mvp.ui.adapter

import android.content.Context
import android.widget.Switch
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
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
class DailyHomeAdapter : BaseMultiItemQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    private var imageLoader: ImageLoader

    constructor(context: Context?) : super(null) {
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
        addItemType(0, R.layout.item_daily_list)
        addItemType(1, R.layout.item_daily_header)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.HomeItem?) {

        var tagText: String? = "#"
        when (item?.itemType) {
            0 -> {
                imageLoader.loadImage(
                    helper?.itemView?.context, ImageConfigImpl.builder()
                        .url(item.data.cover.feed)
                        .imageView(helper!!.getView(R.id.cover_iv))
                        .isCenterCrop(true)
                        .build()
                )
                imageLoader.loadImage(
                    helper?.itemView?.context, ImageConfigImpl.builder()
                        .url(item.data.author.icon)
                        .placeholder(R.mipmap.default_avatar)
                        .isCircle(true)
                        .imageView(helper?.getView(R.id.daily_header_iv))
                        .build()
                )
                helper.setText(R.id.daily_description_tv, item.data.title)
                item.data.tags.take(4).forEach {
                    tagText += it.name + "/"
                }
                var timeDuration =durationFormat(item.data.duration)
                tagText += timeDuration
                helper.setText(R.id.daily_tags_tv, tagText)
                helper.setText(R.id.daily_type_tv, "#${item.data.category}")
            }
            1 -> {
                helper?.setText(R.id.daily_header_tv, item.data.text)
            }
        }

    }

    private fun durationFormat(duration: Long): String {
        val minute = duration/60
        val second = duration % 60
        return if (minute <= 9) {
            if (second <= 9) {
                "0$minute' 0$second''"
            } else {
                "0$minute' $second''"
            }
        } else {
            if (second <= 9) {
                "$minute' 0$second''"
            } else {
                "$minute' $second''"
            }
        }

    }
}
