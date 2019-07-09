package com.terry.kaiyan.mvp.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.app.GSYVideoListener
import com.terry.kaiyan.di.component.DaggerVideoDetailComponent
import com.terry.kaiyan.di.module.VideoDetailModule
import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.presenter.VideoDetailPresenter
import com.terry.kaiyan.mvp.ui.adapter.VideoDetailAdapter
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.item_header_detail.*

/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */

const val VIDEO_DETAIL_BEAN = "itemBean"
const val TRANSITION_DETAIL_NAME = "transition_detail_name"

class VideoDetailActivity : BaseActivity<VideoDetailPresenter>(), VideoDetailContract.View {

    private var nowHomeItem: HomeBean.Issue.HomeItem? = null
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var mDetailAdapter: VideoDetailAdapter? = null
    private var headerView: View? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerVideoDetailComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .videoDetailModule(VideoDetailModule(this))
            .build()
            .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_video_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        nowHomeItem = intent.getSerializableExtra(VIDEO_DETAIL_BEAN) as HomeBean.Issue.HomeItem
        initTransition()
        initVideoConfig()
        mDetailAdapter = VideoDetailAdapter(this)
        detailRelatedRecyclerView.adapter = mDetailAdapter
        detailRelatedRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        detailRelatedRecyclerView.itemAnimator = DefaultItemAnimator()
        inflateHeader()
        mDetailAdapter?.setOnItemChildClickListener { adapter, view, position ->
            var item = adapter.getItem(position) as HomeBean.Issue.HomeItem
            mDetailAdapter?.setNewData(null)
            mPresenter?.getPlayInfo(item)
        }
    }

    private fun inflateHeader() {
        headerView = LayoutInflater.from(this)
            .inflate(R.layout.item_header_detail, null)
        mDetailAdapter?.setHeaderView(headerView)

    }

    private fun updateHeaderView() {
        detailTitleTv.text = ""
        tagTitleTv.text = ""
        descriptionTitleTv.text = ""
        LogUtils.debugInfo(
            "cxm",
            "title = ${nowHomeItem?.data?.title} , category = ${nowHomeItem?.data?.category}, description = ${nowHomeItem?.data?.description}"
        )
        detailTitleTv?.text = nowHomeItem?.data?.title
        tagTitleTv?.text = "#${nowHomeItem?.data?.category}"
        descriptionTitleTv?.text = nowHomeItem?.data?.description
        detailLikeTv?.text = nowHomeItem?.data?.consumption?.collectionCount.toString()
        detailShareTv?.text = nowHomeItem?.data?.consumption?.shareCount.toString()
        detailReplyTv?.text = nowHomeItem?.data?.consumption?.replyCount.toString()
    }

    private fun initTransition() {
        postponeEnterTransition()
        ViewCompat.setTransitionName(videoPlayer, TRANSITION_DETAIL_NAME)
        var transition: Transition = window.sharedElementEnterTransition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                mPresenter?.getPlayInfo(nowHomeItem)
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {

            }
        })
        startPostponedEnterTransition()
    }

    private fun initVideoConfig() {
        orientationUtils = OrientationUtils(this, videoPlayer)

        videoPlayer.isRotateViewAuto = false
        videoPlayer.setIsTouchWiget(true)

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ArmsUtils.obtainAppComponentFromContext(this)
            .imageLoader()
            .loadImage(
                this, ImageConfigImpl.builder().url(nowHomeItem?.data?.cover?.feed).imageView(
                    imageView
                ).build()
            )
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        videoPlayer.thumbImageView = imageView
        videoPlayer.setVideoAllCallBack(object : GSYVideoListener {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                isPlay = true
                orientationUtils?.isEnable = true
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                super.onPlayError(url, *objects)
                ArmsUtils.snackbarText(getString(R.string.error_play_fail))
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }
        })
        videoPlayer.isShowFullAnimation = true
        videoPlayer.backButton.setOnClickListener { onBackPressed() }
        videoPlayer.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
            videoPlayer.startWindowFullscreen(this, true, true)
        }

        videoPlayer.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }

    }

    override fun showLoading() {
        detailProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        detailProgress.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    override fun setVideoPlayUrl(url: String?) {
        videoPlayer.setUp(url, false, "")
        videoPlayer.startPlayLogic()
    }

    override fun setRelatedBgUrl(url: String?) {
        ArmsUtils.obtainAppComponentFromContext(this)
            .imageLoader()
            .loadImage(
                this, ImageConfigImpl.builder().url(url).isCenterCrop(true).imageView(
                    detailRelatedBgIv
                ).build()
            )
    }

    override fun setPlayInfo(homeItem: HomeBean.Issue.HomeItem?) {
        nowHomeItem = homeItem
        updateHeaderView()
        mPresenter?.getRelatedVideos(nowHomeItem?.data?.id)
    }

    override fun getRelated(issue: HomeBean.Issue?) {
        var itemList = issue?.itemList
        itemList?.forEach {
            if (it.type == "textCard") {
                it.itemTYpe = 1
            }
        }
        mDetailAdapter?.setNewData(itemList)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.currentPlayer.onVideoPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.currentPlayer.onVideoResume(false)
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            videoPlayer.currentPlayer.release()
        }
        orientationUtils?.releaseListener()
    }
}
