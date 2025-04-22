package com.jain.yourownbank.webServices

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class APICallback(
    private val requestCode: String,
    private val repositoryDataReceivedListener: RepositoryDataReceivedListener
) : Callback<ResponseBody> {

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        validateResponse(call, response, repositoryDataReceivedListener, requestCode)
    }

    override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
        validateError(throwable, repositoryDataReceivedListener, requestCode, call.request().url.toString())
    }

    private fun validateError(throwable: Throwable, repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, url: String) {
        repositoryDataReceivedListener.onWebServiceFailed(
            message = throwable.message,
            requestCode = requestCode,
            responseCode = 99,
            url = url
        )
    }

    private fun validateResponse(call: Call<ResponseBody>, response:  Response<ResponseBody>, repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String) {
        try {
            val responseBody = response.body()
            val errorBody = response.errorBody()?.string()
            if (response.isSuccessful) {
                if (responseBody != null) {
                    val responseString = responseBody.string()
                    repositoryDataReceivedListener.onWebServiceSuccess(
                        response = responseString,
                        requestCode = requestCode
                    )
                } else {
                    repositoryDataReceivedListener.onWebServiceSuccess(
                        response = "",
                        requestCode = requestCode
                    )
                }
            } else {
                repositoryDataReceivedListener.onWebServiceFailed(
                    message = errorBody.toString(),
                    responseCode = response.code(),
                    requestCode = requestCode,
                    url = call.request().url.toString()
                )
            }
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }
}