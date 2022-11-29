package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = HorarioS.TABLE_NAME, indices = [Index(value = ["nombre"], unique = true)])
//anotacion. El indice es un array de diferentes indices. El nombre sería unico en la base de datos.

data class HorarioS(val nombre: String) : BaseEntity() {companion object {const val TABLE_NAME = "horariosS"}}
//atributos. En baseEntity pongo el id común a todos.