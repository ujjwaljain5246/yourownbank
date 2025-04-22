package com.jain.yourownbank.screens.auth

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.screensRoutes.ScreensRoutes

@Composable
fun LogoutScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(true) }

    LogoutDialog(
        navController = navController,
        showDialog = showDialog,
        keepShowingDialog = { showDialog = true },
        onDismiss = { showDialog = false },
        onConfirm = { showDialog = false }
    )
}

@Composable
fun LogoutDialog(
    navController: NavController,
    showDialog: Boolean,
    keepShowingDialog: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = keepShowingDialog,
            title = { Text(text = "Logout") },
            text = { Text(text = "Do you really want to logout?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor)),
                    onClick = {
                    onConfirm()
                    navController.navigate(ScreensRoutes.SignInScreen.route) {
                        popUpTo(0) {inclusive = true}
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonUnselectedColor)),
                    onClick = {
                    onDismiss()
                    navController.navigate(ScreensRoutes.SettingScreen.route) {
                        popUpTo(route = ScreensRoutes.SettingScreen.route) {inclusive = true}
                    }
                }) {
                    Text("No")
                }
            }
        )
    }
}