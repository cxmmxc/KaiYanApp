package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.DiscoverLikeContract
import com.terry.kaiyan.mvp.model.DiscoverLikeModel


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建DiscoverLikeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DiscoverLikeModule(private val view: DiscoverLikeContract.View) {
    @FragmentScope
    @Provides
    fun provideDiscoverLikeView(): DiscoverLikeContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDiscoverLikeModel(model: DiscoverLikeModel): DiscoverLikeContract.Model {
        return model
    }
}
