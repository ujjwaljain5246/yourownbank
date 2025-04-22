package com.jain.yourownbank.screens.service

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.TransactionDetails
import com.jain.yourownbank.models.transaction.TransactionHistory
import com.jain.yourownbank.models.transaction.TransactionHistoryModel
import com.jain.yourownbank.screens.home.ObserveLiveData
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.viewModels.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ServiceScreen(
    navController: NavController
) {
    val transactionViewModel : TransactionViewModel = viewModel()
    val isLoading by transactionViewModel.getIsLoadingLiveData().observeAsState(initial = false)
    val userTransactionHistoryList = rememberSaveable { mutableStateOf(TransactionHistoryModel()) }
    ObserveLiveDataInServiceScreen(
        navController = navController,
        transactionViewModel = transactionViewModel,
        userTransactionHistoryList = userTransactionHistoryList
    )
    if (isLoading == true) {
        Log.i("ServiceScreen", "ServiceScreen: isLoading = $isLoading")
        ShowFullScreenCustomLoader(animationRes = R.raw.yob_loader_anim)
    }

    if (isLoading == false) {
        Box (
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SetHeader(heading = "Services for you")
                ServiceList(
                    navController = navController,
                    transactionViewModel = transactionViewModel
                )
            }
        }
    }
}

@Composable
@Preview
fun ServiceScreenPreview() {
//    ServiceScreen()
}


@Composable
fun ServiceList(
    navController: NavController,
    transactionViewModel: TransactionViewModel
    ) {
    val services = Constants.SERVICE_LIST

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(services) { service ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        colorResource(id = R.color.buttonUnselectedColor),
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        if (service.id == "4") {
                            transactionViewModel.setIsLoadingTrue()
                            CoroutineScope(Dispatchers.Main).launch {
                                transactionViewModel.getUserTransactionHistoryListViewModel(
                                    path = EndPoints.TRANSACTION_HISTORY_END_POINT + Constants.USER_ID,
                                    requestCode = RequestCodes.USER_TRANSACTION_HISTORY
                                )
                            }
                        } else if (service.id == "0") {
                            navController.navigate(service.route) {
                                popUpTo(ScreensRoutes.HomeScreen.route) {inclusive = true}
                            }
                        } else {
                            navController.navigate(service.route)
                        }
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = service.title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Composable
fun ObserveLiveDataInServiceScreen(
    transactionViewModel: TransactionViewModel,
    navController: NavController,
    userTransactionHistoryList: MutableState<TransactionHistoryModel>
) {

    val context = LocalContext.current
    val userTransactionHistoryListObserver by transactionViewModel.getTransactionHistoryLiveData().observeAsState()
    val apiFailedResponse by transactionViewModel.getApiFailedResponseLiveData().collectAsState()

    userTransactionHistoryListObserver?.let { response ->
        if (response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
                userTransactionHistoryList.value = response
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "userTransactionHistoryList", userTransactionHistoryList.value
                )
                navController.navigate(ScreensRoutes.TransactionHistoryListScreen.route)
                transactionViewModel.setTransactionHistoryToEmpty()
                transactionViewModel.setIsLoadingFalse()
            }
        }
    }

    apiFailedResponse.let { response ->
        if (response.message.isNotEmpty() && response.code != -1) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                transactionViewModel.setApiFailedResponseToEmpty()
                transactionViewModel.setIsLoadingFalse()
            }
        }
    }

}