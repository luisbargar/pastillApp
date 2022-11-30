package es.luisbarreiros.proyecto.pastillApp.ui.home

import androidx.lifecycle.*
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.entities.Toma
import es.luisbarreiros.proyecto.pastillApp.database.entities.Stock
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {


    private val medicacionDao = App.getDatabase().medicacionDao() //inicializamos los tres Daos
    private val tomaDao = App.getDatabase().tomaDao()
    private val stockDao = App.getDatabase().stockDao()

    fun medicaciones(tipo:TipoMedicacion): LiveData<List<MedicacionCompleta>> { //función para recuperar todos los datos dependiendo del tipo de medicación
        return when (tipo.tipo) {
            1 -> medicacionDao.findAll(App.getUsuario()!!.id).asLiveData()
            2 -> medicacionDao.findAllToma(App.getUsuario()!!.id).asLiveData()
            else -> medicacionDao.findAllStock(App.getUsuario()!!.id).asLiveData()
        }
    }
    fun addToma(medicacionId: Long) { //función de añadir a la lista que toma el usuario
        viewModelScope.launch {withContext(Dispatchers.IO) {
                tomaDao.save(Toma(medicacionId, App.getUsuario()!!.id))
            }
        }
    }
    fun delToma(medicacionId: Long) { //Borrar de la lista que toma el usuario
        viewModelScope.launch {withContext(Dispatchers.IO) {
                tomaDao.delete(Toma(medicacionId, App.getUsuario()!!.id))
            }
        }
    }
    fun addStock(medicacionId: Long) { //añadir a la lista de medicación que hay que comprar
        viewModelScope.launch {withContext(Dispatchers.IO) {
                stockDao.save(Stock(medicacionId, App.getUsuario()!!.id))
            }
        }
    }
    fun delStock(medicacionId: Long) { //borrar de la lista de medicación que hay que comprar
        viewModelScope.launch {withContext(Dispatchers.IO) {
                stockDao.delete(Stock(medicacionId, App.getUsuario()!!.id))
            }
        }
    }
}