package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.MineContract
import com.terry.kaiyan.mvp.model.MineModel


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MineModule(private val view: MineContract.View) {
    @FragmentScope
    @Provides
    fun provideMineView(): MineContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideMineModel(model: MineModel): MineContract.Model {
        return model
    }
}
