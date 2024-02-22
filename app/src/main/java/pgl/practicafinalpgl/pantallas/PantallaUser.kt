package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pgl.practicafinalpgl.R
import pgl.practicafinalpgl.pantallas.Logo
import pgl.practicafinalpgl.pantallas.TextoPiratify
import pgl.practicafinalpgl.utils.AppColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaUser() {
    val userData = listOf(
        "Username" to "test2",
        "Correo" to "Correo@correo.com",
        "Seguidores" to "0",
        "Siguiendo" to "0",
        "Role" to "USER",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.negro)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Información de Usuario",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            UserPortrait()
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.size(100.dp)){
                Logo()
            }
            TextoPiratify(22.sp)

        }
        ContenidoUser(userData = userData)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoUser(userData: List<Pair<String, String>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (data in userData) {
            UserDataRow(name = data.first, value = data.second)
        }
    }
}

@Composable
fun UserDataRow(name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = AppColors.verde,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaUserPreview() {
    PantallaUser()
}

@Composable
fun UserPortrait() {
    val borderColor = AppColors.verde

    Image(
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
            .clip(CircleShape)
            .border(width = 6.dp, color = borderColor, shape = CircleShape),
    )
}

