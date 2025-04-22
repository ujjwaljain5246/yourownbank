package com.jain.yourownbank.repositories

import com.google.gson.Gson
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.transaction.AvailableBalanceModel
import com.jain.yourownbank.models.transaction.UserBankDetailsModel
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.RepositoryDataReceivedListener
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import com.jain.yourownbank.webServices.WebServiceConnector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeRepository(viewModelDataReceivedListener: ViewModelDataReceivedListener) : RepositoryDataReceivedListener {


    private var viewModelDataReceivedListener: ViewModelDataReceivedListener

    init {
        this.viewModelDataReceivedListener = viewModelDataReceivedListener
    }

    fun getUserBankDetailsRepository(path: String, requestCode: String) {
        WebServiceConnector.getUserBankDetailsWebServiceConnector(
            repositoryDataReceivedListener = this,
            requestCode = requestCode,
            path = path
        )
    }

    fun getUserAvailableBalanceRepository(path: String, requestCode: String) {
        WebServiceConnector.getUserAvailableBalanceWebServiceConnector(
            repositoryDataReceivedListener = this,
            requestCode = requestCode,
            path = path
        )
    }

    override fun onWebServiceSuccess(response: String?, requestCode: String) {
        GlobalScope.launch {
            if (requestCode == RequestCodes.USER_BANK_DETAILS) {
                val userBankDetails = Gson().fromJson(response, UserBankDetailsModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(userBankDetails, requestCode = requestCode)
                }
            }
            if (requestCode == RequestCodes.USER_AVAILABLE_BALANCE) {
                val userAvailableBalance = Gson().fromJson(response, AvailableBalanceModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(userAvailableBalance, requestCode = requestCode)
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