package pgl.practicafinalpgl.pantallas

import android.content.Context
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
@OptIn(ExperimentalComposeUiApi::class)
class LoginViewModel: ViewModel() {

    fun addUIFunctions(context: Context, manager: FocusManager, controller: SoftwareKeyboardController?){
        _context.value = context
        _focusManager.value = manager
        _keyboardController.value = controller
    }

    //context
    private var _context = MutableStateFlow<Context?>(null)
    val contexto = _context.asStateFlow()
    //focusManager
    private var _focusManager = MutableStateFlow<FocusManager?>(null)
    val focusManager = _focusManager.asStateFlow()
    //keyboardController
    private var _keyboardController = MutableStateFlow<SoftwareKeyboardController?>(null)
    val keyboardController = _keyboardController.asStateFlow()
    //username
    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    //password
    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private var _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()
    //error
    private var _errorText = MutableStateFlow("")
    val errorText = _errorText.asStateFlow()
    //login o sign in
    private var _isLogin = MutableStateFlow(true)
    val isLogin = _isLogin.asStateFlow()
    fun setPassword(new: String){ _password.value = new }
    fun getPassword(): String{ return _password.value } //asi solo lo usamos para cuando la contraseña esté completa
    fun setConfirmPassword(new: String){ _confirmPassword.value = new }
    fun getConfirmPassword(): String{ return _confirmPassword.value }
    fun isContrasenyaConfirmed(): Boolean { return _password.value == _confirmPassword.value }
    fun setUserName(new: String) { _username.value = new }
    fun getUserName(): String{ return _username.value }

    fun setErrorText(new: String){ _errorText.value = new }
    fun getErrorText(): String{ return _errorText.value }

    fun changeLoginState(newValue: Boolean){ _isLogin.value = newValue }

}