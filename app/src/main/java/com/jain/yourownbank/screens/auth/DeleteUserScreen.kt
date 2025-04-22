package com.jain.yourownbank.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.ErrorCodesAndMessages
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically
import com.jain.yourownbank.viewModels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DeleteUserScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()
    val isLoading by loginViewModel.isLoadingLiveData().observeAsState(initial = false)
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val isMobileSelected = rememberSaveable { mutableStateOf(true) }
    val password = rememberSaveable { mutableStateOf("") }
    val userName = rememberSaveable { mutableStateOf("") }
    val userNameError = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    ObserveLiveDataInDeleteUserScreen(
        loginViewModel = loginViewModel,
        lifeCycleOwner = lifeCycleOwner,
        userNameError = userNameError,
        passwordError = passwordError,
        navController = navController,
        context = context
    )

    if (isLoading == true) {
        ShowFullScreenCustomLoader(animationRes = R.raw.yob_loader_anim)
    }

    if (isLoading == false) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WayOfLoginButton(
                    isMobileSelected = isMobileSelected
                )
                UserLoginCredentialeDetails(
                    isMobileSelected = isMobileSelected,
                    userName = userName,
                    userNameError = userNameError,
                    password = password,
                    passwordError = passwordError
                )
                SetSpaceHorizontallyOrVertically(height = true, value = 5.dp)
                DeleteUserButton(
                    isMobileSelected = isMobileSelected,
                    userName = userName,
                    userNameError = userNameError,
                    password = password,
                    passwordError = passwordError,
                    loginViewModel = loginViewModel,
                    navController = navController,
                    focusManager = focusManager
                )
            }

        }
    }
}

@Composable
fun DeleteUserButton(
    isMobileSelected: MutableState<Boolean>,
    userName: MutableState<String>,
    userNameError: MutableState<String>,
    password: MutableState<String>,
    passwordError: MutableState<String>,
    loginViewModel: LoginViewModel,
    navController: NavController,
    focusManager: FocusManager
) {
    Button(
        onClick = {
            focusManager.clearFocus() // To remove the keyboard whenever user click any button
            val userNameValidationError = when {
                userName.value.isEmpty() -> "Username can not be empty"
                isMobileSelected.value && !userName.value.matches(Regex("^\\d{10}$")) -> "Please enter a valid 10 digit mobile number"
                !isMobileSelected.value && !userName.value.contains("@", ignoreCase = true) -> "@ is missing in your email"
                else -> ""
            }
            val passwordValidationError = when {
                password.value.isEmpty() -> "Password can not be empty"
                else -> ""
            }
            userNameError.value = userNameValidationError
            passwordError.value = passwordValidationError
            Log.i("Ujjwal", "DeleteUserButton: userNameValidationError = $userNameValidationError")
            Log.i("Ujjwal", "DeleteUserButton: passwordValidationError = $passwordValidationError")
            if (userNameValidationError.isEmpty() && passwordValidationError.isEmpty()) {
                Log.i("Ujjwal", "Delete api called from screen")
                loginViewModel.setLoadingTrue()
                CoroutineScope(Dispatchers.Main).launch {
                    loginViewModel.deleteUserViewModel(
                        userName = userName.value,
                        password = password.value,
                        path = EndPoints.DELETE_USER_END_POINT,
                        requestCode = RequestCodes.DELETE_USER
                    )
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Delete Account")
    }
}


@Composable
fun ObserveLiveDataInDeleteUserScreen(
    loginViewModel: LoginViewModel,
    lifeCycleOwner: LifecycleOwner,
    userNameError: MutableState<String>,
    passwordError: MutableState<String>,
    navController: NavController,
    context: Context
) {
    val deleteUserSuccessResponse by loginViewModel.getDeleteUserSuccessLiveData().observeAsState()
    val deleteUserFailedResponse by loginViewModel.getDeleteUserFailedLiveData().collectAsState()

    deleteUserSuccessResponse?.let { response ->
        if (response.message.isNotEmpty() && response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_LONG).show()
                navController.navigate(ScreensRoutes.SignInScreen.route) {
                    popUpTo(0) {inclusive = true}
                }
            }
        }
    }

    deleteUserFailedResponse?.let { response ->
        if (response.message.isNotEmpty() && response.code != -1) {
            LaunchedEffect(Unit) {
                Log.i("Ujjwal", "ObserveLiveData: setting the isLoading as false after failure")
                if (response.errorCode == ErrorCodesAndMessages.WRONG_PASSWORD.errorCode) {
                    passwordError.value = ErrorCodesAndMessages.WRONG_PASSWORD.errorMessage
                    Log.i("Ujjwal", "ObserveLiveData: passwordError = ${passwordError.value}")
                    userNameError.value = ""
                } else if (response.errorCode == ErrorCodesAndMessages.INVALID_USER.errorCode) {
                    userNameError.value = ErrorCodesAndMessages.INVALID_USER.errorMessage
                    passwordError.value = ""
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                loginViewModel.setApiFailedResponseToEmpty()
                loginViewModel.setLoadingFalse()
            }
        }
    }
}