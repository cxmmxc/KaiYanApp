package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.HideContract
import com.terry.kaiyan.mvp.model.HideModel


/**
 * Author:ChenXinming
 * Date: 2019/08/27
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建HideModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HideModule(private val view: HideContract.View) {
    @ActivityScope
    @Provides
    fun provideHideView(): HideContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideHideModel(model: HideModel): HideContract.Model {
        return model
    }
}
