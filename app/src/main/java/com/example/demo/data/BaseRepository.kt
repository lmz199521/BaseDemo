package com.example.demo.data


/**
 * describe:
 * Date:2024/12/16
 * Author:lmz
 */
open class BaseRepository {
    // TODO:  这个类需要做网络数据的获取

     companion object{
         val apiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED ) {

         }
     }
}