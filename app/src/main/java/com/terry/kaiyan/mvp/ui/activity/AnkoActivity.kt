package com.terry.kaiyan.mvp.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 * Author:ChenXinming
 * Date:2019/07/10
 * Description:
 */
class AnkoActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main()
    }

    fun main() = runBlocking {
        var job = GlobalScope.launch(Dispatchers.Main){
            delay(1000)
            var content  = fetchData()
            Log.i("cxm","FetchData $content")
        }
        job.join()
    }

    private suspend fun fetchData() :String{
        return "GETLI@!@!@!@"
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

}
