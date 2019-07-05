package com.terry.kaiyan.mvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.ui.activity.TRANSITION_DETAIL_NAME
import com.terry.kaiyan.mvp.ui.activity.VIDEO_DETAIL_BEAN
import com.terry.kaiyan.mvp.ui.activity.VideoDetailActivity

/**
 * 作者：Terry.CHen
 * 创建日期：2019/06/24
 * 邮箱：herewinner@163.com
 * 描述：TODO
 */
class DailyBannerAdapter : BaseQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {
    private lateinit var mImageLoader: ImageLoader
    private lateinit var context: Context

    constructor(context: Context) : super(R.layout.item_banner_image) {
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
        this.context = context
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

        helper?.itemView?.setOnClickListener {
            gotoVideoDetailActivity(context, helper.getView(R.id.bannerImage), item)
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
