package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.HotContract
import com.terry.kaiyan.mvp.model.HotModel


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建HotModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HotModule(private val view: HotContract.View) {
    @FragmentScope
    @Provides
    fun provideHotView(): HotContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideHotModel(model: HotModel): HotContract.Model {
        return model
    }
}
