package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.HidePicModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.HidePicActivity


/**
 * Author:ChenXinming
 * Date: 2019/08/29
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(HidePicModule::class), dependencies = arrayOf(AppComponent::class))
interface HidePicComponent {
    fun inject(activity: HidePicActivity)
}
