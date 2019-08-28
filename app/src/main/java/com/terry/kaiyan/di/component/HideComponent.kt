package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.HideModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.HideActivity


/**
 * Author:ChenXinming
 * Date: 2019/08/27
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(HideModule::class), dependencies = arrayOf(AppComponent::class))
interface HideComponent {
    fun inject(activity: HideActivity)
}
