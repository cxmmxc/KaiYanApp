package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.model.DailyModel


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建DailyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DailyModule(private val view: DailyContract.View) {
    @FragmentScope
    @Provides
    fun provideDailyView(): DailyContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDailyModel(model: DailyModel): DailyContract.Model {
        return model
    }
}
