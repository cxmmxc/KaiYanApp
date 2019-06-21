package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.DicoverContract
import com.terry.kaiyan.mvp.model.DicoverModel


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建DicoverModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DicoverModule(private val view: DicoverContract.View) {
    @FragmentScope
    @Provides
    fun provideDicoverView(): DicoverContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDicoverModel(model: DicoverModel): DicoverContract.Model {
        return model
    }
}
