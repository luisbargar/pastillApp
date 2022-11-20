package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = HorarioS.TABLE_NAME,
    indices = [
        Index(value = ["nombre"], unique = true)
    ]
)


data class HorarioS(val nombre: String) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "horariosS"
    }
}