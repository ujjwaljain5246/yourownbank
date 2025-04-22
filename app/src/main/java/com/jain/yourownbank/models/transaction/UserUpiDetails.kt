package com.jain.yourownbank.models.transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUpiDetails(
    val upiId: String = ""
) : Parcelable