package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.MineModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.MineFragment


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(MineModule::class), dependencies = arrayOf(AppComponent::class))
interface MineComponent {
    fun inject(fragment: MineFragment)
}
