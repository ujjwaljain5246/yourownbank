package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAccountDetails(
    val accountType: String = "",
    val occupation: String = ""
) : Parcelable