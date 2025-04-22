package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import androidx.compose.ui.layout.ParentDataModifier
import com.jain.yourownbank.models.CommonModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionHistoryModel(
    val transactionHistory: List<TransactionHistory> = emptyList()
) : CommonModel(), Parcelable

@Parcelize
data class TransactionHistory(
    val amount: Int,
    val creditDebitStatus: String,
    val modeOfTransaction: String,
    val receiverDetails: ReceiverDetails,
    val senderDetails: SenderDetails,
    val transactionId: String,
    val transactionStatus: String,
    val transactionTime: String,
    val userName: String
) : Parcelable

@Parcelize
data class ReceiverBankAccountDetails(
    val accountNumber: String = "",
    val ifscCode: String = ""
) : Parcelable

@Parcelize
data class ReceiverDetails(
    val receiverBankAccountDetails: ReceiverBankAccountDetails = ReceiverBankAccountDetails(),
    val receiverCustomerId: String = "",
    val receiverName: String = "",
    val receiverUpiDetails: ReceiverUpiDetails = ReceiverUpiDetails()
) : Parcelable

@Parcelize
data class ReceiverUpiDetails(
    val upiId: String = ""
) : Parcelable

@Parcelize
data class SenderDetails(
    val senderBankAccountDetails: SenderBankAccountDetails = SenderBankAccountDetails(),
    val senderCustomerId: String = "",
    val senderName: String = "",
    val senderUpiDetails: SenderUpiDetails = SenderUpiDetails()
) : Parcelable

@Parcelize
data class SenderUpiDetails(
    val upiId: String = ""
) : Parcelable

@Parcelize
data class SenderBankAccountDetails(
    val accountNumber: String = "",
    val ifscCode: String = ""
) : Parcelable