package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.HideDetailContract
import com.terry.kaiyan.mvp.model.HideDetailModel


/**
 * Author:ChenXinming
 * Date: 2019/08/28
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建HideDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HideDetailModule(private val view: HideDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideHideDetailView(): HideDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideHideDetailModel(model: HideDetailModel): HideDetailContract.Model {
        return model
    }
}
