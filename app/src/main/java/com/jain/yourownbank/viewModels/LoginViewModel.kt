package com.jain.yourownbank.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jain.yourownbank.models.CommonModel
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.auth.User
import com.jain.yourownbank.models.auth.UserAccountDetails
import com.jain.yourownbank.models.auth.UserContactDetails
import com.jain.yourownbank.models.auth.UserPersonalDetails
import com.jain.yourownbank.models.auth.UserSecurityDetails
import com.jain.yourownbank.repositories.LoginRepository
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.ApiError
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.log

class LoginViewModel() : ViewModel(), ViewModelDataReceivedListener {

    private var loginRepository: LoginRepository = LoginRepository(this)

    private var isLoading = MutableLiveData(false)

    private var loginSuccessLiveData = MutableLiveData<LoginDataModel>()

    private var signUpSuccessLiveData = MutableLiveData<LoginDataModel>()

    private var deleteUserSuccessLiveData = MutableLiveData<CommonModel>()

    private var loginFailedLivedDataMessage = MutableStateFlow(ApiError.ApiFailedResponse())

    private var signUpApiFailedLiveDataResponse = MutableStateFlow(ApiError.SignUpApiFailedResponse())

    private var deleteUserFailedLiveData = MutableStateFlow(ApiError.ApiFailedResponse())

    fun isLoadingLiveData() = isLoading
    fun getLoginSuccessLiveData() = loginSuccessLiveData
    fun getSighUpSuccessLiveData() = signUpSuccessLiveData
    fun getDeleteUserSuccessLiveData() = deleteUserSuccessLiveData
    fun getLoginFailedLivedDataMessage():StateFlow<ApiError.ApiFailedResponse> = loginFailedLivedDataMessage
    fun getSignUpFailedLiveDataResponse() = signUpApiFailedLiveDataResponse
    fun getDeleteUserFailedLiveData() = deleteUserFailedLiveData

    fun signInUserViewModel(userName: String, password: String, path: String, requestCode: String) {
        loginRepository.signInUserRepository(
            userName = userName,
            password = password,
            path = path,
            requestCode = requestCode
        )
    }

    fun signUpUserViewModel(
        userPersonalDetails: UserPersonalDetails,
        userContactDetails: UserContactDetails,
        userAccountDetails: UserAccountDetails,
        userSecurityDetails: UserSecurityDetails,
        path: String,
        requestCode: String
    ) {
        loginRepository.signUpUserRepository(
            userPersonalDetails = userPersonalDetails,
            userContactDetails = userContactDetails,
            userAccountDetails = userAccountDetails,
            userSecurityDetails = userSecurityDetails,
            path = path,
            requestCode = requestCode
        )
    }

    fun deleteUserViewModel(userName: String, password: String, path: String, requestCode: String) {
        Log.i("Ujjwal", "Delete api called from view model ")
        loginRepository.deleteUserRepository(
            userName = userName,
            password = password,
            path = path,
            requestCode = requestCode
        )
    }

    override fun dataReceivedInRepositorySuccess(commonModel: CommonModel, requestCode: String) {
        if (requestCode == RequestCodes.SIGN_IN) {
            val loginDataModel = commonModel as LoginDataModel
            loginSuccessLiveData.postValue(loginDataModel)
        } else if (requestCode == RequestCodes.SIGN_UP) {
            val signUpModel = commonModel as LoginDataModel
            signUpSuccessLiveData.postValue(signUpModel)
        } else if (requestCode == RequestCodes.DELETE_USER) {
            val deleteUserModel = commonModel
            deleteUserSuccessLiveData.postValue(deleteUserModel)
        }
    }

    override fun dataReceivedInRepositoryFailed(
        message: String?,
        requestCode: String,
        responseCode: Int
    ) {
        if (requestCode == RequestCodes.SIGN_IN) {
            val errorData = Gson().fromJson(message, ApiError.ApiFailedResponse::class.java)
            loginFailedLivedDataMessage.value = errorData
        }
        if (requestCode == RequestCodes.SIGN_UP) {
            val errorData = Gson().fromJson(message, ApiError.SignUpApiFailedResponse::class.java)
            Log.i("signUpResponseLoginViewModel", "dataReceivedInRepositoryFailed: errorData = $message")
            signUpApiFailedLiveDataResponse.value = errorData
        }
        if (requestCode == RequestCodes.DELETE_USER) {
            val errorData = Gson().fromJson(message, ApiError.ApiFailedResponse::class.java)
            deleteUserFailedLiveData.value = errorData
        }
    }

    fun setLoadingFalse() {
        isLoading.value = false
    }

    fun setLoadingTrue() {
        isLoading.value = true
    }

    fun setApiFailedResponseToEmpty() {
        loginFailedLivedDataMessage.value = ApiError.ApiFailedResponse()
        signUpApiFailedLiveDataResponse.value = ApiError.SignUpApiFailedResponse()
        deleteUserFailedLiveData.value = ApiError.ApiFailedResponse()
    }


}