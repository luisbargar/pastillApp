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

    private val medicacionDao = App.getDatabase().medicacionDao()
    private val tomaDao = App.getDatabase().tomaDao()
    private val stockDao = App.getDatabase().stockDao()

    fun medicaciones(tipo:TipoMedicacion): LiveData<List<MedicacionCompleta>> {
        return when (tipo.tipo) {
            1 -> medicacionDao.findAll(App.getUsuario()!!.id).asLiveData()
            2 -> medicacionDao.findAllToma(App.getUsuario()!!.id).asLiveData()
            else -> medicacionDao.findAllStock(App.getUsuario()!!.id).asLiveData()
        }
    }

    fun addToma(medicacionId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tomaDao.save(Toma(medicacionId, App.getUsuario()!!.id))
            }
        }
    }

    fun delToma(medicacionId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tomaDao.delete(Toma(medicacionId, App.getUsuario()!!.id))
            }
        }
    }

    fun addStock(medicacionId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                stockDao.save(Stock(medicacionId, App.getUsuario()!!.id))
            }
        }
    }

    fun delStock(medicacionId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                stockDao.delete(Stock(medicacionId, App.getUsuario()!!.id))
            }
        }
    }
}