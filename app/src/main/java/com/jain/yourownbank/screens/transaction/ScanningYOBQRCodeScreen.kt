package com.jain.yourownbank.screens.transaction

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.jain.yourownbank.screensRoutes.ScreensRoutes
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanningYOBQRCodeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { scannedResult ->
            if (scannedResult.contents != null) {
                val isYOBQRCode = scannedResult.contents.lines().find { it.startsWith("isYOBQRCode:") }?.split(":")?.get(1)?.trim()
                if (isYOBQRCode?.equals("true", ignoreCase = true) == true) {
                    val upiId = scannedResult.contents.lines().find { it.startsWith("UPI:") }?.split(":")?.get(1)?.trim()
                    val modeOfTransaction = "UPI"
                    // Pass result back using savedStateHandle
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("upiId", upiId)
                        set("modeOfTransaction", modeOfTransaction)
                    }
                    navController.navigateUp()
                } else {
                    Toast.makeText(context, "Please scan a valid YOB QR Code", Toast.LENGTH_SHORT).show()
                    navController.navigate(ScreensRoutes.SendMoneyScreen.route) {
                        popUpTo(ScreensRoutes.SendMoneyScreen.route) {inclusive = true}
                    }
                }
            } else {
                Toast.makeText(context, "Scanning cancelled", Toast.LENGTH_SHORT).show()
                navController.navigate(ScreensRoutes.SendMoneyScreen.route) {
                    popUpTo(ScreensRoutes.SendMoneyScreen.route) {inclusive = true}
                }
            }
            isScanning = false
        }
    )

    LaunchedEffect(isScanning) {
        if (isScanning) {
            val options = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                setPrompt("Scanning YOB QR for you")
                setCameraId(0) // 0 for rear camera
                setBeepEnabled(true)
                setBarcodeImageEnabled(true)
                setOrientationLocked(true)
            }
            launcher.launch(options)
        }
    }
}




