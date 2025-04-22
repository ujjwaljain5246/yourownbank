package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import com.jain.yourownbank.models.CommonModel
import com.jain.yourownbank.models.auth.UserContactDetails
import com.jain.yourownbank.models.auth.UserPersonalDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserBankDetailsModel(
    val userBankDetails: UserBankDetails = UserBankDetails()
): CommonModel(), Parcelable

@Parcelize
data class UserBankDetails(
    val userAccountDetails: UserAccountDetails = UserAccountDetails(),
    val userAvailableBalance: Int = 0,
    val userBankAccountDetails: UserBankAccountDetails = UserBankAccountDetails(),
    val userContactDetails: UserContactDetails = UserContactDetails(),
    val userCustomerId: String = "",
    val userPersonalDetails: UserPersonalDetails = UserPersonalDetails(),
    val userUpiDetails: UserUpiDetails = UserUpiDetails()
) : Parcelable