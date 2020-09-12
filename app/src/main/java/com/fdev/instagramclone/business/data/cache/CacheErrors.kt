package com.fdev.instagramclone.business.data.cache

object CacheErrors{

    //When some unknown error happen with the cache operation
    const val CACHE_ERROR_UNKNOWN = "Unknown cache Error."

    //When something error with the cache operation
    const val CACHE_ERROR_ = "Cache Error."

    //When trying to do cache operation but pass the cache limit time
    const val CACHE_ERROR_TIMEOUT = "Cache TimeOut."

    //When trying to get data from the cache but it return null
    const val CACHE_DATA_NULL = "Cache data null"

}