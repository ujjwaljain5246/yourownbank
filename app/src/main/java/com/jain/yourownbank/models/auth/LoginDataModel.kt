package com.jain.yourownbank.models.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jain.yourownbank.models.CommonModel
import kotlinx.parcelize.Parcelize


data class LoginDataModel(
    @SerializedName("token") val token: String = "",
    @SerializedName("user") val user: User = User()
) : CommonModel()

data class User(
    @SerializedName("userAccountDetails") val accountDetails: UserAccountDetails = UserAccountDetails(),
    @SerializedName("userContactDetails") val contactDetails: UserContactDetails = UserContactDetails(),
    @SerializedName("userId") val id: String = "",
    @SerializedName("userPersonalDetails") val personalDetails: UserPersonalDetails = UserPersonalDetails()
)

@Parcelize
data class UserContactDetails(
    @SerializedName("address") val address: Address = Address(),
    @SerializedName("email") var email: String = "",
    @SerializedName("mobile") var mobile: String = ""
) : Parcelable

@Parcelize
data class UserPersonalDetails(
    @SerializedName("aadharNumber") var aadharNumber: String = "",
    @SerializedName("gender") var gender: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("panNumber") var panNumber: String = ""
) : Parcelable

@Parcelize
data class Address(
    @SerializedName("city") var city: String = "",
    @SerializedName("country") var country: String = "",
    @SerializedName("pincode") var pincode: String = "",
    @SerializedName("state") var state: String = "",
    @SerializedName("street") var street: String = ""
) : Parcelable

data class UserAccountDetails(
    @SerializedName("accountType") var accountType: String = "",
    @SerializedName("occupation") var occupation: String = ""
)

data class UserSecurityDetails(
    var password: String = ""
)