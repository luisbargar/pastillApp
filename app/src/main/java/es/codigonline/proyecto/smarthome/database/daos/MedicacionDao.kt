package es.codigonline.proyecto.smarthome.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.codigonline.proyecto.smarthome.database.entities.Medicacion
import es.codigonline.proyecto.smarthome.database.relations.MedicacionCompleta
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicacionDao : BaseDao<Medicacion> {

    @Query(
        "SELECT d.*," +
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = d.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = d.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=3) as noche " +
                "FROM medicaciones d "

    )
    fun findAll(id: Long): Flow<List<MedicacionCompleta>>

    @Query(
        "SELECT d.*, " +
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = d.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = d.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=3) as noche " +
                "FROM medicaciones d " +
                "JOIN tomas f2 on f2.id_medicacion=d.id " +
                "WHERE f2.id_usuario = :id"
    )
    fun findAllToma(id: Long): Flow<List<MedicacionCompleta>>

    @Query(
        "SELECT d.*, " +
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = d.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = d.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=d.id and id_horarioS=3) as noche " +
                "FROM medicaciones d " +
                "JOIN stocks p on p.id_medicacion=d.id " +
                "WHERE p.id_usuario = :id"
    )
    fun findAllStock(id: Long): Flow<List<MedicacionCompleta>>


}