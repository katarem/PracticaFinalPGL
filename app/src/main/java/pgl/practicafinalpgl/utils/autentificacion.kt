package pgl.practicafinalpgl.utils
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import io.github.katarem.piratify.pantallas.PantallaLogin
import pgl.practicafinalpgl.Rutas.Rutas.PantallaReproductor

class autentificacion {

    fun loginUser(context: Context, email: String, password: String) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Se ha logueado correctamente!", Toast.LENGTH_LONG).show()

                    val intent = Intent(context, PantallaReproductor::class.java)
                    context.startActivity(intent)
                } else {
                    val errorMessage = "Ha habido algún problema: ${task.exception?.localizedMessage}"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

}