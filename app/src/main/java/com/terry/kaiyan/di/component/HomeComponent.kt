package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.HomeModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.HomeActivity


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent {
    fun inject(activity: HomeActivity)
}
