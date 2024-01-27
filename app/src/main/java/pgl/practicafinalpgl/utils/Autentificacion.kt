package pgl.practicafinalpgl.utils
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import pgl.practicafinalpgl.Rutas.Rutas.PantallaReproductor
import pgl.practicafinalpgl.db.DBViewModel
import pgl.practicafinalpgl.db.UserRepository
import pgl.practicafinalpgl.model.login.User
import pgl.practicafinalpgl.pantallas.PantallaPrueba
import pgl.practicafinalpgl.pantallas.Testing

fun loginUser(context: Context, email: String, password: String,onError: () -> Unit) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(context, Testing::class.java)
                context.startActivity(intent)
            } else onError()
        }
}

fun registerUser(context: Context, email: String,password: String, onError: () -> Unit){
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                saveUser(auth.currentUser!!.uid,email);
                val intent = Intent(context, PantallaReproductor::class.java)
                context.startActivity(intent)
            } else onError()
        }
}

fun saveUser(id: String, email: String){
    val repoUsuario = UserRepository()
    val userName = email.split("@")[0]
    val nuevoUsuario = User(id)
    nuevoUsuario.nickname = userName
    repoUsuario.insert(nuevoUsuario)
}
