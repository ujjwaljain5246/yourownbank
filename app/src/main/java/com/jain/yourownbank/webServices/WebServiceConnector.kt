package com.jain.yourownbank.webServices

import android.util.Log
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

object WebServiceConnector {

    private var mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun signInUserWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String, requestBody: JsonObject) {
        val requestJsonObject = RequestBody.create(mediaType, requestBody.toString())
        val services = ApiClient.createServiceWithOutToken()
        services.signInUserApiServices(path = path, requestBody = requestJsonObject).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun signUpUserWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String, requestBody: JsonObject) {
        val requestJsonObject = RequestBody.create(mediaType, requestBody.toString())
        val services = ApiClient.createServiceWithOutToken()
        services.signUpUserApiServices(path = path, requestBody = requestJsonObject).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun getUserBankDetailsWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String) {
        val services = ApiClient.createServiceWithToken()
        services.getUserBankDetailsApiServices(path = path).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun getUserAvailableBalanceWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String) {
        val services = ApiClient.createServiceWithToken()
        services.getUserAvailableBalanceApiServices(path = path).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun getUserTransactionHistoryListWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String) {
        val services = ApiClient.createServiceWithToken()
        services.getUserTransactionHistoryApiServices(path = path).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun sendMoneyWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String, requestBody: JsonObject) {
        val requestJsonObject = RequestBody.create(mediaType, requestBody.toString())
        val services = ApiClient.createServiceWithToken()
        services.sendMoneyApiServices(path = path, requestBody = requestJsonObject).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

    fun deleteUserWebServiceConnector(repositoryDataReceivedListener: RepositoryDataReceivedListener, requestCode: String, path: String, requestBody: JsonObject) {
        Log.i("Ujjwal", "Delete api called from web service connector, path = $path, requestBody = ${requestBody},")
        val requestJsonObject = RequestBody.create(mediaType, requestBody.toString())
        val services = ApiClient.createServiceWithToken()
        services.deleteUserApiServices(path = path, requestBody = requestJsonObject).enqueue(APICallback(requestCode = requestCode, repositoryDataReceivedListener = repositoryDataReceivedListener))
    }

}