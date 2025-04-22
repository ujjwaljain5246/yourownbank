package com.jain.yourownbank.repositories

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jain.yourownbank.models.CommonModel
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.auth.User
import com.jain.yourownbank.models.auth.UserAccountDetails
import com.jain.yourownbank.models.auth.UserContactDetails
import com.jain.yourownbank.models.auth.UserPersonalDetails
import com.jain.yourownbank.models.auth.UserSecurityDetails
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import com.jain.yourownbank.webServices.WebServiceConnector
import com.jain.yourownbank.webServices.RepositoryDataReceivedListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginRepository(viewModelDataReceivedListener: ViewModelDataReceivedListener) : RepositoryDataReceivedListener {

    private var viewModelDataReceivedListener: ViewModelDataReceivedListener

    init {
        this.viewModelDataReceivedListener = viewModelDataReceivedListener
    }

    fun signInUserRepository(userName: String, password: String, path: String, requestCode: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("userName", userName)
        jsonObject.addProperty("password", password)
        WebServiceConnector.signInUserWebServiceConnector(
            repositoryDataReceivedListener = this,
            requestCode = requestCode,
            path = path,
            requestBody = jsonObject
            )
    }

    fun deleteUserRepository(userName: String, password: String, path: String, requestCode: String) {
        Log.i("Ujjwal", "Delete api called from repository ")
        val jsonObject = JsonObject()
        jsonObject.addProperty("userName", userName)
        jsonObject.addProperty("password", password)
        WebServiceConnector.deleteUserWebServiceConnector(
            repositoryDataReceivedListener = this,
            requestCode = requestCode,
            path = path,
            requestBody = jsonObject
        )
    }

    fun signUpUserRepository(
        userPersonalDetails: UserPersonalDetails,
        userContactDetails: UserContactDetails,
        userAccountDetails: UserAccountDetails,
        userSecurityDetails: UserSecurityDetails,
        path: String,
        requestCode: String
    ) {
        val jsonObject = JsonObject()
        val userPersonalDetailsJson = Gson().toJsonTree(userPersonalDetails)
        jsonObject.add("userPersonalDetails", userPersonalDetailsJson)
        val userContactDetailsJson = Gson().toJsonTree(userContactDetails)
        jsonObject.add("userContactDetails", userContactDetailsJson)
        val userAccountDetailsJson = Gson().toJsonTree(userAccountDetails)
        jsonObject.add("userAccountDetails", userAccountDetailsJson)
        val userSecurityDetailsJson = Gson().toJsonTree(userSecurityDetails)
        jsonObject.add("userSecurityDetails", userSecurityDetailsJson)
        WebServiceConnector.signUpUserWebServiceConnector(
            repositoryDataReceivedListener = this,
            requestCode = requestCode,
            path = path,
            requestBody = jsonObject
        )
    }

    override fun onWebServiceSuccess(response: String?, requestCode: String) {
        GlobalScope.launch {
            if (requestCode == RequestCodes.SIGN_IN) {
                val loginDataResponse = Gson().fromJson(response, LoginDataModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(loginDataResponse, requestCode = requestCode)
                }
            } else if (requestCode == RequestCodes.SIGN_UP) {
                val signUpDataResponse = Gson().fromJson(response, LoginDataModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(signUpDataResponse, requestCode = requestCode)
                }
            } else if (requestCode == RequestCodes.DELETE_USER) {
                val deleteUserResponse = Gson().fromJson(response, CommonModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(deleteUserResponse, requestCode = requestCode)
                }
            }
        }
    }

    override fun onWebServiceFailed(
        message: String?,
        responseCode: Int,
        requestCode: String,
        url: String
    ) {
        viewModelDataReceivedListener.dataReceivedInRepositoryFailed(
            message = message,
            responseCode = responseCode,
            requestCode = requestCode
        )
    }
}