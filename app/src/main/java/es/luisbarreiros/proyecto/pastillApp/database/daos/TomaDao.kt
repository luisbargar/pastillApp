package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Toma

@Dao
interface TomaDao : BaseDao<Toma> {//heredamos de BaseDao

    @Query("DELETE FROM tomas where id_medicacion=:id") //elimino las tomas de la medicacion.
    fun deleteByMedicationId(id: Long)
}