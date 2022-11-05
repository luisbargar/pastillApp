package es.codigonline.proyecto.smarthome.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.codigonline.proyecto.smarthome.database.entities.Horario

@Dao
interface HorarioDao : BaseDao<Horario> {

    @Query("DELETE FROM horarios where id_medicacion=:id")
    fun deleteByDeviceId(id: Long)
}