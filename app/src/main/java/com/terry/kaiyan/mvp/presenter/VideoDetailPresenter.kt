package com.terry.kaiyan.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean

/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class VideoDetailPresenter
@Inject
constructor(
  model: VideoDetailContract.Model,
  rootView: VideoDetailContract.View
) :
    BasePresenter<VideoDetailContract.Model, VideoDetailContract.View>(model, rootView) {
  @Inject
  lateinit var mErrorHandler: RxErrorHandler
  @Inject
  lateinit var mApplication: Application
  @Inject
  lateinit var mImageLoader: ImageLoader
  @Inject
  lateinit var mAppManager: AppManager

  override fun onDestroy() {
    super.onDestroy()
  }

  fun getPlayInfo(homeItem: HomeBean.Issue.HomeItem) {
    mRootView.setVideoPlayUrl(homeItem.data.playUrl)
  }
}
