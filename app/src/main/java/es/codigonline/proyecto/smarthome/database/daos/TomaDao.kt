package es.codigonline.proyecto.smarthome.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.codigonline.proyecto.smarthome.database.entities.Toma

@Dao
interface TomaDao : BaseDao<Toma> {

    @Query("DELETE FROM tomas where id_medicacion=:id")
    fun deleteByDeviceId(id: Long)
}