package es.luisbarreiros.proyecto.pastillApp.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.entities.Usuario
import es.luisbarreiros.proyecto.pastillApp.databinding.ActivityLoginBinding
import es.luisbarreiros.proyecto.pastillApp.ui.HomeActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding //generamos view Binding
    private val viewModel: UsuarioViewModel by viewModels() //inicializamos VIewModel individualizado.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) //inicializamos binding
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        val usuario = App.getUsuario()
        usuario?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.login.setOnClickListener { //funcion login vinculadas al boton acceso
            login()
        }
        binding.registro.setOnClickListener {//funcion login vinculada al boton registro
            registro()
        }
    }
    private fun login() {
        val nombre = binding.tieUsername.text.toString()
        val password = binding.tiePassword.text.toString()
        if (nombre.isBlank() || password.isBlank()) {
            App.showSnackbar(binding.root, getString(R.string.rellenar_campos))
            return
        }//comprueba si login y password estan en blanco
        viewModel.login(Usuario(nombre, password)).observe(this) {
            if (!it.result) {
                App.showSnackbar(binding.root, it.msg)
            } else {
                startActivity(Intent(this, HomeActivity::class.java)) //inicia el homeActivity
                finish() //finalizamos para que no ocupe espacio de la pila de memoria
            }
        }
    }
    private fun registro() {
        startActivity(Intent(this, RegistroActivity::class.java)) //vamos a la nueva actividad
        finish() //finalizamos para que no se quede en la pila de memoria
    }
}