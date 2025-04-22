package com.jain.yourownbank.webServices

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jain.yourownbank.models.auth.LoginDataModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @POST("{signInPath}")
    fun signInUserApiServices(@Path(value = "signInPath", encoded = true) path: String, @Body requestBody : RequestBody) : Call<ResponseBody>

    @POST("{signUpPath}")
    fun signUpUserApiServices(@Path(value = "signUpPath", encoded = true) path: String, @Body requestBody : RequestBody) : Call<ResponseBody>

    @GET("{bankDetailsPath}")
    fun getUserBankDetailsApiServices(@Path(value = "bankDetailsPath", encoded = true) path: String, ) : Call<ResponseBody>

    @GET("{fetchAvailableBalancePath}")
    fun getUserAvailableBalanceApiServices(@Path(value = "fetchAvailableBalancePath", encoded = true) path: String) : Call<ResponseBody>

    @GET("{transactionHistoryPath}")
    fun getUserTransactionHistoryApiServices(@Path(value = "transactionHistoryPath", encoded = true) path: String) : Call<ResponseBody>

    @POST("{sendMoneyPath}")
    fun sendMoneyApiServices(@Path(value = "sendMoneyPath", encoded = true) path: String, @Body requestBody: RequestBody) : Call<ResponseBody>

    @HTTP(method = "DELETE", path = "{deleteUserPath}", hasBody = true)
    fun deleteUserApiServices(@Path(value = "deleteUserPath", encoded = true) path: String, @Body requestBody: RequestBody) : Call<ResponseBody>
}