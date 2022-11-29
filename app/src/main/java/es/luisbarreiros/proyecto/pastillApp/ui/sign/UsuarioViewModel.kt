package es.luisbarreiros.proyecto.pastillApp.ui.sign

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.entities.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel : ViewModel() {//viewModel es una capa adicional para guardar los datos. No está unido
//a la actividad y funciona independientemente. si la actividad se destruye esto permanece.
    private val db = App.getDatabase()
    fun save(usuario: Usuario): MutableLiveData<Boolean> { //devuelve el mutableLiveData, un tipo de variable
        //que nos devuelve un valor en el tiempo. Para ejecuciones asincronas.
        val data = MutableLiveData<Boolean>()
        viewModelScope.launch {//abro la corrutina
            try {
                val id = withContext(Dispatchers.IO) {//usamos el contexto de dispatchers.IO. El hilo de ejecición de entrada y salida de datos.
                    db.usuarioDao().save(usuario) //guardamos a la función save dentro de usuarioDao
                }
                usuario.id = id //establecemos el id recuperado de la base de datos.
                App.saveUser(usuario) //lo guardamos en saveUser
                data.value = true
            } catch (ex: SQLiteConstraintException) {
                data.value = false //si el usuario ya existe
            }
        }
        return data //devolvemos el data
    }

    fun login(usuario: Usuario): MutableLiveData<Result> {
        val data = MutableLiveData<Result>()
        viewModelScope.launch {//abro la corrutina
            try {
                val usuarioFromDB: Usuario? = withContext(Dispatchers.IO) {//usamos el contexto de dispatchers.IO. El hilo de ejecición de entrada y salida de datos.
                    db.usuarioDao().findOneByName(usuario.nombre) //buscamos en usuarioDao el nombre edl usuario
                }
                usuarioFromDB?.let {
                    if (it.password == usuario.password) { //si las contraseñas coinciden devolvemos true y guardamos
                        App.saveUser(usuarioFromDB)
                        data.value = Result(true)
                    } else
                        data.value = Result(false, "Revisa los datos introducidos") //si las contraseñas fallan hay que revisar los datos
                } ?: run {
                    data.value = Result(false, "Revisa los datos introducidos") //usuario en blanco
                }
            } catch (ex: SQLiteConstraintException) {
                data.value = Result(false, "Revisa los datos introducidos") //si no encuentra un usuario
            //mismo mensaje para todos los fallos para no dar pistas si es
                //una persona ajena que quiere entrar en nuestra aplicación.
            }
        }
        return data
    }
}
data class Result(val result: Boolean, val msg: String = "") //lo que devuelve al haber un error con un string personalizado
