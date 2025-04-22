package com.jain.yourownbank.screens.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jain.yourownbank.R


@Composable
fun ShowFullScreenCustomLoader(animationRes: Int) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    Box(
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
        )
    }
}