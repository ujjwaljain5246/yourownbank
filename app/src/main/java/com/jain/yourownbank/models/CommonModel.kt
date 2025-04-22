package com.jain.yourownbank.models

import com.google.gson.annotations.SerializedName

open class CommonModel(
    @SerializedName("code")
    val code : Int,
    @SerializedName("message")
    val message : String
) {
    constructor() : this(code = 0, message = "")
}