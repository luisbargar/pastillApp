package es.luisbarreiros.proyecto.pastillApp.ui.newMedication

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewMedicationViewModel : ViewModel() {

    private val horarioDao = App.getDatabase().horarioDao()
    private val medicacionDao = App.getDatabase().medicacionDao()
    fun save(medicacion: Medicacion): LiveData<Long> { //función para guardar una medicación
        val data = MutableLiveData<Long>()
        viewModelScope.launch {
            try { val id = withContext(Dispatchers.IO) {
                    medicacionDao.save(medicacion)
                }
                data.value = id
            } catch (ex: SQLiteConstraintException) {
                data.value = -1
            }
        }
        return data
    }
    fun update(medicacion: Medicacion): LiveData<Boolean> { //función para actualizar medicación
        val data = MutableLiveData<Boolean>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try { medicacionDao.update(medicacion)
                    data.postValue(true)
                } catch (ex: SQLiteConstraintException) {
                    data.postValue(false)
                }
            }
        }
        return data
    }

    fun eliminar(medicacion: Medicacion) { //función para eliminar una medicación
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                medicacionDao.delete(medicacion)
            }
        }

    }
    fun addHorario(sistema: Long, medicacion: Long) { //función para añadir un horario
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                horarioDao.save(Horario(sistema, medicacion))
            }
        }
    }
    fun delHorario(sistema: Long, id: Long) { //función para borrar un horario
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                horarioDao.delete(Horario(sistema, id))
            }
        }
    }
}