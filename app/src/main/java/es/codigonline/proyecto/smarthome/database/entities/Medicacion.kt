package es.codigonline.proyecto.smarthome.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = Medicacion.TABLE_NAME,
    indices = [
        Index(value = ["nombre"], unique = true),

    ],

)

data class Medicacion(
    val nombre: String,
    val uso: String,
    val url: String,
    val imagen: String,
    val numero: Double,

) : BaseEntity(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),

    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(uso)
        parcel.writeString(url)
        parcel.writeString(imagen)
        parcel.writeDouble(numero)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicacion> {
        override fun createFromParcel(parcel: Parcel): Medicacion {
            return Medicacion(parcel)
        }

        override fun newArray(size: Int): Array<Medicacion?> {
            return arrayOfNulls(size)
        }

        const val TABLE_NAME = "medicaciones"
    }
}


