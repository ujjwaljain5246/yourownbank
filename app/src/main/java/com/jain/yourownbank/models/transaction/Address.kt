package com.jain.yourownbank.models.transaction

data class Address(
    val city: String,
    val country: String,
    val pincode: String,
    val state: String,
    val street: String
)