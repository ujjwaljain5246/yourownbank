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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.gson.Gson
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.TransactionDetails
import com.jain.yourownbank.models.transaction.TransactionSuccessModel
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically

@Composable
fun TransactionSuccessScreen(
    navController: NavController
) {
    val dummyRes = "{\"code\":200,\"message\":\"Moneytransferredsuccessfully\",\"transactionDetails\":{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":10,\"transactionId\":\"TXN-CF2126BF034E84CC\",\"transactionStatus\":\"Success\",\"transactiionTime\":\"17Feb,2025,07:19:27:203\"}}"
//    val successModel = Gson().fromJson(dummyRes, TransactionSuccessModel::class.java)
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val transactionDetails = savedStateHandle?.get<TransactionDetails>("transactionDetails") ?: TransactionDetails()
    Log.i("Ujjwal", "TransactionStatusScreen: transactionDetails = $transactionDetails")
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
            ShowSuccessOrFailureAnimation(transactionStatus = transactionDetails.transactionStatus)
            SetSpaceHorizontallyOrVertically(height = true, value = 16.dp)
            SetTransactionSuccessfulText(transactionDetails = transactionDetails)
            SetSpaceHorizontallyOrVertically(height = true, value = 16.dp)
            TransactionDetailsCard(transactionDetails = transactionDetails)
        }
    }
}

@Composable
fun ShowSuccessOrFailureAnimation(transactionStatus: String) {
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

@Composable
fun TransactionDetailsCard(transactionDetails: TransactionDetails) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SetTransactionIdTextIntoTransactionDetailsCard(transactionDetails = transactionDetails)
            SetTransactionAmountTextIntoTransactionDetailsCard(transactionDetails = transactionDetails)
            SetTransactionModeTextIntoTransactionDetailsCard(transactionDetails = transactionDetails)
            SetSenderDetailsIntoTransactionDetailsCard(transactionDetails = transactionDetails)
            SetReceiverDetailsIntoTransactionDetailsCard(transactionDetails = transactionDetails)
            SetTransactionDateAndTimeIntoTransactionDetailsCard(transactionDetails = transactionDetails)
        }
    }
}

@Composable
fun SetTransactionDateAndTimeIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    Text(
        text = "Date: ${transactionDetails.transactiionTime}"
    )
}

@Composable
fun SetReceiverDetailsIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    if (transactionDetails.modeOfTransaction == "UPI") {
        SetReceiverDetailsIntoTransactionDetailsCardForUpiMode(transactionDetails = transactionDetails)
    } else {
        SetReceiverDetailsIntoTransactionDetailsCardForBankTransferMode(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetReceiverDetailsIntoTransactionDetailsCardForUpiMode(transactionDetails: TransactionDetails) {
    Column {
        SetReceiverNameText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetReceiverUpiText(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetReceiverUpiText(transactionDetails: TransactionDetails) {
    Text(
        text = "Receiver's UPI Id: ${transactionDetails.receiverDetails.receiverUpiDetails.upiId}"
    )
}

@Composable
fun SetReceiverDetailsIntoTransactionDetailsCardForBankTransferMode(transactionDetails: TransactionDetails) {
    Column {
        SetReceiverNameText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetReceiverBankAccountNumberText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetReceiverIfscCodeText(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetReceiverIfscCodeText(transactionDetails: TransactionDetails) {
    Text(
        text = "Receiver's IFSC Code: ${transactionDetails.receiverDetails.receiverBankAccountDetails.ifscCode}"
    )
}

@Composable
fun SetReceiverBankAccountNumberText(transactionDetails: TransactionDetails) {
    Text(
        text = "Receiver's Account No.: ${transactionDetails.receiverDetails.receiverBankAccountDetails.accountNumber}"
    )
}

@Composable
fun SetReceiverNameText(transactionDetails: TransactionDetails) {
    Text(
        text = "Receiver's Name: ${transactionDetails.receiverDetails.receiverName}"
    )
}

@Composable
fun SetSenderDetailsIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    if (transactionDetails.modeOfTransaction == "UPI") {
        SetSenderDetailsIntoTransactionDetailsCardForUpiMode(transactionDetails = transactionDetails)
    } else {
        SetSenderDetailsIntoTransactionDetailsCardForBankTransferMode(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetTransactionModeTextIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    Text(
        text = "Mode: ${transactionDetails.modeOfTransaction}"
    )
}

@Composable
fun SetTransactionAmountTextIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    Text(
        text = "Amount: ₹${transactionDetails.amount}"
    )
}

@Composable
fun SetTransactionSuccessfulText(transactionDetails: TransactionDetails) {
    Text(
        text = if (transactionDetails.transactionStatus == "Success")
            "Transaction Successful" else "Transaction Failed",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = if (transactionDetails.transactionStatus == "Success") Color.Green else Color.Red
    )
}

@Composable
fun SetTransactionIdTextIntoTransactionDetailsCard(transactionDetails: TransactionDetails) {
    Text(
        text = "Transaction ID: ${transactionDetails.transactionId}",
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SetSenderDetailsIntoTransactionDetailsCardForUpiMode(transactionDetails: TransactionDetails) {
    Column {
        SetSenderNameText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetSenderUpiText(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetSenderUpiText(transactionDetails: TransactionDetails) {
    Text(
        text = "Sender's UPI Id: ${transactionDetails.senderDetails.senderUpiDetails.upiId}"
    )
}

@Composable
fun SetSenderNameText(transactionDetails: TransactionDetails) {
    Text(
        text = "Sender's Name: ${transactionDetails.senderDetails.senderName}"
    )
}

@Composable
fun SetSenderDetailsIntoTransactionDetailsCardForBankTransferMode(transactionDetails: TransactionDetails) {
    Column {
        SetSenderNameText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetSenderAccountNumberText(transactionDetails = transactionDetails)
        SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)
        SetSenderIfscCodeText(transactionDetails = transactionDetails)
    }
}

@Composable
fun SetSenderIfscCodeText(transactionDetails: TransactionDetails) {
    Text(
        text = "Sender's IFSC Code: ${transactionDetails.senderDetails.senderBankAccountDetails.ifscCode}"
    )
}

@Composable
fun SetSenderAccountNumberText(transactionDetails: TransactionDetails) {
    Text(
        text = "Sender's Account No.: ${transactionDetails.senderDetails.senderBankAccountDetails.accountNumber}"
    )
}
