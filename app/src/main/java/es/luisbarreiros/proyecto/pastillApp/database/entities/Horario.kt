package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = Horario.TABLE_NAME,
    primaryKeys = ["id_horarioS", "id_medicacion"],
    indices = [Index(value = ["id_horarioS"]), Index(value = ["id_medicacion"])],
    foreignKeys = [ForeignKey(
        entity = Medicacion::class,
        parentColumns = ["id"],
        childColumns = ["id_medicacion"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = HorarioS::class,
        parentColumns = ["id"],
        childColumns = ["id_horarioS"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
    )]
)
data class Horario(
    @ColumnInfo(name = "id_horarioS") val sistemaId: Long,
    @ColumnInfo(name = "id_medicacion") val medicacionId: Long
) {

    companion object{

        const val TABLE_NAME = "horarios"
    }

}
