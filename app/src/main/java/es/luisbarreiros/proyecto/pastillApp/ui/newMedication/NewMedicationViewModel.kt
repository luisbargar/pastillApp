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



    fun save(medicacion: Medicacion): LiveData<Long> {
        val data = MutableLiveData<Long>()
        viewModelScope.launch {
            try {
                val id = withContext(Dispatchers.IO) {
                    medicacionDao.save(medicacion)
                }
                data.value = id
            } catch (ex: SQLiteConstraintException) {
                data.value = -1
            }
        }
        return data
    }




    fun addHorario(sistema: Long, dispositivo: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                horarioDao.save(Horario(sistema, dispositivo))
            }
        }
    }

    fun update(medicacion: Medicacion): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    medicacionDao.update(medicacion)
                    data.postValue(true)
                } catch (ex: SQLiteConstraintException) {
                    data.postValue(false)
                }
            }
        }
        return data
    }

    fun delHorario(sistema: Long, id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                horarioDao.delete(Horario(sistema, id))
            }
        }
    }

    fun eliminar(medicacion: Medicacion) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                medicacionDao.delete(medicacion)
            }
        }

    }


}