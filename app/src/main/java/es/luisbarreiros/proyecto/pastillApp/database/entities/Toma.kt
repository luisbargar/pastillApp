package es.luisbarreiros.proyecto.pastillApp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    tableName = Toma.TABLE_NAME, primaryKeys = ["id_usuario", "id_medicacion"], //no extiende de BaseEntity, clave compuesta por dos columnas.
    indices = [Index(value = ["id_usuario"]), Index(value = ["id_medicacion"])],
    foreignKeys = [ForeignKey(
        entity = Medicacion::class, parentColumns = ["id"],  childColumns = ["id_medicacion"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE//cambios en cascada.
    ), ForeignKey(
        entity = Usuario::class, parentColumns = ["id"], childColumns = ["id_usuario"],
        onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE//cambios en cascada.
    )]
)
data class Toma(
    @ColumnInfo(name = "id_medicacion") val medicacionId: Long,
    @ColumnInfo(name = "id_usuario") val usuarioId: Long
) {
    companion object {
        const val TABLE_NAME = "tomas"
    }
}
