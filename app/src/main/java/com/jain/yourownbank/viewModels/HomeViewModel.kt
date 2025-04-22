package com.jain.yourownbank.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jain.yourownbank.models.CommonModel
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.transaction.AvailableBalanceModel
import com.jain.yourownbank.models.transaction.UserBankDetailsModel
import com.jain.yourownbank.repositories.HomeRepository
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.ApiError
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(): ViewModel(), ViewModelDataReceivedListener {

    private val homeRepository: HomeRepository = HomeRepository(this)

    private var isLoading = MutableLiveData<Boolean>(true)

    private var successfullyFetchedUserBankDetailsLiveData = MutableLiveData<UserBankDetailsModel>()

    private var apiFailedResponse = MutableStateFlow(ApiError.ApiFailedResponse())

    private var successfullyFetchedUserAvailableBalance = MutableLiveData<AvailableBalanceModel>()



    fun isLoadingLiveData() = isLoading

    fun successfullyFetchedUserBankDetailsLiveDataExposed() = successfullyFetchedUserBankDetailsLiveData

    fun successfullyFetchedUserAvailableBalanceLiveDataExposed() = successfullyFetchedUserAvailableBalance

    fun apiFailedResponseStateFlowExposed() = apiFailedResponse


    fun getUserBankDetailsViewModel(path: String, requestCode: String) {
        homeRepository.getUserBankDetailsRepository(
            path = path,
            requestCode = requestCode
        )
    }

    fun getUserAvailableBalanceViewModel(path: String, requestCode: String) {
        homeRepository.getUserAvailableBalanceRepository(
            path = path,
            requestCode = requestCode
        )
    }


    override fun dataReceivedInRepositorySuccess(commonModel: CommonModel, requestCode: String) {
        if (requestCode == RequestCodes.USER_BANK_DETAILS) {
            val userBankDetails = commonModel as UserBankDetailsModel
            successfullyFetchedUserBankDetailsLiveData.postValue(userBankDetails)
        }
        if (requestCode == RequestCodes.USER_AVAILABLE_BALANCE) {
            val userAvailableBalance = commonModel as AvailableBalanceModel
            successfullyFetchedUserAvailableBalance.postValue(userAvailableBalance)
        }
    }

    override fun dataReceivedInRepositoryFailed(
        message: String?,
        requestCode: String,
        responseCode: Int
    ) {
        if (requestCode == RequestCodes.USER_BANK_DETAILS) {
            val errorData = Gson().fromJson(message, ApiError.ApiFailedResponse::class.java)
            apiFailedResponse.value = errorData
        }
    }

    fun setIsLoadingTrue() {
        isLoading.value = true
    }

    fun setIsLoadingFalse() {
        isLoading.value = false
    }

    fun setUserAvailableBalanceToEmpty() {
        successfullyFetchedUserAvailableBalance.value = AvailableBalanceModel()
    }

    fun setApiFailedResponseToEmpty() {
        apiFailedResponse.value = ApiError.ApiFailedResponse()
    }
}