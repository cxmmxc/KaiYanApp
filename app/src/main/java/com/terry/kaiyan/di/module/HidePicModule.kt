package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.HidePicContract
import com.terry.kaiyan.mvp.model.HidePicModel


/**
 * Author:ChenXinming
 * Date: 2019/08/29
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建HidePicModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HidePicModule(private val view: HidePicContract.View) {
    @ActivityScope
    @Provides
    fun provideHidePicView(): HidePicContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideHidePicModel(model: HidePicModel): HidePicContract.Model {
        return model
    }
}
