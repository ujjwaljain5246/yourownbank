package com.jain.yourownbank.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.jain.yourownbank.models.auth.User
import com.jain.yourownbank.models.transaction.AvailableBalanceModel
import com.jain.yourownbank.models.transaction.UserAvailableBalanceWithBankDetails
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically

@Composable
fun AvailableBalanceScreen(
    navController: NavController
) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val userAvailableBalanceWithBankDetails = savedStateHandle?.get<UserAvailableBalanceWithBankDetails>("userAvailableBalanceWithBankDetails")
    val responseString = "{\"code\":200,\"message\":\"Userbalancefetchedsuccessfully\",\"userAvailableBalanceWithBankDetails\":{\"userAvailableBalance\":199656,\"userBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"userAccountDetails\":{\"accountType\":\"Savings\",\"occupation\":\"Engineer\"},\"userUpiDetails\":{\"upiId\":\"9939481241@yob\"}}}"
    val response = Gson().fromJson(responseString, AvailableBalanceModel::class.java)
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ShowAvailableBalanceTextAndAmount(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails ?: UserAvailableBalanceWithBankDetails())

            SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)

            ShowBankDetailsCard(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails ?: UserAvailableBalanceWithBankDetails())
        }
    }
}


@Composable
fun ShowAvailableBalanceTextAndAmount(
    userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails
){
    SetAvailableBalanceText()
    SetAvailableAmountText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
}

@Composable
fun SetAvailableBalanceText(){
    Text(
        text = "Available Balance",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SetAvailableAmountText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(
        text = "₹${userAvailableBalanceWithBankDetails.userAvailableBalance}",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4CAF50)
    )
}

@Composable
fun ShowBankDetailsCard(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SetBankDetails(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)

            SetSpaceHorizontallyOrVertically(height = true, value = 8.dp)

            SetAccountDetails(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
        }
    }
}

@Composable
fun SetBankDetails(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    SetBankDetailsText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
    SetBankAccountNumberText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
    SetIFSCCodeText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
}

@Composable
fun SetBankDetailsText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(
        text = "Bank Details",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SetBankAccountNumberText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(text = "Account Number: ${userAvailableBalanceWithBankDetails.userBankAccountDetails.accountNumber}")
}

@Composable
fun SetIFSCCodeText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(text = "IFSC Code: ${userAvailableBalanceWithBankDetails.userBankAccountDetails.ifscCode}")
}

@Composable
fun SetAccountDetails(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    SetAccountDetailsText()
    SetAccountTypeText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
    SetOccupationTypeText(userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails)
}

@Composable
fun SetAccountDetailsText() {
    Text(
        text = "Account Details",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SetAccountTypeText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(text = "Account Type: ${userAvailableBalanceWithBankDetails.userAccountDetails.accountType}")
}

@Composable
fun SetOccupationTypeText(userAvailableBalanceWithBankDetails: UserAvailableBalanceWithBankDetails) {
    Text(text = "Occupation: ${userAvailableBalanceWithBankDetails.userAccountDetails.occupation}")
}
