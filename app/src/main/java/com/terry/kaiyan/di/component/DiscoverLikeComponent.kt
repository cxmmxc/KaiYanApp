package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.DiscoverLikeModule

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.mvp.ui.fragment.DiscoverLikeFragment


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
@Component(modules = arrayOf(DiscoverLikeModule::class), dependencies = arrayOf(AppComponent::class))
interface DiscoverLikeComponent {
    fun inject(fragment: DiscoverLikeFragment)
}
