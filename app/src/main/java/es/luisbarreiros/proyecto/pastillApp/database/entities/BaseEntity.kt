package es.luisbarreiros.proyecto.pastillApp.database.entities


import androidx.room.PrimaryKey


abstract class BaseEntity {@PrimaryKey(autoGenerate = true) var id: Long = 0

}//clase abstracta que autogenera id, clave primaria. Así no duplicamos información en todos los entities.