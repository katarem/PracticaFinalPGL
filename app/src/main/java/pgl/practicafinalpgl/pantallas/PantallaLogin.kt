package pgl.practicafinalpgl.pantallas

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pgl.practicafinalpgl.R
import pgl.practicafinalpgl.utils.AppColors
import pgl.practicafinalpgl.utils.loginUser
import pgl.practicafinalpgl.utils.registerUser

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaLogin() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val loginViewModel: LoginViewModel = viewModel()
    loginViewModel.addUIFunctions(context, focusManager, keyboardController)
    val isLogin = loginViewModel.isLogin.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.negro)
            .padding(16.dp)
    ) {
        if (isLogin.value)
            ContenidoLogin(model = loginViewModel)
        else
            ContenidoSingIn(model = loginViewModel)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoLogin(model: LoginViewModel) {

    val errorText = model.errorText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        TextoPiratify()
        EntradaUsuario(viewModel = model)
        EntradaContrasenya(model = model)
        Text(
            text = errorText.value,
            color = Color.Red,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        BotonEnviarFormulario(onClick = { model.changeLoginState(true) }, "Iniciar sesion")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            TextoRedirigir("No tienes una cuenta?", "Registrame")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Continúa también con", color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))
        RedesSociales()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoSingIn(
    model: LoginViewModel
) {
    val errorText = model.errorText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        TextoPiratify()
        EntradaUsuario(viewModel = model)
        RegistrarContrasenya(model = model)
        Text(
            text = errorText.value,
            color = Color.Red,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        BotonEnviarFormulario(
            onClick = {
                if (!model.isContrasenyaConfirmed()) model.setErrorText("Las contraseñas no son iguales")
                else registerUser(
                    model.contexto.value!!,
                    model.getUserName(),
                    model.getPassword(),
                    onError = { model.setErrorText("Hubo error con el registro") })
            },
            "Registrarse"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            TextoRedirigir("Ya tengo una cuenta, ", "Iniciar sesion")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PantallaLoginPreview() {
    PantallaLogin()
}


@Composable
fun Logo() {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.piratify),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
            .clickable { loginUser(context, "testing@testing.es", "testing", onError = {}) }
    )
}

@Composable
fun TextoPiratify(size: TextUnit = 42.sp) {
    Text(
        text = "Piratify",
        fontSize = size,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun EntradaUsuario(viewModel: LoginViewModel) {

    val username = viewModel.username.collectAsState()
    val focusManager = viewModel.focusManager.collectAsState()

    OutlinedTextField(
        value = username.value,
        onValueChange = { viewModel.setUserName(it) },
        label = { Text(text = "Usuario") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.value!!.moveFocus(FocusDirection.Down)
        })
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EntradaContrasenya(model: LoginViewModel) {

    val password = model.password.collectAsState()
    val keyboardController = model.keyboardController.collectAsState()
    val context = model.contexto.collectAsState()

    OutlinedTextField(
        value = password.value,
        onValueChange = { model.setPassword(it) },
        label = { Text(text = "Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            loginUser(context.value!!, model.getUserName(), model.getPassword()) {
                model.setErrorText("Correo o contraseña inválidos")
            }
            keyboardController.value?.hide()
        })
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrarContrasenya(model: LoginViewModel) {

    val password = model.password.collectAsState()
    val confirmPassword = model.confirmPassword.collectAsState()
    val focusManager = model.focusManager.collectAsState()
    val keyboardController = model.keyboardController.collectAsState()
    val context = model.contexto.collectAsState()

    OutlinedTextField(
        value = password.value,
        onValueChange = { model.setPassword(it) },
        label = { Text(text = "Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.value!!.moveFocus(FocusDirection.Down)
        })
    )
    OutlinedTextField(
        value = confirmPassword.value,
        onValueChange = { model.setConfirmPassword(it) },
        label = { Text(text = "Repetir Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (model.isContrasenyaConfirmed()) {
                registerUser(context = context.value!!,
                    email = model.getUserName(),
                    password = model.getPassword(),
                    onError = { model.setErrorText("Correo o contraseña inválidos") })
            } else {
                model.setErrorText("Las contraseñas no coinciden")
                keyboardController.value?.hide()
            }
        })
    )
}


@Composable
fun BotonEnviarFormulario(onClick: () -> Unit, textoBoton: String) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = AppColors.verde
        )
    ) {
        Text(text = textoBoton)
    }
}

@Composable
fun TextoRedirigir(textoInformacion: String, textoRedirigir: String) {
    val loginViewModel: LoginViewModel = viewModel()
    val isLogin = loginViewModel.isLogin.collectAsState()
    Row {
        Text(text = textoInformacion, color = Color.DarkGray)
        Text(text = textoRedirigir,
            color = AppColors.verde,
            modifier = Modifier.clickable { loginViewModel.changeLoginState(!isLogin.value) })
    }
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
