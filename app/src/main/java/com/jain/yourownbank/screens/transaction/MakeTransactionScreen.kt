package com.jain.yourownbank.screens.transaction

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.TransactionDetails
import com.jain.yourownbank.models.transaction.TransactionSuccessModel
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.ErrorCodesAndMessages
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically
import com.jain.yourownbank.utils.ShowErrorMessage
import com.jain.yourownbank.viewModels.TransactionViewModel
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SendMoney(navController: NavController) {
    val modeOfTransaction = remember { mutableStateOf("UPI") }
    val upiId = remember { mutableStateOf("") }
    val accountNumber = remember { mutableStateOf("") }
    val ifscCode = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }

    val transactionViewModel : TransactionViewModel = viewModel()
    val isLoading by transactionViewModel.getIsLoadingLiveData().observeAsState(initial = false)
    val transactionDetails = rememberSaveable { mutableStateOf(TransactionDetails()) }

    val upiIdError = rememberSaveable { mutableStateOf("") }
    val amountError = rememberSaveable { mutableStateOf("") }
    val bankAccountNumberError = rememberSaveable { mutableStateOf("") }
    val ifscCodeError = rememberSaveable { mutableStateOf("") }

    ObserveLiveDataInMakeTransactionScreen(
        transactionViewModel = transactionViewModel,
        transactionDetails = transactionDetails,
        navController = navController,
        upiIdError = upiIdError,
        amountError = amountError,
        bankAccountNumberError = bankAccountNumberError,
        ifscCodeError = ifscCodeError
    )

    if (isLoading == true) {
        ShowFullScreenCustomLoader(animationRes = R.raw.yob_loader_anim)
    }

    if (isLoading == false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ModeOfTransactionRow(modeOfTransaction)
            SetSpaceHorizontallyOrVertically(height = true, value = 5.dp)
            SetDynamicInputFieldsBasedOnModeSelection(
                modeOfTransaction = modeOfTransaction,
                upiId = upiId,
                accountNumber = accountNumber,
                ifscCode = ifscCode,
                upiIdError = upiIdError,
                bankAccountNumberError = bankAccountNumberError,
                ifscCodeError = ifscCodeError,
                amountError = amountError
            )
            SetSpaceHorizontallyOrVertically(height = true, value = 5.dp)
            SetTextFieldForAmount(
                amount = amount,
                amountError = amountError
            )
            SetSpaceHorizontallyOrVertically(height = true, value = 5.dp)
            SendMoneyButton(
                navController = navController,
                modeOfTransaction = modeOfTransaction,
                bankAccountNumber = accountNumber,
                bankIfscCode = ifscCode,
                upiId = upiId,
                amount = amount,
                transactionViewModel = transactionViewModel,
                upiIdError = upiIdError,
                bankAccountNumberError = bankAccountNumberError,
                ifscCodeError = ifscCodeError,
                amountError = amountError
            )
            ScanYOBQRCodeButton(
                modeOfTransaction = modeOfTransaction,
                upiId = upiId,
                navController = navController
            )
        }
    }
}

@Composable
fun ObserveLiveDataInMakeTransactionScreen(
    transactionViewModel: TransactionViewModel,
    transactionDetails: MutableState<TransactionDetails>,
    navController: NavController,
    upiIdError: MutableState<String>,
    bankAccountNumberError: MutableState<String>,
    amountError: MutableState<String>,
    ifscCodeError: MutableState<String>
) {
    val context = LocalContext.current
    val transactionDetailsSuccess by transactionViewModel.getTransactionDetailsLiveData().observeAsState()
    val apiFailedResponse by transactionViewModel.getApiFailedResponseLiveData().collectAsState()

    transactionDetailsSuccess.let { response ->
        if (response?.message?.contains("success", ignoreCase = true) == true) {
            LaunchedEffect(Unit) {
                transactionDetails.value = response.transactionDetails
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "transactionDetails", response.transactionDetails
                )
                navController.navigate(ScreensRoutes.TransactionSuccessScreen.route)
                transactionViewModel.setTransactionDetailsToEmpty()
                transactionViewModel.setIsLoadingFalse()
            }
        }
    }

    apiFailedResponse.let { response ->
        if (response.message.isNotEmpty() && response.code != -1) {
            LaunchedEffect(Unit) {
                if (response.errorCode == ErrorCodesAndMessages.INVALID_UPI_ID.errorCode) {
                    upiIdError.value = ErrorCodesAndMessages.INVALID_UPI_ID.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.MISSING_UPI_ID.errorCode) {
                    upiIdError.value = ErrorCodesAndMessages.MISSING_UPI_ID.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.SELF_TRANSFER.errorCode) {
                    Toast.makeText(context, ErrorCodesAndMessages.SELF_TRANSFER.errorMessage, Toast.LENGTH_LONG).show()
                } else if (response.errorCode == ErrorCodesAndMessages.NOT_A_VALID_AMOUNT.errorCode) {
                    amountError.value = ErrorCodesAndMessages.NOT_A_VALID_AMOUNT.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.INSUFFICIENT_BALANCE.errorCode) {
                    amountError.value = ErrorCodesAndMessages.INSUFFICIENT_BALANCE.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.INVALID_ACCOUNT_NUMBER.errorCode) {
                    bankAccountNumberError.value = ErrorCodesAndMessages.INVALID_ACCOUNT_NUMBER.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.INVALID_IFSC_CODE.errorCode) {
                    ifscCodeError.value = ErrorCodesAndMessages.INVALID_IFSC_CODE.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.BANK_ACCOUNT_NUMBER_MISSING.errorCode) {
                    bankAccountNumberError.value = ErrorCodesAndMessages.BANK_ACCOUNT_NUMBER_MISSING.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.IFSC_NUMBER_MISSING.errorCode) {
                    ifscCodeError.value = ErrorCodesAndMessages.IFSC_NUMBER_MISSING.errorMessage
                } else if (response.errorCode == ErrorCodesAndMessages.BANK_ACCOUNT_NUMBER_AND_IFSC_CODE_MISSING.errorCode) {
                    Toast.makeText(context, ErrorCodesAndMessages.BANK_ACCOUNT_NUMBER_AND_IFSC_CODE_MISSING.errorMessage, Toast.LENGTH_LONG).show()
                } else if (response.errorCode == ErrorCodesAndMessages.NO_USER_FOUND.errorCode) {
                    Toast.makeText(context, ErrorCodesAndMessages.NO_USER_FOUND.errorMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                transactionViewModel.setApiFailedResponseToEmpty()
                transactionViewModel.setIsLoadingFalse()
            }
        }
    }


}

@Composable
fun ModeOfTransactionRow(
    modeOfTransaction: MutableState<String>
) {
    val expanded = remember { mutableStateOf(false) }
    Box {
        SelectModeTextAndDropDownButton(
            modeOfTransaction, expanded
        )
        DropDownMenu(
            expanded = expanded,
            modeOfTransaction = modeOfTransaction
        )
    }
}

@Composable
fun SelectModeTextAndDropDownButton(
    modeOfTransaction: MutableState<String>,
    expanded: MutableState<Boolean>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ShowSelectModeText()
        SetSpaceHorizontallyOrVertically(height = false, value = 10.dp)
        DropDownButtonWithSelectedModeTextAndDropDownIcon(
            modeOfTransaction,
            expanded
        )
    }
}

@Composable
fun ShowSelectModeText() {
    Text(
        text = "Select mode :",
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DropDownButtonWithSelectedModeTextAndDropDownIcon(
    modeOfTransaction: MutableState<String>,
    expanded: MutableState<Boolean>,
) {
    Button(
        onClick = { expanded.value = !expanded.value },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SetSelectedModeTextInDropDownButton(
                modeOfTransaction = modeOfTransaction.value,
                modifier = Modifier.weight(1f)
            )
            SetSpaceHorizontallyOrVertically(height = false, value = 5.dp)
            SetDownArrowInDropDownButton()
        }
    }
}

@Composable
fun DropDownMenu(
    expanded: MutableState<Boolean>,
    modeOfTransaction: MutableState<String>
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = "UPI") },
            onClick = {
                modeOfTransaction.value = "UPI"
                expanded.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Bank Transfer") },
            onClick = {
                modeOfTransaction.value = "Bank Transfer"
                expanded.value = false
            }
        )
    }
}

@Composable
fun SetTextFieldForBankTransfer(
    accountNumber: MutableState<String>,
    ifscCode: MutableState<String>,
    bankAccountNumberError: MutableState<String>,
    ifscCodeError: MutableState<String>,
    amountError: MutableState<String>
) {
    SetTextFieldForAccountNumber(
        accountNumber = accountNumber,
        bankAccountNumberError = bankAccountNumberError,
    )
    SetSpaceHorizontallyOrVertically(height = true, value = 5.dp)
    SetTextFieldForIfscCode(
        ifscCode = ifscCode,
        ifscCodeError = ifscCodeError
    )
}

@Composable
fun SetTextFieldForUpiTransfer(
    upiId: MutableState<String>,
    upiIdError: MutableState<String>
) {
    SetTextFieldForUpiId(
        upiId = upiId,
        upiIdError = upiIdError
    )
}

@Composable
fun SetTextFieldForIfscCode(
    ifscCode: MutableState<String>,
    ifscCodeError: MutableState<String>
) {
    if (ifscCodeError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = ifscCodeError.value)
    }
    OutlinedTextField(
        value = ifscCode.value,
        onValueChange = {
            ifscCode.value = it.uppercase()
            ifscCodeError.value = ""
        },
        label = { Text("Enter IFSC Code") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SetTextFieldForAccountNumber(
    accountNumber: MutableState<String>,
    bankAccountNumberError: MutableState<String>
) {
    if (bankAccountNumberError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = bankAccountNumberError.value)
    }
    OutlinedTextField(
        value = accountNumber.value,
        onValueChange = {
            accountNumber.value = it.uppercase()
            bankAccountNumberError.value = ""
        },
        label = { Text("Enter Account Number") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SetTextFieldForUpiId(
    upiId: MutableState<String>,
    upiIdError: MutableState<String>
) {
    if (upiIdError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = upiIdError.value)
    }
    OutlinedTextField(
        value = upiId.value,
        onValueChange = {
            upiId.value = it
            upiIdError.value = ""
        },
        label = { Text("Enter UPI ID") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SetTextFieldForAmount(
    amount: MutableState<String>,
    amountError: MutableState<String>
) {
    if (amountError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = amountError.value)
    }
    OutlinedTextField(
        value = amount.value,
        onValueChange = {
            amount.value = it
            amountError.value = ""
        },
        label = { Text("Enter Amount") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SendMoneyButton(
    navController: NavController,
    amount: MutableState<String>,
    upiId: MutableState<String>,
    bankAccountNumber: MutableState<String>,
    bankIfscCode: MutableState<String>,
    transactionViewModel: TransactionViewModel,
    modeOfTransaction: MutableState<String>,
    upiIdError: MutableState<String>,
    bankAccountNumberError: MutableState<String>,
    ifscCodeError: MutableState<String>,
    amountError: MutableState<String>
) {
    Button(
        onClick = {
            val upiIdErrorValidation = when {
                upiId.value.isEmpty() -> "UPI ID can't be empty"
                !upiId.value.contains("@") -> "@ missing in UPI ID"
                upiId.value.startsWith("@") -> "UPI ID can't start with @"
                upiId.value.endsWith("@") -> "UPI ID can't end with @"
                upiId.value.count { it == '@' } > 1 -> "UPI ID must contain exactly one @"
                upiId.value.substringBefore("@").isEmpty() -> "Invalid UPI ID format"
                upiId.value.substringAfter("@").isEmpty() -> "Invalid UPI handle"
                !upiId.value.substringBefore("@").matches(Regex("^[a-zA-Z0-9]+$")) -> "UPI ID should contain only alphanumeric characters before @"
                upiId.value.substringAfter("@").length !in 2..15 -> "Invalid UPI handle length"
                upiId.value.substringAfter("@") != "yob" -> "Invalid UPI ID format"
                else -> ""
            }
            val bankAccountNumberErrorValidation = when {
                bankAccountNumber.value.isEmpty() -> "Bank account number can't be empty"
                bankAccountNumber.value.length != 12 -> "Bank account number must be of 12 digits"
                else -> ""
            }

            val ifscCodeErrorValidation = when {
                bankIfscCode.value.isEmpty() -> "IFSC Code can't be empty"
                bankIfscCode.value.length != 10 -> "IFSC Code must be of 10 digit"
                !bankIfscCode.value.startsWith("YOB") -> "IFSC Code must start with YOB"
                else -> ""
            }


            val amountErrorValidation = when {
                amount.value.isEmpty() -> "Amount can't be empty"
                amount.value == "." || amount.value == ".." || amount.value.matches(Regex("^\\d*\\.$")) -> "Enter a valid amount"
                amount.value.toDoubleOrNull() == null -> "Enter a valid amount"
                amount.value.toDouble() <= 0 -> "Amount must be greater than zero"
                amount.value.toInt() >50000 -> "Max 50,000 possible at a time"
                else -> ""
            }

            upiIdError.value = upiIdErrorValidation
            bankAccountNumberError.value = bankAccountNumberErrorValidation
            ifscCodeError.value = ifscCodeErrorValidation
            amountError.value = amountErrorValidation

            if ((upiIdError.value.isEmpty() || (bankAccountNumberError.value.isEmpty() && ifscCodeError.value.isEmpty())) && amountError.value.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    transactionViewModel.setIsLoadingTrue()
                    transactionViewModel.sendMoneyViewModel(
                        path = EndPoints.SEND_MONEY + Constants.USER_ID,
                        requestCode = RequestCodes.SEND_MONEY,
                        amount = amount.value.toInt(),
                        bankAccountNumber = bankAccountNumber.value,
                        ifscCode = bankIfscCode.value,
                        upiId = upiId.value,
                        modeOfTransaction = modeOfTransaction.value
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor))
    ) {
        Text("Send Money")
    }
}

@Composable
fun SetDynamicInputFieldsBasedOnModeSelection(
    modeOfTransaction: MutableState<String>,
    upiId: MutableState<String>,
    accountNumber: MutableState<String>,
    ifscCode: MutableState<String>,
    upiIdError: MutableState<String>,
    bankAccountNumberError: MutableState<String>,
    ifscCodeError: MutableState<String>,
    amountError: MutableState<String>
) {
    when (modeOfTransaction.value) {
        "UPI" -> {
            SetTextFieldForUpiTransfer(
                upiId = upiId,
                upiIdError = upiIdError
            )
        }
        "Bank Transfer" -> {
            SetTextFieldForBankTransfer(
                accountNumber = accountNumber,
                ifscCode = ifscCode,
                bankAccountNumberError = bankAccountNumberError,
                ifscCodeError = ifscCodeError,
                amountError = amountError
            )
        }
    }
}

@Composable
fun SetSelectedModeTextInDropDownButton(modeOfTransaction: String, modifier: Modifier) {
    Text(
        text = modeOfTransaction,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun SetDownArrowInDropDownButton() {
    Icon(
        imageVector = Icons.Filled.ArrowDropDown,
        contentDescription = "Dropdown Arrow",
        tint = Color.White,
        modifier = Modifier.size(20.dp)
    )
}

@Composable
fun ScanYOBQRCodeButton(
    modeOfTransaction: MutableState<String>,
    upiId: MutableState<String>,
    navController: NavController
) {

    var upiIdAfterScannedYOBQR = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("upiId")

    var modeOfTransactionAfterScannedYOBQR = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("modeOfTransaction")


    if (!upiIdAfterScannedYOBQR?.value.isNullOrEmpty() && !modeOfTransactionAfterScannedYOBQR?.value.isNullOrEmpty()) {
        upiId.value = upiIdAfterScannedYOBQR?.value.toString()
        upiIdAfterScannedYOBQR?.value = null
        modeOfTransaction.value = modeOfTransactionAfterScannedYOBQR?.value.toString()
        modeOfTransactionAfterScannedYOBQR?.value = null
    }

    Button(
        onClick = { navController.navigate(ScreensRoutes.ScanningYOBQRCodeScreen.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor))
    ) {
        Text("Scan QR Code")
    }
}


