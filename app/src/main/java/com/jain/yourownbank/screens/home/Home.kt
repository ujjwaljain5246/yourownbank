package com.jain.yourownbank.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.models.transaction.AvailableBalanceModel
import com.jain.yourownbank.models.transaction.UserAvailableBalanceWithBankDetails
import com.jain.yourownbank.models.transaction.UserBankDetails
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.utils.ShowKeyValuePair
import com.jain.yourownbank.viewModels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavController) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val scrollableState = rememberScrollState()
    val homeViewModel: HomeViewModel = viewModel()
    val userBankDetails = rememberSaveable { mutableStateOf(UserBankDetails()) }
    val userAvailableBalanceWithBankDetails = rememberSaveable { mutableStateOf(UserAvailableBalanceWithBankDetails()) }
    val isLoading by homeViewModel.isLoadingLiveData().observeAsState(initial = true)
    GetUserBankDetailsForInitialSetUp(homeViewModel = homeViewModel)
    ObserveLiveData(
        homeViewModel = homeViewModel,
        lifeCycleOwner = lifeCycleOwner,
        navController = navController,
        userBankDetails = userBankDetails,
        userAvailableBalanceWithBankDetails = userAvailableBalanceWithBankDetails
    )
    if (isLoading == false) {
        Box (
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Scaffold (
                topBar = {
                    AppTopBar(navController = navController)
                },
                bottomBar = {
                    AppBottomBar(
                        navController = navController,
                        homeViewModel = homeViewModel
                    )
                }
            ) { paddingValues ->
                AppBodyContent(
                    paddingValues = paddingValues,
                    scrollableState = scrollableState,
                    userBankDetails = userBankDetails
                )
            }
        }
    }
    
    if (isLoading == true) {
        ShowFullScreenCustomLoader(animationRes = R.raw.yob_loader_anim)
    }
}


@Composable
@Preview
fun HomePreview() {
//    Home()
}

@Composable
fun UserProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.user_profile_image),
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
}

@Composable
fun UserBasicDetails(userBankDetails: MutableState<UserBankDetails>) {
    ShowKeyValuePair(key = "Name", value = userBankDetails.value.userPersonalDetails.name)
    ShowKeyValuePair(key = "Gender", value = userBankDetails.value.userPersonalDetails.gender)
    ShowKeyValuePair(key = "Occupation", value = userBankDetails.value.userAccountDetails.occupation)
    ShowKeyValuePair(key = "Aadhar Number", value = userBankDetails.value.userPersonalDetails.aadharNumber)
    ShowKeyValuePair(key = "Pan Number", value = userBankDetails.value.userPersonalDetails.panNumber.uppercase())
}

@Composable
fun UserContactDetails(userBankDetails: MutableState<UserBankDetails>) {
    ShowKeyValuePair(key = "Mobile Number", value = userBankDetails.value.userContactDetails.mobile)
    ShowKeyValuePair(key = "Email", value = userBankDetails.value.userContactDetails.email)
    ShowKeyValuePair(key = "Address", value = userBankDetails.value.userContactDetails.address.street)
    ShowKeyValuePair(key = "City", value = userBankDetails.value.userContactDetails.address.city)
    ShowKeyValuePair(key = "State", value = userBankDetails.value.userContactDetails.address.state)
    ShowKeyValuePair(key = "Country", value = userBankDetails.value.userContactDetails.address.country)
    ShowKeyValuePair(key = "Pin Code", value = userBankDetails.value.userContactDetails.address.pincode)
}

@Composable
fun UserAccountDetails(userBankDetails: MutableState<UserBankDetails>) {
    ShowKeyValuePair(key = "Account Number", value = userBankDetails.value.userBankAccountDetails.accountNumber)
    ShowKeyValuePair(key = "IFSC Code", value = userBankDetails.value.userBankAccountDetails.ifscCode)
    ShowKeyValuePair(key = "UPI Id", value = userBankDetails.value.userUpiDetails.upiId)
    ShowKeyValuePair(key = "Account Type", value = userBankDetails.value.userAccountDetails.accountType)
}

@Composable
fun AppTopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HamburgerMenu(navController)
        AppLogoAndName(modifier = Modifier.weight(1f))
        SettingButton(navController)
    }
}

@Composable
fun HamburgerMenu(navController: NavController) {
    IconButton(onClick = { navController.navigate(ScreensRoutes.ServiceScreen.route) }) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = colorResource(id = R.color.buttonUnselectedColor)
        )
    }
}

@Composable
fun AppLogoAndName(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.your_own_bank),
            contentDescription = "App Logo",
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
        )
    }
}

@Composable
fun SettingButton(navController: NavController) {
    IconButton(onClick = { navController.navigate(ScreensRoutes.SettingScreen.route) }) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = colorResource(id = R.color.buttonUnselectedColor)
        )
    }
}

@Composable
fun AppBodyContent(
    paddingValues: PaddingValues,
    scrollableState: ScrollState,
    userBankDetails: MutableState<UserBankDetails>
) {
    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(scrollableState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SetHeader(heading = "Profile Picture")
        UserProfileImage()
        SetHeader(heading = "Basic Details")
        UserBasicDetails(userBankDetails = userBankDetails)
        SetHeader(heading = "Contact Details")
        UserContactDetails(userBankDetails = userBankDetails)
        SetHeader(heading = "Account Details")
        UserAccountDetails(userBankDetails = userBankDetails)
    }
}

@Composable
fun AppBottomBar(
    navController: NavController,
    homeViewModel: HomeViewModel
    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeButton(modifier = Modifier.weight(1f), navController)
        PayFromHomePageButton(modifier = Modifier.weight(1f), navController)
        FetchBalanceButton(
            modifier = Modifier.weight(1f),
            navController = navController,
            homeViewModel = homeViewModel
        )
    }
}

@Composable
fun PayFromHomePageButton(modifier: Modifier, navController: NavController) {
    FloatingActionButton(
        modifier = modifier
            .height(60.dp),
        onClick = { navController.navigate(ScreensRoutes.SendMoneyScreen.route) },
        containerColor = colorResource(id = R.color.buttonUnselectedColor),
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Text(
            text = "Pay",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeButton(modifier: Modifier, navController: NavController) {
    Button(
        onClick = {
            if (navController.currentDestination?.route != ScreensRoutes.HomeScreen.route) {
                navController.navigate(ScreensRoutes.HomeScreen.route)
            }
        },
        modifier = modifier
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor))
    ) {
        Text(
            text = "Home",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FetchBalanceButton(
    modifier: Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Button(
        onClick = {
            homeViewModel.setIsLoadingTrue()
            if (homeViewModel.isLoadingLiveData().value == true) {
                CoroutineScope(Dispatchers.Main).launch {
                    homeViewModel.getUserAvailableBalanceViewModel(
                        path = EndPoints.FETCH_AVAILABLE_BALANCE_END_POINT + Constants.USER_ID,
                        requestCode = RequestCodes.USER_AVAILABLE_BALANCE
                    )
                }
            }
        },
        modifier = modifier
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor))
    ) {
        Text(
            text = "Check Balance",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ObserveLiveData(
    userBankDetails: MutableState<UserBankDetails>,
    userAvailableBalanceWithBankDetails : MutableState<UserAvailableBalanceWithBankDetails>,
    homeViewModel: HomeViewModel,
    lifeCycleOwner: LifecycleOwner,
    navController: NavController
) {

    val context = LocalContext.current
    val userBankDetailsSuccess by homeViewModel.successfullyFetchedUserBankDetailsLiveDataExposed().observeAsState()
    val apiFailedResponse by homeViewModel.apiFailedResponseStateFlowExposed().collectAsState()
    val userAvailableBalanceSuccess by homeViewModel.successfullyFetchedUserAvailableBalanceLiveDataExposed().observeAsState()

    // Handle Successful Login Response
    userBankDetailsSuccess?.let { response ->
        if (response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
                userBankDetails.value = response.userBankDetails
                Constants.USER_COMPLETE_BANK_DETAILS = response.userBankDetails
                homeViewModel.setIsLoadingFalse()
            }
        }
    }

    userAvailableBalanceSuccess?.let { response ->
        if (response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
                userAvailableBalanceWithBankDetails.value = response.userAvailableBalanceWithBankDetails
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "userAvailableBalanceWithBankDetails", userAvailableBalanceWithBankDetails.value
                )
                navController.navigate(ScreensRoutes.AvailableBalanceScreen.route)
                homeViewModel.setUserAvailableBalanceToEmpty()
                homeViewModel.setIsLoadingFalse()
            }
        }
    }


    // Handle api failed response
    apiFailedResponse.let { response ->
        if (response.message.isNotEmpty() && response.code != -1) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                homeViewModel.setApiFailedResponseToEmpty()
                homeViewModel.setIsLoadingFalse()
            }
        }
    }
}

@Composable
fun GetUserBankDetailsForInitialSetUp(
    homeViewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        homeViewModel.getUserBankDetailsViewModel(
            path = EndPoints.SETUP_BANK_ACCOUNT_END_POINT + Constants.USER_ID,
            requestCode = RequestCodes.USER_BANK_DETAILS
        )
    }
}