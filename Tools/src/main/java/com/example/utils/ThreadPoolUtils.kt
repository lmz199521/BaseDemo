package com.example.Tools

import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * describe: 线程池
 * Date:2024/12/12
 * Author:lmz
 */
class ThreadPoolUtils {

    companion object {
        val instance by lazy { ThreadPoolUtils() }
    }

    private var mThreadNumber: AtomicInteger? = null

    init {
        mThreadNumber = AtomicInteger()
    }


    private val mThreadFactory: ThreadFactory = ThreadFactory { r ->
        val thread = Thread(r)
        thread.name = "线程:[" + (mThreadNumber?.incrementAndGet() ?: 0) + "]"
        thread
    }

    private val mThreadPool =
        ThreadPoolExecutor(2, 5, 0L, TimeUnit.MICROSECONDS, LinkedBlockingDeque(), mThreadFactory)


    fun submitTask(run: Runnable) {
        try {
            val result: Future<*> = mThreadPool.submit(run)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun executeTask(run: Runnable) {
        try {
            mThreadPool.execute(run)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}