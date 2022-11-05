package es.codigonline.proyecto.smarthome.database.daos

import androidx.room.Dao
import androidx.room.Query
import es.codigonline.proyecto.smarthome.database.entities.Usuario

@Dao
interface UsuarioDao: BaseDao<Usuario> {

    @Query("SELECT * from usuarios where nombre = :nombre")
    fun findOneByName(nombre: String): Usuario?
}