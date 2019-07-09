package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.DiscoverCatgoryModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.DiscoverCatgoryFragment


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(DiscoverCatgoryModule::class), dependencies = arrayOf(AppComponent::class))
interface DiscoverCatgoryComponent {
    fun inject(fragment: DiscoverCatgoryFragment)
}
