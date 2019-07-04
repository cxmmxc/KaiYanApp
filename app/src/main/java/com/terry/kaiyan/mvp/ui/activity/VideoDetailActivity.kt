package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerVideoDetailComponent
import com.terry.kaiyan.di.module.VideoDetailModule
import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.presenter.VideoDetailPresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.model.Bean.HomeBean.Issue
import com.terry.kaiyan.mvp.model.Bean.HomeBean.Issue.HomeItem
import kotlinx.android.synthetic.main.activity_video_detail.*


/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */

const val VIDEO_DETAIL_BEAN = "itemBean"
class VideoDetailActivity : BaseActivity<VideoDetailPresenter>(), VideoDetailContract.View {


    private var nowHomeItem: HomeBean.Issue.HomeItem ?= null

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
        initVideoConfig()
        mPresenter?.getPlayInfo(nowHomeItem!!)
    }

    private fun initVideoConfig() {


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

    override fun setVideoPlayUrl(url: String) {
        videoPlayer.setUp(url, false, "")
        videoPlayer.startPlayLogic()
    }

    override fun setRelatedBgUrl(url: String) {
    }

    override fun setPlayInfo(homeItem: HomeItem) {
    }

    override fun getRelated(issue: Issue) {
    }
}
