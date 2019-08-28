package com.terry.kaiyan.mvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Switch
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.ui.activity.*
import com.terry.kaiyan.utils.durationFormat

/**
 * Author:ChenXinming
 * Date:2019/06/24
 * Description:
 */
class DailyHomeAdapter : BaseMultiItemQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    private var imageLoader: ImageLoader
    private var context:Context ?= null

    constructor(context: Context?) : super(null) {
        this.context = context
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
                var timeDuration = durationFormat(item.data.duration)
                tagText += timeDuration
                helper.setText(R.id.daily_tags_tv, tagText)
                helper.setText(R.id.daily_type_tv, "#${item.data.category}")
                helper.itemView.setOnClickListener {
                    gotoVideoDetailActivity(context as Activity,  helper.getView(R.id.cover_iv), item)
                }
            }
            1 -> {
                helper?.setText(R.id.daily_header_tv, item.data.text)
            }
        }
    }

    private fun gotoVideoDetailActivity(context: Context, view: View, item:HomeBean.Issue.HomeItem?) {
        val pair = Pair(view, TRANSITION_DETAIL_NAME)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, pair)
        var intent = Intent(context, VideoDetailActivity::class.java)
        intent.putExtra(VIDEO_DETAIL_BEAN, item)
        ActivityCompat.startActivity(context, intent, options.toBundle())
    }

}
