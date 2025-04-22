package com.jain.yourownbank.screens.transaction

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.SetHeader
import com.jain.yourownbank.utils.ShowKeyValuePair

@Composable
fun YOBQRCodeScreen() {
    val scrollableState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val panNumber = Constants.USER.personalDetails.panNumber
        val aadharNumber = Constants.USER.personalDetails.aadharNumber
        val mobileNumber = Constants.USER.contactDetails.mobile
        val upiId = Constants.USER_COMPLETE_BANK_DETAILS.userUpiDetails.upiId
        val email = Constants.USER.contactDetails.email
        val isYOBQRCode = true
        val qrData = "PAN: $panNumber\nAadhar: $aadharNumber\nMobile: $mobileNumber\nUPI: $upiId\nEmail: $email\nisYOBQRCode: $isYOBQRCode"


        val bitmap = remember(qrData) {
            generateQRCode(qrData)
        }
        Column (
            modifier = Modifier.verticalScroll(scrollableState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SetHeader(heading = "Your YOB QR Code")
            if (bitmap != null) SetYOBQRCodeToImageComposable(bitmap = bitmap)
            SetHeader(heading = "Your QR Details")
            ShowKeyValuePair(key = "UPI ID", value = upiId)
            ShowKeyValuePair(key = "Mobile Number", value = mobileNumber)
            ShowKeyValuePair(key = "Aadhar Number", value = aadharNumber)
            ShowKeyValuePair(key = "Pan Number", value = panNumber)
            ShowKeyValuePair(key = "Email ID", value = email)
        }
    }
}


@Composable
fun SetYOBQRCodeToImageComposable(bitmap: Bitmap) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "QR Code",
        modifier = Modifier
            .size(400.dp)
            .padding(horizontal = 5.dp, vertical = 0.dp)
    )
}


@SuppressLint("ResourceAsColor")
fun generateQRCode(data: String): Bitmap? {
    return try {
        val size = 512
        val bits = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bits[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
