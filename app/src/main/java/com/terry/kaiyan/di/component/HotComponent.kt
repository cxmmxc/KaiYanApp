package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.HotModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.HotFragment


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(HotModule::class), dependencies = arrayOf(AppComponent::class))
interface HotComponent {
    fun inject(fragment: HotFragment)
}
