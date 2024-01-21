package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pgl.practicafinalpgl.R
import pgl.practicafinalpgl.utils.AppColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaLogin(onLoginClick: () -> Unit, onSinginClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.negro)
            .padding(16.dp)
    ) {
        ContenidoLogin(username = username,
            onUsernameChange = { username = it },
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = onLoginClick,
            onSinginClick = onSinginClick,
            focusManager = focusManager,
            keyboardController = keyboardController
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoLogin(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSinginClick: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()

        TextoPiratify()

        EntradaUsuario(
            username = username, onUsernameChange = onUsernameChange, focusManager = focusManager
        )

        EntradaContraseña(
            password = password,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick,
            keyboardController = keyboardController
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonIniciarSesion(onLoginClick = onLoginClick)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            TextoRegistro(onSigninClick = onSinginClick)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextoContinuarCon()

        Spacer(modifier = Modifier.height(8.dp))

        RedesSociales()
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaLoginPreview() {
    PantallaLogin(onLoginClick = {}, onSinginClick = {})
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.piratify),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
}

@Composable
fun TextoPiratify() {
    Text(
        text = "Piratify",
        fontSize = 42.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun EntradaUsuario(
    username: String, onUsernameChange: (String) -> Unit, focusManager: FocusManager
) {
    OutlinedTextField(value = username,
        onValueChange = { onUsernameChange(it) },
        label = { Text(text = "Usuario") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        })
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EntradaContraseña(
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    OutlinedTextField(value = password,
        onValueChange = { onPasswordChange(it) },
        label = { Text(text = "Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            onLoginClick()
            keyboardController?.hide()
        })
    )
}

@Composable
fun BotonIniciarSesion(onLoginClick: () -> Unit) {
    Button(
        onClick = { onLoginClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = AppColors.verde
        )
    ) {
        Text(text = "Iniciar sesión")
    }
}

@Composable
fun TextoRegistro(onSigninClick: () -> Unit) {
    Row {
        Text(
            text = "No tienes una cuenta? ", color = Color.DarkGray
        )
        Text(text = "Registrame",
            color = AppColors.verde,
            modifier = Modifier.clickable { onSigninClick() })
    }
}

@Composable
fun TextoContinuarCon() {
    Text(text = "Continúa también con", color = Color.DarkGray)
}

@Composable
fun RedSocial(imageRes: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(color = AppColors.verde, shape = CircleShape)
            .padding(2.dp), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color = AppColors.negro, shape = CircleShape)
        )
    }
}

@Composable
fun RedesSociales() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        RedSocial(imageRes = R.drawable.googlelogo)
        RedSocial(imageRes = R.drawable.twitterlogo)
        RedSocial(imageRes = R.drawable.logo_cl_ve)
    }
}
