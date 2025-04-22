package com.jain.yourownbank.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.TransactionHistory
import com.jain.yourownbank.models.transaction.TransactionHistoryModel
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically

@Composable
fun TransactionHistory(navController: NavController) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val transactionHistoryList = savedStateHandle?.get<TransactionHistoryModel>("userTransactionHistoryList")
    val dummyList = "{\"code\":200,\"message\":\"Transactionhistoryfetchedsuccessfully\",\"transactionHistory\":[{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"receiverName\":\"Ravi\",\"receiverBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"amount\":10,\"transactionId\":\"TXN-D34DBAB0194A3CC2\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"14Feb,2025,20:07:14:641\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":1,\"transactionId\":\"TXN-33E94DB4633EF892\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"14Feb,2025,14:46:09:769\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":1,\"transactionId\":\"TXN-865DC4434431DF80\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"14Feb,2025,12:19:33:991\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":0,\"transactionId\":\"TXN-D79D03B57D9D5205\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"14Feb,2025,12:16:41:858\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":0,\"transactionId\":\"TXN-14C7D60EAD5349DF\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"4Feb,2025,17:27:22:193\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d23b0c386ae7dbfd46752\",\"receiverName\":\"Siddhart\",\"receiverBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"amount\":50,\"transactionId\":\"TXN-6D1F98AE9B5CC121\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"4Feb,2025,17:27:14:163\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"67a09af35a3441102803fc05\",\"receiverName\":\"Praveed\",\"receiverBankAccountDetails\":{\"accountNumber\":\"19BF9717CDA6\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"6260887891@yob\"}},\"amount\":50,\"transactionId\":\"TXN-B27907CABFC249E5\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"4Feb,2025,06:38:22:280\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"senderName\":\"Ujjwal\",\"senderBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"67a09af35a3441102803fc05\",\"receiverName\":\"Praveen\",\"receiverBankAccountDetails\":{\"accountNumber\":\"19BF9717CDA6\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"6260887891@yob\"}},\"amount\":50,\"transactionId\":\"TXN-65657D9D3D5406E9\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Debit\",\"transactionTime\":\"3Feb,2025,10:33:28:033\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d23b0c386ae7dbfd46752\",\"senderName\":\"Siddhart\",\"senderBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"Ujjwal\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-538417FC90410510\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"1Feb,2025,12:42:31:863\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d23b0c386ae7dbfd46752\",\"senderName\":\"Siddhart\",\"senderBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"Ujjwal\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-C1D29A5792899F3E\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"1Feb,2025,12:39:42:092\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d23b0c386ae7dbfd46752\",\"senderName\":\"Siddhart\",\"senderBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"Ujjwal\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-14F6B34C6A52D694\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"1Feb,2025,12:35:43:969\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"Ujjwal\",\"senderDetails\":{\"senderCustomerId\":\"679d23b0c386ae7dbfd46752\",\"senderName\":\"Siddhart\",\"senderBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"Ujjwal\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-E96E135EAEB5E430\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"1Feb,2025,12:27:23:790\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d23b0c386ae7dbfd46752\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"EA2563044102\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"9939481241@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-7BD3C98AB1038541\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"1Feb,2025,12:05:32:819\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-DDB9FEFABAAE87EB\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,19:34:43:290\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-1323FAC31477410B\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:39:01:302\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-4757D826BEE29429\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:37:47:453\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-44ECAD0A8E464B55\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:30:51:757\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-5F77B25150A7ED5A\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:28:00:577\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-619CD40CB0EED5F1\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:59:929\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-235F28936EBC68DF\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:58:339\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-980D375750D11A9B\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:55:603\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-946BB0C6C91FF003\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:55:001\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-3F2252E99B4A2E72\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:54:066\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-F763EE8611309D66\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:27:53:328\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-05227D9E88433972\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:26:41:570\"},{\"modeOfTransaction\":\"BankDetails\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-6C4A2DEF041C3F63\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:23:33:609\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-4C1263CFB0B46450\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:21:59:894\"},{\"modeOfTransaction\":\"UPI\",\"userName\":\"\",\"senderDetails\":{\"senderCustomerId\":\"679d1424fe92ac0a0cb7bee1\",\"senderName\":\"\",\"senderBankAccountDetails\":{\"accountNumber\":\"186158F5DFCD\",\"ifscCode\":\"YOB0400701\"},\"senderUpiDetails\":{\"upiId\":\"8104909647@yob\"}},\"receiverDetails\":{\"receiverCustomerId\":\"679d1403fe92ac0a0cb7bede\",\"receiverName\":\"\",\"receiverBankAccountDetails\":{\"accountNumber\":\"04CF6FAEF9C3\",\"ifscCode\":\"YOB0400701\"},\"receiverUpiDetails\":{\"upiId\":\"8292407021@yob\"}},\"amount\":1,\"transactionId\":\"TXN-76A401C9CE5420F5\",\"transactionStatus\":\"Success\",\"creditDebitStatus\":\"Credit\",\"transactionTime\":\"31Jan,2025,18:21:39:865\"}]}"
//    val transactionHistoryList = Gson().fromJson(dummyList, TransactionHistoryModel::class.java)
    Box (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column {
            SetHeader(heading = "Your Transaction History")
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(transactionHistoryList?.transactionHistory ?: emptyList()) { eachTransaction ->
                    EachTransaction(singleTransaction = eachTransaction, navController)
                }
            }
        }
    }
}

@Composable
fun TransactionHistoryPreview() {
//    TransactionHistory()
}

@Composable
fun EachTransaction(singleTransaction: TransactionHistory, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "clickedTransaction", singleTransaction
                )
                navController.navigate(ScreensRoutes.TransactionDetailsScreen.route)
            }
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShowCreditOrDebitArrow(singleTransaction)
            SetSpaceHorizontallyOrVertically(height = false, value = 8.dp)
            ShowMiniTransactionDetail(singleTransaction, modifier = Modifier.weight(1f))
            SetSpaceHorizontallyOrVertically(height = false, value = 8.dp)
            ShowTransactionAmount(singleTransaction)
        }
    }
}

@Composable
fun ShowCreditOrDebitArrow(singleTransaction: TransactionHistory) {
    Icon(
        painter = painterResource(id = if (singleTransaction.creditDebitStatus == "Credit") R.drawable.diagonal_arrow  else R.drawable.diagonal_arrow),
        contentDescription = "Transaction Type",
        tint = if (singleTransaction.creditDebitStatus == "Credit") Color.Green else Color.Red,
        modifier = Modifier
            .size(24.dp)
            .rotate(if (singleTransaction.creditDebitStatus == "Credit") 180f else 0f)
    )
}

@Composable
fun ShowMiniTransactionDetail(singleTransaction: TransactionHistory, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        // Sender or Receiver Name
        Text(
            text = if (singleTransaction.creditDebitStatus == "Credit") "From: ${singleTransaction.senderDetails.senderName}" else "To: ${singleTransaction.receiverDetails.receiverName}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        // Transaction ID
        Text(
            text = "Transaction ID: ${singleTransaction.transactionId}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Date and Time
        Text(
            text = singleTransaction.transactionTime,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ShowTransactionAmount(singleTransaction: TransactionHistory) {
    Text(
        text = "₹${singleTransaction.amount}",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (singleTransaction.creditDebitStatus == "Credit") Color.Green else Color.Red
    )
}
