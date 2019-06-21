package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.DicoverModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.DiscoverFragment


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(DicoverModule::class), dependencies = arrayOf(AppComponent::class))
interface DicoverComponent {
    fun inject(fragment: DiscoverFragment)
}
