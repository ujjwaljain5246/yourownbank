package com.jain.yourownbank.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jain.yourownbank.R

@Composable
fun SetSpaceHorizontallyOrVertically(height: Boolean, value : Dp) {
    if (height) {
        Spacer(modifier = Modifier.height(value))
    } else {
        Spacer(modifier = Modifier.width(value))
    }
}

@Composable
fun SetHeader(heading: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.buttonUnselectedColor))
            .padding(10.dp),
        text = heading,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        style = TextStyle(
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(4f, 4f),
                blurRadius = 8f
            )
        )
    )
}

@Composable
fun ShowErrorMessage(
    errorMessage : String
) {
    Text(
        text = errorMessage,
        color = Color.Red,
        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
    )
}


@Composable
fun ShowKeyValuePair(key: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = key,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = " : ",
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = value,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
