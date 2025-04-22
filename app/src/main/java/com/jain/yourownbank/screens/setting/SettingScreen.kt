package com.jain.yourownbank.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jain.yourownbank.R
import com.jain.yourownbank.utils.Constants
import com.jain.yourownbank.utils.SetHeader

@Composable
fun SettingScreen(navController: NavController) {
    Box (
        modifier = Modifier
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetHeader(heading = "Setting")
            SettingList(navController)
        }
    }
}


@Composable
fun SettingList(navController: NavController) {
    val services = Constants.SETTING_LIST

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
                    .background(colorResource(id = R.color.buttonUnselectedColor), RoundedCornerShape(8.dp))
                    .clickable { navController.navigate(service.route) }
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