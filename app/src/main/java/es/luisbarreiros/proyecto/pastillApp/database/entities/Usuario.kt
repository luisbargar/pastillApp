package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["nombre"], unique = true)])//el nombre del usuario tiene que ser único.

data class Usuario(val nombre: String, val password: String? = "") : BaseEntity()//atributos. En baseEntity pongo el id común a todo