package com.jain.yourownbank.utils

data class ErrorInfo(
    val errorCode: String = "",
    val errorMessage: String = ""
)

object ErrorCodesAndMessages {
    // 5000 related error
    val NETWORK_LOST = ErrorInfo(errorCode = "ERR_5001", errorMessage = "Network Connection Lost")
    val SERVER_ERROR = ErrorInfo(errorCode = "ERR_5002", errorMessage = "Server Error")

    // 4000 related error
    val WRONG_PASSWORD = ErrorInfo(errorCode = "ERR_4001", errorMessage = "Wrong Password")
    val INVALID_USER = ErrorInfo(errorCode = "ERR_4002", errorMessage = "Invalid User")
    val INVALID_UPI_ID = ErrorInfo(errorCode = "ERR_4003", errorMessage = "Invalid UPI Id")
    val INVALID_ACCOUNT_NUMBER = ErrorInfo(errorCode = "ERR_4004", errorMessage = "Invalid Bank Account Number for entered IFSC code")
    val INVALID_IFSC_CODE = ErrorInfo(errorCode = "ERR_4005", errorMessage = "Invalid IFSC Code for entered bank account number")
    val INSUFFICIENT_BALANCE = ErrorInfo(errorCode = "ERR_4006", errorMessage = "Not Sufficient Balance Available")
    val NOT_A_VALID_AMOUNT = ErrorInfo(errorCode = "ERR_4007", errorMessage = "Enter a valid amount")
    val TOKEN_MISSING_IN_HEADER = ErrorInfo(errorCode = "ERR_4008", errorMessage = "Authorization Token Missing in Header")
    val INVALID_OR_EXPIRED_TOKEN = ErrorInfo(errorCode = "ERR_4009", errorMessage = "Invalid or Expired Authorization Token")
    val INVALID_CREDENTIAL_IN_TOKEN = ErrorInfo(errorCode = "ERR_4010", errorMessage = "User's Credential Didn't Matches in Token")
    val BANK_DETAILS_NOT_FOUND = ErrorInfo(errorCode = "ERR_4011", errorMessage = "User's Bank Details Not Available")
    val MISSING_UPI_ID = ErrorInfo(errorCode = "ERR_4012", errorMessage = "UPI Id is missing")
    val BANK_ACCOUNT_NUMBER_MISSING = ErrorInfo(errorCode = "ERR_4013", errorMessage = "Bank account number is missing")
    val IFSC_NUMBER_MISSING = ErrorInfo(errorCode = "ERR_4014", errorMessage = "IFSC code is missing")
    val BANK_ACCOUNT_NUMBER_AND_IFSC_CODE_MISSING = ErrorInfo(errorCode = "ERR_4015", errorMessage = "Bank account number and IFSC code is missing")
    val NO_USER_FOUND = ErrorInfo(errorCode = "ERR_4016", errorMessage = "No user available with entered bank account number and IFSC code")
    val SELF_TRANSFER = ErrorInfo(errorCode = "ERR_4017", errorMessage = "Self transferring is invalid")
    val MOBILE_NUMBER_CONFLICT = ErrorInfo(errorCode = "ERR_4018", errorMessage = "User already exist with this mobile number")
    val EMAIL_CONFLICT = ErrorInfo(errorCode = "ERR_4019", errorMessage = "User already exist with this email")
    val AADHAR_CONFLICT = ErrorInfo(errorCode = "ERR_4020", errorMessage = "User already exist with this aadhar number")
    val PAN_CONFLICT = ErrorInfo(errorCode = "ERR_4021", errorMessage = "User already exist with this pan number")
    val SIGN_UP_CONFLICT = ErrorInfo(errorCode = "ERR_4022", errorMessage = "Sign up conflict occurred")



}