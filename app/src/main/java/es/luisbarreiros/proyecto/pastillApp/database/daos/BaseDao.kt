package es.luisbarreiros.proyecto.pastillApp.database.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao <T> {//este BaseDao nos vale para ahorrar código. Es de tipo genérico
    //y podremos insertarle el tipo que queramos, sea medicacion, stock, etc...
    @Insert(onConflict = OnConflictStrategy.ABORT) //manejamos excepción y si hay conflicto abortamos.
    fun save(t: T): Long //guardar

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg t: T) //insertar muchos objetos de golpe

    @Delete
    fun delete(t: T) //borra

    @Update
    fun update(t: T) //actualiza
}