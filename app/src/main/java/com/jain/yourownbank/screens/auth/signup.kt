package com.jain.yourownbank.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.models.auth.Address
import com.jain.yourownbank.models.auth.User
import com.jain.yourownbank.models.auth.UserAccountDetails
import com.jain.yourownbank.models.auth.UserContactDetails
import com.jain.yourownbank.models.auth.UserPersonalDetails
import com.jain.yourownbank.models.auth.UserSecurityDetails
import com.jain.yourownbank.screens.loader.ShowFullScreenCustomLoader
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.EndPoints
import com.jain.yourownbank.utils.ErrorCodesAndMessages
import com.jain.yourownbank.utils.RequestCodes
import com.jain.yourownbank.utils.SetSpaceHorizontallyOrVertically
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.utils.ShowErrorMessage
import com.jain.yourownbank.viewModels.LoginViewModel

@Composable
fun SignUp(
    navController: NavController
) {
    val scrollableState = rememberScrollState()

    val loginViewModel: LoginViewModel = viewModel()
    val isLoading by loginViewModel.isLoadingLiveData().observeAsState(initial = false)
    val lifeCycleOwner = LocalLifecycleOwner.current

    // Input field variable
    val name = rememberSaveable { mutableStateOf("") }
    val gender = rememberSaveable { mutableStateOf("") }
    val aadharNumber = rememberSaveable { mutableStateOf("") }
    val panNumber = rememberSaveable { mutableStateOf("") }
    val mobileNumber = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    var address = rememberSaveable { mutableStateOf("") }
    val city = rememberSaveable { mutableStateOf("") }
    val state = rememberSaveable { mutableStateOf("") }
    val country = rememberSaveable { mutableStateOf("") }
    val pincode = rememberSaveable { mutableStateOf("") }
    val accountType = rememberSaveable { mutableStateOf("") }
    val occupation = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    // Error state variable
    val nameError = rememberSaveable { mutableStateOf("") }
    val genderError = rememberSaveable { mutableStateOf("") }
    val aadharNumberError = rememberSaveable { mutableStateOf("") }
    val panNumberError = rememberSaveable { mutableStateOf("") }
    val mobileNumberError = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("") }
    val addressError = rememberSaveable { mutableStateOf("") }
    val cityError = rememberSaveable { mutableStateOf("") }
    val stateError = rememberSaveable { mutableStateOf("") }
    val countryError = rememberSaveable { mutableStateOf("") }
    val pincodeError = rememberSaveable { mutableStateOf("") }
    val accountTypeError = rememberSaveable { mutableStateOf("") }
    val occupationError = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }

    ObserveLiveDataInSignUpPage(
        navController = navController,
        loginViewModel = loginViewModel,
        aadharNumberError = aadharNumberError,
        panNumberError = panNumberError,
        mobileNumberError = mobileNumberError,
        emailError = emailError
    )

    if (isLoading == true) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollableState)
            ) {
                SetHeader(heading = "Personal Details")
                UserPersonalDetailsEntry(
                    name = name,
                    gender = gender,
                    aadharNumber = aadharNumber,
                    panNumber = panNumber,
                    nameError = nameError,
                    genderError = genderError,
                    aadharNumberError = aadharNumberError,
                    panNumberError = panNumberError
                )
                SetSpaceHorizontallyOrVertically(height = true, value = 6.dp)
                SetHeader(heading = "Contact Details")
                UserContactDetailsEntry(
                    mobileNumber = mobileNumber,
                    email = email,
                    address = address,
                    city = city,
                    state = state,
                    country = country,
                    pincode = pincode,
                    mobileNumberError = mobileNumberError,
                    emailError = emailError,
                    addressError = addressError,
                    cityError = cityError,
                    stateError = stateError,
                    countryError = countryError,
                    pincodeError = pincodeError
                )
                SetSpaceHorizontallyOrVertically(height = true, value = 6.dp)
                SetHeader(heading = "Account Details")
                UserAccountDetailsEntry (
                    accountType = accountType,
                    occupation = occupation,
                    accountTypeError = accountTypeError,
                    occupationError = occupationError
                )
                SetSpaceHorizontallyOrVertically(height = true, value = 6.dp)
                SetHeader(heading = "Password Details")
                UserPasswordDetailsEntry(
                    password = password,
                    passwordError = passwordError
                )
                SetSpaceHorizontallyOrVertically(height = true, value = 6.dp)
                SignUpAndClearButton(
                    name = name,
                    gender = gender,
                    aadharNumber = aadharNumber,
                    panNumber = panNumber,
                    mobileNumber = mobileNumber,
                    email = email,
                    address = address,
                    city = city,
                    state = state,
                    country = country,
                    pincode = pincode,
                    accountType = accountType,
                    occupation = occupation,
                    password = password,
                    nameError = nameError,
                    genderError = genderError,
                    aadharNumberError = aadharNumberError,
                    panNumberError = panNumberError,
                    mobileNumberError = mobileNumberError,
                    emailError = emailError,
                    addressError = addressError,
                    cityError = cityError,
                    stateError = stateError,
                    countryError = countryError,
                    pincodeError = pincodeError,
                    accountTypeError = accountTypeError,
                    occupationError = occupationError,
                    passwordError = passwordError,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}

@Composable
@Preview
fun SignUpPreview() {
//    SignUp()
}

@Composable
fun UserPersonalDetailsEntry(
    name: MutableState<String>,
    gender: MutableState<String>,
    aadharNumber: MutableState<String>,
    panNumber: MutableState<String>,
    nameError: MutableState<String>,
    genderError: MutableState<String>,
    aadharNumberError: MutableState<String>,
    panNumberError: MutableState<String>
) {
    Column(
        modifier = Modifier
            .padding(5.dp, 0.dp)
    ) {
        if (nameError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = nameError.value)
        }
        UserNameInput(
            name = name,
            nameError = nameError
        )
        if (genderError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = genderError.value)
        }
        UserGenderInput(
            gender = gender,
            genderError = genderError
        )
        if (aadharNumberError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = aadharNumberError.value)
        }
        UserAadharNumberInput(
            aadharNumber = aadharNumber,
            aadharNumberError = aadharNumberError
        )
        if (panNumberError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = panNumberError.value)
        }
        UserPanNumberInput(
            panNumber = panNumber,
            panNumberError = panNumberError
        )
    }
}

@Composable
fun UserContactDetailsEntry(
    mobileNumber: MutableState<String>,
    email: MutableState<String>,
    address: MutableState<String>,
    city: MutableState<String>,
    state: MutableState<String>,
    country: MutableState<String>,
    pincode: MutableState<String>,
    mobileNumberError: MutableState<String>,
    emailError: MutableState<String>,
    addressError: MutableState<String>,
    cityError: MutableState<String>,
    stateError: MutableState<String>,
    countryError: MutableState<String>,
    pincodeError: MutableState<String>
) {
    Column(
        modifier = Modifier
            .padding(5.dp, 0.dp)
    ) {
        if (mobileNumber.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = mobileNumberError.value)
        }
        UserMobileInput(
            mobileNumber = mobileNumber,
            mobileNumberError = mobileNumberError
        )
        if (emailError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = emailError.value)
        }
        UserEmailInput(
            email = email,
            emailError = emailError
        )
        UserCompleteAddressInput(
            address = address,
            city = city,
            state = state,
            country = country,
            pincode = pincode,
            addressError = addressError,
            cityError = cityError,
            stateError = stateError,
            countryError = countryError,
            pincodeError = pincodeError
        )

    }
}

@Composable
fun UserAccountDetailsEntry(
    accountType: MutableState<String>,
    occupation: MutableState<String>,
    accountTypeError: MutableState<String>,
    occupationError: MutableState<String>
) {
    Column(
        modifier = Modifier
            .padding(5.dp, 0.dp)
    ) {
        if (accountTypeError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = accountTypeError.value)
        }
        UserAccountTypeInput(
            accountType = accountType,
            accountTypeError = accountTypeError
        )
        if (occupationError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = occupationError.value)
        }
        UserOccupationInput(
            occupation = occupation,
            occupationError = occupationError
        )
    }
}

@Composable
fun UserPasswordDetailsEntry(
    password: MutableState<String>,
    passwordError: MutableState<String>
) {

    Column(
        modifier = Modifier
            .padding(5.dp, 0.dp)
    ) {
        if (passwordError.value.isNotEmpty()) {
            ShowErrorMessage(errorMessage = passwordError.value)
        }
        UserPasswordInput(
            password = password
        )
    }

}

@Composable
fun UserCompleteAddressInput(
    address: MutableState<String>,
    city: MutableState<String>,
    state: MutableState<String>,
    country: MutableState<String>,
    pincode: MutableState<String>,
    addressError: MutableState<String>,
    cityError: MutableState<String>,
    stateError: MutableState<String>,
    countryError: MutableState<String>,
    pincodeError: MutableState<String>
) {
    if (addressError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = addressError.value)
    }
    UserAddressInput(
        address = address,
        addressError = addressError
    )
    if (cityError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = cityError.value)
    }
    UserCityInput(
        city = city,
        cityError = cityError
    )
    if (stateError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = stateError.value)
    }
    UserStateInput(
        state = state,
        stateError = stateError
    )
    if (countryError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = countryError.value)
    }
    UserCountryInput(
        country = country,
        countryError = countryError
    )
    if (pincodeError.value.isNotEmpty()) {
        ShowErrorMessage(errorMessage = pincodeError.value)
    }
    UserPincodeInput(
        pincode = pincode,
        pincodeError = pincodeError
    )
}

@Composable
fun UserEmailInput(
    email: MutableState<String>,
    emailError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = email.value,
        onValueChange = {
            email.value = it.lowercase()
            emailError.value = ""
        },
        label = { Text(text = "Enter your email")},
        singleLine = true
    )
}

@Composable
fun UserMobileInput(
    mobileNumber: MutableState<String>,
    mobileNumberError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = mobileNumber.value,
        onValueChange = { userInput ->
            // Allow only digits (numeric input)
            if (userInput.all { it.isDigit() }) {
                mobileNumber.value = userInput
            }
            mobileNumberError.value = ""
        },
        label = { Text(text = "Enter your mobile number") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Opens numeric keyboard
    )
}

@Composable
fun UserNameInput(
    name: MutableState<String>,
    nameError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = name.value,
        onValueChange = {
            name.value = it
            nameError.value = ""
        },
        label = { Text(text = "Enter your name") },
        singleLine = true
    )
}

@Composable
fun UserGenderInput(
    gender: MutableState<String>,
    genderError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = gender.value,
        onValueChange = {
            gender.value = it
            genderError.value = ""
        },
        label = { Text(text = "Enter your gender")},
        singleLine = true
    )
}

@Composable
fun UserAadharNumberInput(
    aadharNumber: MutableState<String>,
    aadharNumberError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = aadharNumber.value,
        onValueChange = { userInput ->
            // Allow only digits (numeric input)
            if (userInput.all { it.isDigit() }) {
                aadharNumber.value = userInput.uppercase()
            }
            aadharNumberError.value = ""
        },
        label = { Text(text = "Enter your aadhar number") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Opens numeric keyboard
    )
}

@Composable
fun UserPanNumberInput(
    panNumber: MutableState<String>,
    panNumberError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = panNumber.value,
        onValueChange = {
            panNumber.value = it.uppercase()
            panNumberError.value = ""
        },
        label = { Text(text = "Enter your pan number")},
        singleLine = true
    )
}

@Composable
fun UserAccountTypeInput(
    accountType: MutableState<String>,
    accountTypeError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = accountType.value,
        onValueChange = {
            accountType.value = it
            accountTypeError.value = ""
        },
        label = { Text(text = "Enter your preferred account type")},
        singleLine = true
    )
}

@Composable
fun UserOccupationInput(
    occupation: MutableState<String>,
    occupationError: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = occupation.value,
        onValueChange = {
            occupation.value = it
            occupationError.value = ""
        },
        label = { Text(text = "Enter your occupation")},
        singleLine = true
    )
}

@Composable
fun UserPasswordInput(
    password: MutableState<String>,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text(text = "Choose your password")},
        singleLine = true
    )
}

@Composable
fun SignUpButton(
    modifier: Modifier,
    name: MutableState<String>,
    gender: MutableState<String>,
    aadharNumber: MutableState<String>,
    panNumber: MutableState<String>,
    mobileNumber: MutableState<String>,
    email: MutableState<String>,
    address: MutableState<String>,
    city: MutableState<String>,
    state: MutableState<String>,
    country: MutableState<String>,
    pincode: MutableState<String>,
    accountType: MutableState<String>,
    occupation: MutableState<String>,
    password: MutableState<String>,
    nameError: MutableState<String>,
    genderError: MutableState<String>,
    aadharNumberError: MutableState<String>,
    panNumberError: MutableState<String>,
    mobileNumberError: MutableState<String>,
    emailError: MutableState<String>,
    addressError: MutableState<String>,
    cityError: MutableState<String>,
    stateError: MutableState<String>,
    countryError: MutableState<String>,
    pincodeError: MutableState<String>,
    accountTypeError: MutableState<String>,
    occupationError: MutableState<String>,
    passwordError: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    Button(
        modifier = modifier,
        onClick = {
            val nameErrorValidation = when {
                name.value.isEmpty() -> "Name can not be empty"
                else -> ""
            }
            nameError.value = nameErrorValidation

            val genderErrorValidation = when {
                gender.value.isEmpty() -> "Gender cannot be empty"
                !gender.value.equals("male", ignoreCase = true) &&
                        !gender.value.equals("female", ignoreCase = true) -> "Gender must be 'Male' or 'Female'"
                else -> ""
            }
            genderError.value = genderErrorValidation

            val aadharNumberErrorValidation = when {
                aadharNumber.value.isEmpty() -> "Aadhar number can not be empty"
                aadharNumber.value.length != 12 -> "Enter a valid 12 digit aadhar number"
                else -> ""
            }
            aadharNumberError.value = aadharNumberErrorValidation

            val panNumberErrorValidation = when {
                panNumber.value.isEmpty() -> "Pan number can not be empty"
                panNumber.value.length != 10 -> "Enter a valid 10 digit pan number"
                else -> ""
            }
            panNumberError.value = panNumberErrorValidation

            val mobileNumberErrorValidation = when {
                mobileNumber.value.isEmpty() -> "Mobile number can't be empty"
                mobileNumber.value.length != 10 -> "Mobile number must be of 10 digit"
                mobileNumber.value.contains(".", ignoreCase = true) -> "Invalid mobile number"
                else -> ""
            }
            mobileNumberError.value = mobileNumberErrorValidation

            val emailErrorValidation = when {
                email.value.isEmpty() -> "Email can't be empty"
                !email.value.contains("@", ignoreCase = true) -> "Email must contain @"
                else -> ""
            }
            emailError.value = emailErrorValidation

            val addressErrorValidation = when {
                address.value.isEmpty() -> "Address can't be empty"
                else -> ""
            }
            addressError.value = addressErrorValidation

            val cityErrorValidation = when {
                city.value.isEmpty() -> "City can't be empty"
                else -> ""
            }
            cityError.value = cityErrorValidation

            val stateErrorValidation = when {
                state.value.isEmpty() -> "State can't be empty"
                else -> ""
            }
            stateError.value = stateErrorValidation

            val countryErrorValidation = when {
                country.value.isEmpty() -> "Country can't be empty"
                else -> ""
            }
            countryError.value = countryErrorValidation

            val pincodeErrorValidation = when {
                pincode.value.isEmpty() -> "Pincode can't be empty"
                pincode.value.length != 6 -> "Pincode must be of 6 digit"
                else -> ""
            }
            pincodeError.value = pincodeErrorValidation

            val accountTypeErrorValidation = when {
                accountType.value.isEmpty() -> "Account type can't be empty"
                !accountType.value.equals("Saving", ignoreCase = true) &&
                        !accountType.value.equals("Salary", ignoreCase = true) -> "Account type must be 'Saving' or 'Salary'"
                else -> ""
            }
            accountTypeError.value = accountTypeErrorValidation

            val occupationErrorValidation = when {
                occupation.value.isEmpty() -> "Occupation can't be empty"
                else -> ""
            }
            occupationError.value = occupationErrorValidation

            val passwordErrorValidation = when {
                password.value.isEmpty() -> "Password can't be empty"
                else -> ""
            }
            passwordError.value = passwordErrorValidation

            if (nameError.value.isEmpty() &&
                genderError.value.isEmpty() &&
                aadharNumberError.value.isEmpty() &&
                panNumberError.value.isEmpty() &&
                mobileNumberError.value.isEmpty() &&
                emailError.value.isEmpty() &&
                addressError.value.isEmpty() &&
                cityError.value.isEmpty() &&
                stateError.value.isEmpty() &&
                countryError.value.isEmpty() &&
                pincodeError.value.isEmpty() &&
                accountTypeError.value.isEmpty() &&
                occupationError.value.isEmpty() &&
                passwordError.value.isEmpty()
                ) {
                val userPersonalDetails = UserPersonalDetails()
                userPersonalDetails.name = name.value
                userPersonalDetails.gender = gender.value
                userPersonalDetails.aadharNumber = aadharNumber.value
                userPersonalDetails.panNumber = panNumber.value
                val userContactDetails = UserContactDetails()
                userContactDetails.mobile = mobileNumber.value
                userContactDetails.email = email.value
                userContactDetails.address.street = address.value
                userContactDetails.address.city = city.value
                userContactDetails.address.state = state.value
                userContactDetails.address.country = country.value
                userContactDetails.address.pincode = pincode.value
                val userAccountDetails = UserAccountDetails()
                userAccountDetails.accountType = accountType.value
                userAccountDetails.occupation = occupation.value
                val userSecurityDetails = UserSecurityDetails()
                userSecurityDetails.password = password.value
                loginViewModel.setLoadingTrue()
                loginViewModel.signUpUserViewModel(
                    userPersonalDetails = userPersonalDetails,
                    userContactDetails = userContactDetails,
                    userAccountDetails = userAccountDetails,
                    userSecurityDetails = userSecurityDetails,
                    path = EndPoints.SIGN_UP_END_PONT,
                    requestCode = RequestCodes.SIGN_UP
                )
            }

        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Sign Up")
    }
}

@Composable
fun ClearUserEnteredDetailsButton(
    modifier: Modifier,
    name: MutableState<String>,
    gender: MutableState<String>,
    aadharNumber: MutableState<String>,
    panNumber: MutableState<String>,
    mobileNumber: MutableState<String>,
    email: MutableState<String>,
    address: MutableState<String>,
    city: MutableState<String>,
    state: MutableState<String>,
    country: MutableState<String>,
    pincode: MutableState<String>,
    accountType: MutableState<String>,
    occupation: MutableState<String>,
    password: MutableState<String>,
    nameError: MutableState<String>,
    genderError: MutableState<String>,
    aadharNumberError: MutableState<String>,
    panNumberError: MutableState<String>,
    mobileNumberError: MutableState<String>,
    emailError: MutableState<String>,
    addressError: MutableState<String>,
    cityError: MutableState<String>,
    stateError: MutableState<String>,
    countryError: MutableState<String>,
    pincodeError: MutableState<String>,
    accountTypeError: MutableState<String>,
    occupationError: MutableState<String>,
    passwordError: MutableState<String>
) {
    Button(
        modifier = modifier,
        onClick = {
            name.value = ""
            gender.value = ""
            aadharNumber.value = ""
            panNumber.value = ""
            mobileNumber.value = ""
            email.value = ""
            address.value = ""
            city.value = ""
            state.value = ""
            country.value = ""
            pincode.value = ""
            accountType.value = ""
            occupation.value = ""
            password.value = ""
            nameError.value = ""
            genderError.value = ""
            aadharNumberError.value = ""
            panNumberError.value = ""
            mobileNumberError.value = ""
            emailError.value = ""
            addressError.value = ""
            cityError.value = ""
            stateError.value = ""
            countryError.value = ""
            pincodeError.value = ""
            accountTypeError.value = ""
            occupationError.value = ""
            passwordError.value = ""
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.buttonUnselectedColor)
        )
    ) {
        Text(text = "Clear Entry")
    }
}

@Composable
fun SignUpAndClearButton(
    name: MutableState<String>,
    gender: MutableState<String>,
    aadharNumber: MutableState<String>,
    panNumber: MutableState<String>,
    mobileNumber: MutableState<String>,
    email: MutableState<String>,
    address: MutableState<String>,
    city: MutableState<String>,
    state: MutableState<String>,
    country: MutableState<String>,
    pincode: MutableState<String>,
    accountType: MutableState<String>,
    occupation: MutableState<String>,
    password: MutableState<String>,
    nameError: MutableState<String>,
    genderError: MutableState<String>,
    aadharNumberError: MutableState<String>,
    panNumberError: MutableState<String>,
    mobileNumberError: MutableState<String>,
    emailError: MutableState<String>,
    addressError: MutableState<String>,
    cityError: MutableState<String>,
    stateError: MutableState<String>,
    countryError: MutableState<String>,
    pincodeError: MutableState<String>,
    accountTypeError: MutableState<String>,
    occupationError: MutableState<String>,
    passwordError: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
    ) {
        SignUpButton (
            modifier = Modifier.weight(1f),
            name = name,
            gender = gender,
            aadharNumber = aadharNumber,
            panNumber = panNumber,
            mobileNumber = mobileNumber,
            email = email,
            address = address,
            city = city,
            state = state,
            country = country,
            pincode = pincode,
            accountType = accountType,
            occupation = occupation,
            password = password,
            nameError = nameError,
            genderError = genderError,
            aadharNumberError = aadharNumberError,
            panNumberError = panNumberError,
            mobileNumberError = mobileNumberError,
            emailError = emailError,
            addressError = addressError,
            cityError = cityError,
            stateError = stateError,
            countryError = countryError,
            pincodeError = pincodeError,
            accountTypeError = accountTypeError,
            occupationError = occupationError,
            passwordError = passwordError,
            loginViewModel = loginViewModel
        )
        SetSpaceHorizontallyOrVertically(height = false, value = 5.dp)
        ClearUserEnteredDetailsButton(
            modifier = Modifier.weight(1f),
            name = name,
            gender = gender,
            aadharNumber = aadharNumber,
            panNumber = panNumber,
            mobileNumber = mobileNumber,
            email = email,
            address = address,
            city = city,
            state = state,
            country = country,
            pincode = pincode,
            accountType = accountType,
            occupation = occupation,
            password = password,
            nameError = nameError,
            genderError = genderError,
            aadharNumberError = aadharNumberError,
            panNumberError = panNumberError,
            mobileNumberError = mobileNumberError,
            emailError = emailError,
            addressError = addressError,
            cityError = cityError,
            stateError = stateError,
            countryError = countryError,
            pincodeError = pincodeError,
            accountTypeError = accountTypeError,
            occupationError = occupationError,
            passwordError = passwordError
        )
    }
}

@Composable
fun UserAddressInput(
    address: MutableState<String>,
    addressError: MutableState<String>
) {
    OutlinedTextField(
        value = address.value,
        onValueChange = {
            address.value = it
            addressError.value = ""
        },
        label = { Text("Enter your address") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun UserCityInput(
    city: MutableState<String>,
    cityError: MutableState<String>
) {
    OutlinedTextField(
        value = city.value,
        onValueChange = {
            city.value = it
            cityError.value = ""
        },
        label = { Text("Enter your city") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun UserStateInput(
    state: MutableState<String>,
    stateError: MutableState<String>
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
            stateError.value = ""
        },
        label = { Text("Enter your state") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun UserCountryInput(
    country: MutableState<String>,
    countryError: MutableState<String>
) {
    OutlinedTextField(
        value = country.value,
        onValueChange = {
            country.value = it
            countryError.value = ""
        },
        label = { Text("Enter your country") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun UserPincodeInput(
    pincode: MutableState<String>,
    pincodeError: MutableState<String>
) {
    OutlinedTextField(
        value = pincode.value,
        onValueChange = {
            if (it.all { char -> char.isDigit() }) pincode.value = it
            pincodeError.value = ""
        },
        label = { Text("Enter your pincode") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Opens numeric keyboard
    )
}

@Composable
fun ObserveLiveDataInSignUpPage(
    navController: NavController,
    loginViewModel: LoginViewModel,
    emailError: MutableState<String>,
    mobileNumberError: MutableState<String>,
    panNumberError: MutableState<String>,
    aadharNumberError: MutableState<String>
) {

    val context = LocalContext.current
    val signUpSuccessResponse by loginViewModel.getSighUpSuccessLiveData().observeAsState()
    val signUpFailedResponse by loginViewModel.getSignUpFailedLiveDataResponse().collectAsState()

    signUpSuccessResponse?.let { response ->
        if (response.message.contains("success", ignoreCase = true)) {
            LaunchedEffect(Unit) {
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

    signUpFailedResponse?.let { response ->
        if (response.message.isNotEmpty() && response.code != -1) {
            LaunchedEffect(Unit) {
                if (response.errorCode == ErrorCodesAndMessages.SIGN_UP_CONFLICT.errorCode) {
                    response.conflicts.forEach { conflictField ->
                        if (conflictField.errorCode == ErrorCodesAndMessages.MOBILE_NUMBER_CONFLICT.errorCode) {
                            mobileNumberError.value = ErrorCodesAndMessages.MOBILE_NUMBER_CONFLICT.errorMessage
                        }
                        if (conflictField.errorCode == ErrorCodesAndMessages.EMAIL_CONFLICT.errorCode) {
                            emailError.value = ErrorCodesAndMessages.EMAIL_CONFLICT.errorMessage
                        }
                        if (conflictField.errorCode == ErrorCodesAndMessages.PAN_CONFLICT.errorCode) {
                            panNumberError.value = ErrorCodesAndMessages.PAN_CONFLICT.errorMessage
                        }
                        if (conflictField.errorCode == ErrorCodesAndMessages.AADHAR_CONFLICT.errorCode) {
                            aadharNumberError.value = ErrorCodesAndMessages.AADHAR_CONFLICT.errorMessage
                        }
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                loginViewModel.setApiFailedResponseToEmpty()
                loginViewModel.setLoadingFalse()
            }
        }
    }
}
