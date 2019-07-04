package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.VideoDetailModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.VideoDetailActivity


/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(VideoDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface VideoDetailComponent {
    fun inject(activity: VideoDetailActivity)
}
