package com.jain.yourownbank.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jain.yourownbank.models.CommonModel
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.transaction.TransactionDetails
import com.jain.yourownbank.models.transaction.TransactionHistoryModel
import com.jain.yourownbank.models.transaction.TransactionSuccessModel
import com.jain.yourownbank.repositories.TransactionRepository
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.ApiError
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import kotlinx.coroutines.flow.MutableStateFlow

class TransactionViewModel() : ViewModel(), ViewModelDataReceivedListener {

    private val transactionRepository: TransactionRepository = TransactionRepository(this)

    private var isLoading = MutableLiveData(false)

    private var transactionHistorySuccess = MutableLiveData<TransactionHistoryModel>()

    private var transactionDetailsSuccess = MutableLiveData<TransactionSuccessModel>()

    private var apiFailedResponse = MutableStateFlow(ApiError.ApiFailedResponse())

    fun getTransactionHistoryLiveData() = transactionHistorySuccess

    fun getTransactionDetailsLiveData() = transactionDetailsSuccess

    fun getApiFailedResponseLiveData() = apiFailedResponse

    fun getIsLoadingLiveData() = isLoading

    fun getUserTransactionHistoryListViewModel(path: String, requestCode: String) {
        transactionRepository.getUserTransactionHistoryListRepository(
            path = path,
            requestCode = requestCode
        )
    }

    fun sendMoneyViewModel(path: String, requestCode: String, modeOfTransaction: String, bankAccountNumber: String, ifscCode: String, upiId: String, amount: Int) {
        transactionRepository.sendMoneyRepository(
            path = path,
            modeOfTransaction = modeOfTransaction,
            bankAccountNumber = bankAccountNumber,
            ifscCode = ifscCode,
            upiId = upiId,
            amount = amount,
            requestCode = requestCode
        )
    }

    override fun dataReceivedInRepositorySuccess(commonModel: CommonModel, requestCode: String) {
        if (requestCode == RequestCodes.USER_TRANSACTION_HISTORY) {
            val userTransactionHistory = commonModel as TransactionHistoryModel
            transactionHistorySuccess.postValue(userTransactionHistory)
        }
        if (requestCode == RequestCodes.SEND_MONEY) {
            val transactionDetails = commonModel as TransactionSuccessModel
            transactionDetailsSuccess.postValue(transactionDetails)
        }
    }

    override fun dataReceivedInRepositoryFailed(
        message: String?,
        requestCode: String,
        responseCode: Int
    ) {
        val errorData = Gson().fromJson(message, ApiError.ApiFailedResponse::class.java)
        apiFailedResponse.value = errorData
    }

    fun setIsLoadingTrue() {
        Log.i("ServiceScreen", "setIsLoadingTrue")
        isLoading.value = true
    }

    fun setIsLoadingFalse() {
        Log.i("ServiceScreen", "setIsLoadingFalse")
        isLoading.value = false
    }

    fun setTransactionDetailsToEmpty() {
        transactionDetailsSuccess.value = TransactionSuccessModel()
    }

    fun setTransactionHistoryToEmpty() {
        transactionHistorySuccess.value = TransactionHistoryModel()
    }

    fun setApiFailedResponseToEmpty() {
        apiFailedResponse.value = ApiError.ApiFailedResponse()
    }
}