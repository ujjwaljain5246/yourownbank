package com.jain.yourownbank.screens.auth

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jain.yourownbank.R
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.ErrorCodesAndMessages
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically
import com.jain.yourownbank.utils.ShowErrorMessage
import com.jain.yourownbank.viewModels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler

@Composable
fun SignIn(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()
    val isLoading by loginViewModel.isLoadingLiveData().observeAsState(initial = false)
    val lifeCycleOwner = LocalLifecycleOwner.current
    val isMobileSelected = rememberSaveable { mutableStateOf(true) }
    val password = rememberSaveable { mutableStateOf("") }
    val userName = rememberSaveable { mutableStateOf("") }
    val userNameError = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    ObserveLiveData(
        loginViewModel = loginViewModel,
        lifeCycleOwner = lifeCycleOwner,
        userNameError = userNameError,
        passwordError = passwordError,
        navController = navController
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
                SignInAndForgetPasswordButton(
                    isMobileSelected = isMobileSelected,
                    userName = userName,
                    userNameError = userNameError,
                    password = password,
                    passwordError = passwordError,
                    loginViewModel = loginViewModel,
                    navController = navController,
                    focusManager = focusManager
                )
                SetSpaceHorizontallyOrVertically(
                    height = true,
                    value = 5.dp
                )
                SignUpClickableText(
                    navController = navController
                )
            }

        }
    }

}


@Composable
@Preview
fun PreviewSignIn() {
//    SignIn()
}

@Composable
fun WayOfLoginButton(
    isMobileSelected: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
    ) {

        SignInwithMobileNumber(modifier = Modifier.weight(1f), isMobileSelected = isMobileSelected)
        SetSpaceHorizontallyOrVertically(height = false, value = 5.dp)
        SignInWithEmailId(modifier = Modifier.weight(1f), isMobileSelected = isMobileSelected)

    }
}

@Composable
fun SignInWithEmailId(
    modifier: Modifier,
    isMobileSelected: MutableState<Boolean>
) {
    Button(
        modifier = modifier,
        onClick = { isMobileSelected.value = false },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isMobileSelected.value == false) colorResource(id = R.color.buttonSelectedColor) else colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Using Email Id.")
    }
}

@Composable
fun SignInwithMobileNumber(
    modifier: Modifier,
    isMobileSelected: MutableState<Boolean>
) {
    Button(
        modifier = modifier,
        onClick = { isMobileSelected.value = true  },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isMobileSelected.value == true) colorResource(id = R.color.buttonSelectedColor) else colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Using Mobile No.")
    }
}

@Composable
fun UserLoginCredentialeDetails(
    isMobileSelected: MutableState<Boolean>,
    userName : MutableState<String>,
    userNameError : MutableState<String>,
    password : MutableState<String>,
    passwordError : MutableState<String>
) {
    val userChoiceText = "Enter" + if (isMobileSelected.value == true) " mobile number"  else " email Id"
    Column (
        modifier = Modifier
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
    ) {
        if (userNameError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = userNameError.value)
        }
        UserNameInputInSignInPage(
            isMobileSelected = isMobileSelected,
            userChoiceText = userChoiceText,
            userName = userName,
            userNameError = userNameError,
            passwordError = passwordError
        )
        if (passwordError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = passwordError.value)
        }
        UserPasswordInputInSignInPage(
            password = password,
            passwordError = passwordError,
            userNameError = userNameError
        )

    }
}

@Composable
fun UserPasswordInputInSignInPage(
    password : MutableState<String>,
    passwordError: MutableState<String>,
    userNameError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password.value,
        onValueChange = {
            password.value = it
            passwordError.value = ""
        },
        label = { Text(text = "Enter Password")},
        singleLine = true,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun UserNameInputInSignInPage(
    isMobileSelected: MutableState<Boolean>,
    userChoiceText: String,
    userName: MutableState<String>,
    userNameError: MutableState<String>,
    passwordError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = userName.value,
        onValueChange = {
            userName.value = it
            userNameError.value = ""
        },
        label = { Text(text = userChoiceText) },
        singleLine = true,
        keyboardOptions = if (isMobileSelected.value == true) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun SignInButton(
    isMobileSelected: MutableState<Boolean>,
    userName: MutableState<String>,
    userNameError: MutableState<String>,
    password: MutableState<String>,
    passwordError: MutableState<String>,
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navController: NavController,
    focusManager: FocusManager
) {

    Button(
        modifier = modifier,
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

            if (userNameValidationError.isEmpty() && passwordValidationError.isEmpty()) {
                Log.i("Ujjwal", "SignInButton: setting isLoading as true")
                loginViewModel.setLoadingTrue()
                CoroutineScope(Dispatchers.Main).launch {
                    loginViewModel.signInUserViewModel(
                        userName = userName.value,
                        password = password.value,
                        path = EndPoints.SIGN_IN_END_POINT,
                        requestCode = RequestCodes.SIGN_IN
                    )
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Sign In")
    }
}

@Composable
fun SignUpClickableText(navController: NavController) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Don't have account?")
        SetSpaceHorizontallyOrVertically(height = false, value = 4.dp)
        Text(
            text = "Sign Up",
            color = colorResource(id = R.color.buttonUnselectedColor),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            fontSize = 18.sp,
            modifier = Modifier
                .clickable {
                    navController.navigate(ScreensRoutes.SignUpScreen.route)
                }
        )
    }
}

@Composable
fun SignInAndForgetPasswordButton(
    isMobileSelected: MutableState<Boolean>,
    userName: MutableState<String>,
    userNameError: MutableState<String>,
    password: MutableState<String>,
    passwordError: MutableState<String>,
    loginViewModel: LoginViewModel,
    navController: NavController,
    focusManager: FocusManager
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
    ) {
        SignInButton(
            modifier = Modifier.weight(1f),
            isMobileSelected = isMobileSelected,
            userName = userName,
            userNameError = userNameError,
            password = password,
            passwordError = passwordError,
            loginViewModel = loginViewModel,
            navController = navController,
            focusManager = focusManager
        )
        SetSpaceHorizontallyOrVertically(height = false, value = 5.dp)
        ForgetPasswordButton(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ForgetPasswordButton(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Forget Password")
    }
}


@Composable
fun ObserveLiveData(
    loginViewModel: LoginViewModel,
    lifeCycleOwner: LifecycleOwner,
    userNameError: MutableState<String>,
    passwordError: MutableState<String>,
    navController: NavController
) {

    val context = LocalContext.current
    val loginSuccessResponse by loginViewModel.getLoginSuccessLiveData().observeAsState()
    val loginFailedResponse by loginViewModel.getLoginFailedLivedDataMessage().collectAsState()

    // Handle Successful Login Response
    loginSuccessResponse?.let { response ->
        if (response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
                Log.i("Ujjwal", "ObserveLiveData: setting the isLoading as false after success")
                Constants.TOKEN = response.token
                Constants.USER_ID = response.user.id
                Constants.USER = response.user
                navController.navigate(ScreensRoutes.HomeScreen.route) {
                    popUpTo(ScreensRoutes.SignInScreen.route) { inclusive = true }
                }
                loginViewModel.setLoadingFalse()
            }
        }
    }

    // Handle Failed Login Response
    loginFailedResponse.let { response ->
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


