package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserBankAccountDetails(
    @SerializedName("accountNumber")
    val accountNumber: String = "",

    @SerializedName("ifscCode")
    val ifscCode: String = ""
) : Parcelable