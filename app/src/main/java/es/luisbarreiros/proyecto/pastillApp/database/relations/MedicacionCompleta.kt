package es.luisbarreiros.proyecto.pastillApp.database.relations

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import es.luisbarreiros.proyecto.pastillApp.database.entities.Medicacion

data class MedicacionCompleta( //como ninguna tiene esos campos creamos medicacionCompleta
    @Embedded val medicacion: Medicacion,
    val toma: Boolean,
    val stock: Boolean,
    val tarde: Boolean,
    val manana: Boolean,
    val noche: Boolean
    ):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Medicacion::class.java.classLoader)!!,

        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun toString(): String { //sobrescribimos el toString
        return super.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(medicacion, flags)
        parcel.writeByte(if (toma) 1 else 0)
        parcel.writeByte(if (stock) 1 else 0)
        parcel.writeByte(if (tarde) 1 else 0)
        parcel.writeByte(if (manana) 1 else 0)
        parcel.writeByte(if (noche) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicacionCompleta> {
        override fun createFromParcel(parcel: Parcel): MedicacionCompleta {
            return MedicacionCompleta(parcel)
        }

        override fun newArray(size: Int): Array<MedicacionCompleta?> {
            return arrayOfNulls(size)
        }
    }

}