package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.HideDetailModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.HideDetailActivity


/**
 * Author:ChenXinming
 * Date: 2019/08/28
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(HideDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface HideDetailComponent {
    fun inject(activity: HideDetailActivity)
}
