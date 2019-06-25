package com.terry.kaiyan.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.terry.kaiyan.di.module.SearchModule

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.mvp.ui.activity.SearchActivity


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
@Component(modules = arrayOf(SearchModule::class), dependencies = arrayOf(AppComponent::class))
interface SearchComponent {
    fun inject(activity: SearchActivity)
}
