package com.boradincer.moviesapp.data.network

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult <out T> (val status: ApiStatus, val data: T?, val message:String?, val code: Int? = 200) {

    data class Success<out R>(val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String, val errorCode: Int): ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception,
        code = errorCode
    )

    data class Loading<out R>(val _data: R?, val isLoading: Boolean): ApiResult<R>(
        status = ApiStatus.LOADING,
        data = _data,
        message = null,
        code = 0
    )
}