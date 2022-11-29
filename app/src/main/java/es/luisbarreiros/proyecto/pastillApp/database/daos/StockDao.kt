package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Stock

@Dao
interface StockDao : BaseDao<Stock> {//heredamos de BaseDao
    @Query("DELETE FROM stocks where id_medicacion=:id") //elimino stocks de medicacion.
    fun deleteByMedicationId(id: Long)
}