package com.jain.yourownbank.screens.transaction

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.ReceiverDetails
import com.jain.yourownbank.models.transaction.SenderDetails
import com.jain.yourownbank.models.transaction.TransactionHistory
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.utils.ShowKeyValuePair

@Composable
fun TransactionDetails(
    navController: NavController
) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val clickedTransaction = savedStateHandle?.get<TransactionHistory>("clickedTransaction")
    Log.i("Ujjwal", "TransactionDetails: clickedTransactionDuration = ${clickedTransaction?.transactionId}")

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SetTransactionStatusAnimation(transactionStatus = clickedTransaction?.transactionStatus)
            if (clickedTransaction?.creditDebitStatus == "Credit") {
                SetHeader(heading = "Sender Details")
                SetSenderDetails(
                    modeOfTransaction = clickedTransaction.modeOfTransaction,
                    senderDetails = clickedTransaction.senderDetails
                )
            }
            if (clickedTransaction?.creditDebitStatus == "Debit") {
                SetHeader(heading = "Receiver Details")
                SetReceiverDetails(
                    modeOfTransaction = clickedTransaction.modeOfTransaction,
                    receiverDetails = clickedTransaction.receiverDetails
                )
            }
            SetHeader(heading = "Transaction Details")
            SetOtherDetailsAboutTransaction(clickedTransaction = clickedTransaction)
        }

    }
}

@Composable
fun SetOtherDetailsAboutTransaction(
    clickedTransaction: TransactionHistory?
) {
    ShowKeyValuePair(key = "Txn Amount", value = clickedTransaction?.amount?.toString() ?: "")
    ShowKeyValuePair(key = "Txn Id", value = clickedTransaction?.transactionId ?: "")
    ShowKeyValuePair(key = "Txn Type", value = clickedTransaction?.creditDebitStatus ?: "")
    ShowKeyValuePair(key = "Txn Mode", value = clickedTransaction?.modeOfTransaction ?: "")
    ShowKeyValuePair(key = "Txn Status", value = clickedTransaction?.transactionStatus ?: "")
    ShowKeyValuePair(key = "Txn Duration", value = clickedTransaction?.transactionTime ?: "")
}

@Composable
fun SetReceiverDetails(
    modeOfTransaction: String?,
    receiverDetails: ReceiverDetails?
) {
    ShowKeyValuePair(key = "Name", value = receiverDetails?.receiverName ?: "")
    if (modeOfTransaction == "UPI") {
        ShowKeyValuePair(key = "UPI Id", value = receiverDetails?.receiverUpiDetails?.upiId ?: "")
    } else {
        ShowKeyValuePair(key = "Bank Account No.", value = receiverDetails?.receiverBankAccountDetails?.accountNumber ?: "")
        ShowKeyValuePair(key = "IFSC Code", value = receiverDetails?.receiverBankAccountDetails?.ifscCode ?: "")
    }
}

@Composable
fun SetSenderDetails(
    modeOfTransaction: String?,
    senderDetails: SenderDetails?
) {
    ShowKeyValuePair(key = "Name", value = senderDetails?.senderName ?: "")
    if (modeOfTransaction == "UPI") {
        ShowKeyValuePair(key = "UPI Id", value = senderDetails?.senderUpiDetails?.upiId ?: "")
    } else {
        ShowKeyValuePair(key = "Bank Account No.", value = senderDetails?.senderBankAccountDetails?.accountNumber ?: "")
        ShowKeyValuePair(key = "IFSC Code", value = senderDetails?.senderBankAccountDetails?.ifscCode ?: "")
    }
}

@Composable
fun SetTransactionStatusAnimation(transactionStatus: String?) {
    val animationRes = if (transactionStatus == "Success")
        R.raw.payment_success_anim else R.raw.payment_failed_anim

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}
