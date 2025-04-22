package com.jain.yourownbank.webServices

sealed class NetworkResult<out T> {

    data class Success<out T>(val data: T) : NetworkResult<T>()

    data class Error(val apiErrorObj : ApiError) : NetworkResult<Nothing>()

    data class Loading(val isInitial: Boolean = false) : NetworkResult<Nothing>()

    object Idle : NetworkResult<Nothing>()
}