package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.luisbarreiros.proyecto.pastillApp.database.entities.Usuario

@Dao
interface UsuarioDao: BaseDao<Usuario> {//heredamos de BaseDao

    @Query("SELECT * from usuarios where nombre = :nombre") //recupero un usuario donde el nombre sea igual al nombre. Para el login.
    fun findOneByName(nombre: String): Usuario? //como es posible que no encontremos nada damos la posibilidad de que esto sea nulo.
}