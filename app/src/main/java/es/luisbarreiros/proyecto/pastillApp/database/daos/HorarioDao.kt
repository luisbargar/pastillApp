package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Horario

@Dao
interface HorarioDao : BaseDao<Horario> {//heredamos de BaseDao

    @Query("DELETE FROM horarios where id_medicacion=:id") //borro los horarios de una deteminada medicación
    fun deleteByMedicationId(id: Long)
}