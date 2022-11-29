package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Medicacion
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicacionDao : BaseDao<Medicacion> {//heredamos de BaseDao

    @Query( //query generica
        "SELECT m.*," + //recuperar entidad medicacion
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = m.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = m.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=3) as noche " +
                "FROM medicaciones m "

    )
    fun findAll(id: Long): Flow<List<MedicacionCompleta>> //creamos medicacion completa con todos los datos de la medicacion en relations.
    //y devuelvo una lista con medicacion completa.

    @Query(
        "SELECT m.*, " +
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = m.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = m.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=3) as noche " +
                "FROM medicaciones m " +
                "JOIN tomas f2 on f2.id_medicacion=m.id " + //recupera la medicacion que toma el usuario
                "WHERE f2.id_usuario = :id"
    )
    fun findAllToma(id: Long): Flow<List<MedicacionCompleta>>

    @Query(
        "SELECT m.*, " +
                "EXISTS (SELECT * from tomas where id_usuario=:id and id_medicacion = m.id) as toma, " +
                "EXISTS (SELECT * from stocks where id_usuario=:id and id_medicacion = m.id) as stock, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=1) as tarde, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=2) as manana, " +
                "EXISTS (SELECT * from horarios where id_medicacion=m.id and id_horarioS=3) as noche " +
                "FROM medicaciones m " +
                "JOIN stocks p on p.id_medicacion=m.id " + //recupera el stock de la medicacion
                "WHERE p.id_usuario = :id"
    )
    fun findAllStock(id: Long): Flow<List<MedicacionCompleta>>


}