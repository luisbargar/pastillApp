package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Horario

@Dao
interface HorarioDao : BaseDao<Horario> {

    @Query("DELETE FROM horarios where id_medicacion=:id")
    fun deleteByMedicationId(id: Long)
}