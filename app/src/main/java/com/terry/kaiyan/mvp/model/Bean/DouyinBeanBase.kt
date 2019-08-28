package com.terry.kaiyan.mvp.model.Bean

/**
 * Author:ChenXinming
 * Date:2019/08/27
 * Description:
 */
data class DouyinBeanBase(val Result:ResultBean?, val Code:Int){

    data class ResultBean(val count:Int, val data:List<DouyinBean>?){
        data class DouyinBean(val Id:Int,val Title:String, val Video:String?, val VideoImg:String?, val CreateTime:String)
    }
}
