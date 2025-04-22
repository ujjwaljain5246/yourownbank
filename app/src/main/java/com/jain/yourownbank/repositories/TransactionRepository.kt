package com.jain.yourownbank.repositories

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.transaction.TransactionHistory
import com.jain.yourownbank.models.transaction.TransactionHistoryModel
import com.jain.yourownbank.models.transaction.TransactionSuccessModel
import com.jain.yourownbank.models.transaction.UserBankAccountDetails
import com.jain.yourownbank.models.transaction.UserUpiDetails
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.webServices.RepositoryDataReceivedListener
import com.jain.yourownbank.webServices.ViewModelDataReceivedListener
import com.jain.yourownbank.webServices.WebServiceConnector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionRepository(viewModelDataReceivedListener: ViewModelDataReceivedListener) : RepositoryDataReceivedListener {


    private var viewModelDataReceivedListener: ViewModelDataReceivedListener

    init {
        this.viewModelDataReceivedListener = viewModelDataReceivedListener
    }

    fun getUserTransactionHistoryListRepository(path: String, requestCode: String) {
        WebServiceConnector.getUserTransactionHistoryListWebServiceConnector(
            repositoryDataReceivedListener = this,
            path = path,
            requestCode = requestCode
        )
    }

    fun sendMoneyRepository(path: String, requestCode: String, modeOfTransaction: String, upiId: String, bankAccountNumber: String, ifscCode: String, amount: Int) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("modeOfTransaction", modeOfTransaction)
        Log.i("Ujjwal", "sendMoneyRepository: upiId = $upiId")
        if (modeOfTransaction == "UPI") {
            val userUpiDetails = UserUpiDetails(upiId = upiId)
            val userUpiDetailsJson = Gson().toJsonTree(userUpiDetails)
            jsonObject.add("userUpiDetails", userUpiDetailsJson)
        } else {
            val userBankAccountDetails = UserBankAccountDetails(accountNumber = bankAccountNumber, ifscCode = ifscCode)
            val userBankAccountDetailsJson = Gson().toJsonTree(userBankAccountDetails)
            jsonObject.add("userBankAccountDetails", userBankAccountDetailsJson)
        }
        jsonObject.addProperty("amount", amount)
        WebServiceConnector.sendMoneyWebServiceConnector(
            path = path,
            requestCode = requestCode,
            requestBody = jsonObject,
            repositoryDataReceivedListener = this
        )
    }


    override fun onWebServiceSuccess(response: String?, requestCode: String) {
        GlobalScope.launch {
            if (requestCode == RequestCodes.USER_TRANSACTION_HISTORY) {
                val transactionHistory = Gson().fromJson(response, TransactionHistoryModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(transactionHistory, requestCode = requestCode)
                }
            }
            if (requestCode == RequestCodes.SEND_MONEY) {
                val transactionResponse = Gson().fromJson(response, TransactionSuccessModel::class.java)
                launch {
                    viewModelDataReceivedListener.dataReceivedInRepositorySuccess(transactionResponse, requestCode = requestCode)
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