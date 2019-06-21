package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.DailyModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.DailyFragment


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(DailyModule::class), dependencies = arrayOf(AppComponent::class))
interface DailyComponent {
    fun inject(fragment: DailyFragment)
}
