package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = Stock.TABLE_NAME, primaryKeys = ["id_usuario", "id_medicacion"],
    indices = [Index(value = ["id_usuario"]), Index(value = ["id_medicacion"])],
    foreignKeys = [ForeignKey(
        entity = Medicacion::class,
        parentColumns = ["id"],
        childColumns = ["id_medicacion"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Usuario::class,
        parentColumns = ["id"],
        childColumns = ["id_usuario"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
    )]
)
data class Stock(
    @ColumnInfo(name = "id_medicacion") val medicacionId: Long,
    @ColumnInfo(name = "id_usuario") val usuarioId: Long
) {
    companion object {
        const val TABLE_NAME = "stocks"
    }

}
