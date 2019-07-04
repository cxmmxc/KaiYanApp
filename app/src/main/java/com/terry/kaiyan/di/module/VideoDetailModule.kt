package com.terry.kaiyan.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.model.VideoDetailModel


/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@Module
//构建VideoDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class VideoDetailModule(private val view: VideoDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideVideoDetailView(): VideoDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideVideoDetailModel(model: VideoDetailModel): VideoDetailContract.Model {
        return model
    }
}
