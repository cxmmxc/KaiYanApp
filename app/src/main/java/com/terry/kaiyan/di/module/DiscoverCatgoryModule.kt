package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.DiscoverCatgoryContract
import com.terry.kaiyan.mvp.model.DiscoverCatgoryModel


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建DiscoverCatgoryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DiscoverCatgoryModule(private val view: DiscoverCatgoryContract.View) {
    @FragmentScope
    @Provides
    fun provideDiscoverCatgoryView(): DiscoverCatgoryContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDiscoverCatgoryModel(model: DiscoverCatgoryModel): DiscoverCatgoryContract.Model {
        return model
    }
}
