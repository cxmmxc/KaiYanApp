package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.model.SearchModel


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建SearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SearchModule(private val view: SearchContract.View) {
    @ActivityScope
    @Provides
    fun provideSearchView(): SearchContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideSearchModel(model: SearchModel): SearchContract.Model {
        return model
    }
}
