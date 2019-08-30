package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

import com.terry.kaiyan.di.component.DaggerHideDetailComponent
import com.terry.kaiyan.di.module.HideDetailModule
import com.terry.kaiyan.mvp.contract.HideDetailContract
import com.terry.kaiyan.mvp.presenter.HideDetailPresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.app.GSYVideoListener
import kotlinx.android.synthetic.main.activity_hide_detail.*
import kotlinx.android.synthetic.main.activity_video_detail.*


/**
 * Author:ChenXinming
 * Date: 2019/08/28
 * Email:chenxinming@antelop.cloud
 * Description:
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class HideDetailActivity : BaseActivity<HideDetailPresenter>(), HideDetailContract.View {

    private var coverUrl: String? = null
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHideDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .hideDetailModule(HideDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_hide_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        val url = intent.getStringExtra("url")
        coverUrl = intent.getStringExtra("cover")
        initVideoConfig()
        hidePlayer.setUp(url, true, "")
        hidePlayer.startPlayLogic()
    }
    private fun initVideoConfig() {
        orientationUtils = OrientationUtils(this, hidePlayer)

        hidePlayer.isRotateViewAuto = false
        hidePlayer.setIsTouchWiget(true)

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ArmsUtils.obtainAppComponentFromContext(this)
                .imageLoader()
                .loadImage(
                        this, ImageConfigImpl.builder().url(coverUrl).imageView(
                        imageView
                ).build()
                )
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT)
        hidePlayer.thumbImageView = imageView
        hidePlayer.setVideoAllCallBack(object : GSYVideoListener {
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
        hidePlayer.isShowFullAnimation = true
        hidePlayer.backButton.setOnClickListener { onBackPressed() }
        hidePlayer.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
            hidePlayer.startWindowFullscreen(this, true, true)
        }

        hidePlayer.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

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
