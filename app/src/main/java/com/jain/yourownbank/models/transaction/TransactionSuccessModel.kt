package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import com.jain.yourownbank.models.CommonModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionSuccessModel(
    val transactionDetails: TransactionDetails = TransactionDetails()
) : CommonModel(), Parcelable

@Parcelize
data class TransactionDetails(
    val amount: Int = 0,
    val modeOfTransaction: String = "",
    val receiverDetails: ReceiverDetails = ReceiverDetails(),
    val senderDetails: SenderDetails = SenderDetails(),
    val transactiionTime: String = "",
    val transactionId: String = "",
    val transactionStatus: String = "",
    val userName: String = ""
) : Parcelable