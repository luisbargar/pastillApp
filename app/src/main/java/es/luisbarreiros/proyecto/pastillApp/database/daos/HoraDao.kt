package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import es.luisbarreiros.proyecto.pastillApp.database.entities.HorarioS

@Dao
interface HoraDao : BaseDao<HorarioS> //heredamos de BaseDao