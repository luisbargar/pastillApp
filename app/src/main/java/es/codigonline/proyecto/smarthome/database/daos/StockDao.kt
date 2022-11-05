package es.codigonline.proyecto.smarthome.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.codigonline.proyecto.smarthome.database.entities.Stock

@Dao
interface StockDao : BaseDao<Stock> {
    @Query("DELETE FROM stocks where id_medicacion=:id")
    fun deleteByDeviceId(id: Long)
}