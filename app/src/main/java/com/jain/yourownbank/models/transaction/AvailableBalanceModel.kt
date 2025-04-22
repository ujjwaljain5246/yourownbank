package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import com.jain.yourownbank.models.CommonModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AvailableBalanceModel(
    val userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails = UserAvailableBalanceWithBankDetails()
) : CommonModel(), Parcelable


@Parcelize
data class UserAvailableBalanceWithBankDetails(
    val userAccountDetails: UserAccountDetails = UserAccountDetails(),
    val userAvailableBalance: Int = 0,
    val userBankAccountDetails: UserBankAccountDetails = UserBankAccountDetails(),
    val userUpiDetails: UserUpiDetails = UserUpiDetails()
) : Parcelable

