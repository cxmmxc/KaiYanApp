package com.terry.kaiyan.app

import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack

/**
 * Author:ChenXinming
 * Date:2019/07/05
 * Description:
 */
interface GSYVideoListener : VideoAllCallBack {
    override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onEnterFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onClickResume(url: String?, vararg objects: Any?) {

    }

    override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onStartPrepared(url: String?, vararg objects: Any?) {

    }

    override fun onClickStartIcon(url: String?, vararg objects: Any?) {

    }

    override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {

    }

    override fun onQuitFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onClickStartThumb(url: String?, vararg objects: Any?) {

    }

    override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {

    }

    override fun onClickStartError(url: String?, vararg objects: Any?) {

    }

    override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {

    }

    override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {

    }

    override fun onClickBlank(url: String?, vararg objects: Any?) {

    }

    override fun onClickStop(url: String?, vararg objects: Any?) {

    }

    override fun onClickSeekbar(url: String?, vararg objects: Any?) {

    }

    override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {

    }

    override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {

    }

    override fun onPrepared(url: String?, vararg objects: Any?) {
    }

    override fun onAutoComplete(url: String?, vararg objects: Any?) {

    }

    override fun onPlayError(url: String?, vararg objects: Any?) {
    }
}
