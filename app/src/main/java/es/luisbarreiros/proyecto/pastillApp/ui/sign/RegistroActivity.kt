package es.luisbarreiros.proyecto.pastillApp.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.entities.Usuario
import es.luisbarreiros.proyecto.pastillApp.databinding.ActivityRegistroBinding
import es.luisbarreiros.proyecto.pastillApp.ui.HomeActivity


class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding //establezco el viewBinding
    private val viewModel: UsuarioViewModel by viewModels()//viewModel es una capa adicional para guardar los datos. No está unido
    //a la actividad y funciona independientemente.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater) //declaro la variable binding para poder utilizarlo
        setContentView(binding.root)
    }

    override fun onStart() { //defino los setOnClickListener
        super.onStart()
        binding.cancelar.setOnClickListener { //pertenece al boton cancelar del registro
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.registro.setOnClickListener {//pertenece al registro
            registro()
        }
    }

    private fun registro() { //funcion de registro, con su nombre, y dos paswords.
        val nombre = binding.tieUsername.text.toString()
        val password = binding.tiePassword.text.toString()
        val password2 = binding.tieConfirmPassword.text.toString()
        if (nombre.isBlank() || password.isBlank() || password2.isBlank()) { //si hay datos introducidos en blanco nos avisa.
            App.showSnackbar(binding.root, getString(R.string.rellenar_campos)) //snackbar
            return //volvemos para que la función no siga ejecutándose
        }

        if (password != password2) { //las dos contraseñas tienen que ser iguales.
            App.showSnackbar(binding.root, getString(R.string.contrasenyas_no_coinciden)) //snackbar
            return //volvemos para que la funcón no siga ejecutándose
        }
        viewModel.save(Usuario(nombre, password)).observe(this) { //devuelve booleano
            if (!it) {
                App.showSnackbar(binding.root, getString(R.string.error_crear_usuario)) //si es falso, y no se puede crear lo que devuelve.
            } else {
                startActivity(Intent(this, HomeActivity::class.java)) //si es true iniciamos el HomeActivity
                finish()
            }
        }
    }
}