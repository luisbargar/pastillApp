package es.luisbarreiros.proyecto.pastillApp.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.database.daos.*
import es.luisbarreiros.proyecto.pastillApp.database.entities.*
import kotlinx.coroutines.*

@Database( //anotación con entidades que forman la base de datos y su versión.
    entities = [Usuario::class, Medicacion::class,  Toma::class, Stock::class, HorarioS::class, Horario::class],
    version = 1
)


abstract class AppDatabase : RoomDatabase() { //clase que extiende de room database y es necesaria para el funcionamiento de la base de datos.
//definimos base de datos. creamos funciones abstarctas donde cada una correponde a la interfaz correspondiente.
    //declaramos todos los Dao como funcion abstracta.
    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicacionDao(): MedicacionDao
    abstract fun stockDao(): StockDao
    abstract fun tomaDao(): TomaDao
    abstract fun horaDao(): HoraDao
    abstract fun horarioDao(): HorarioDao

    companion object { //metodo estatico
        private lateinit var db: AppDatabase //creamos objeto de esta clase utilizando el patron builder.

        fun initDB(context: Context): AppDatabase { //recibe el contexto y devuelve instancia de appdatabase
            if (!this::db.isInitialized) { // aplicamos patrón Singleton. Si no esta inicializado consturimos objeto.

                db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
                    .addCallback(callback) //prerrellenamos la base de datos
                    .build()
            }
            return db //si ya esta inicializada devolvemos
        }

        private val callback: Callback = object : Callback() { //creamos implementacion de la clase Callback. Al ser abstracta
            //debemos de implementar los diferentes metodos que vayamos a utilizar.
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {//accedemos al dispatchers
                    //INSERCION DE DATOS
                    withContext(Dispatchers.IO) {
                        App.getDatabase().usuarioDao().save(Usuario("luis", "123456")) //insertamos datos con los Dao
                        App.getDatabase().horaDao().insertAll(
                            HorarioS("Tarde"),
                            HorarioS("Manana"),
                            HorarioS("Noche"),
                        )


                        App.getDatabase().medicacionDao().save(
                            Medicacion(
                                "Ibuprofeno",
                                "Para el dolor de cabeza",
                                "https://medlineplus.gov/spanish/druginfo/meds/a682159-es.html",
                                "https://static.eldiario.es/clip/6a876801-0e6b-467a-b293-e48217ab77d0_16-9-discover-aspect-ratio_default_0.jpg",
                                600.0,

                            )

                        )

                        App.getDatabase().medicacionDao().save(
                            Medicacion(
                                "Adiro",
                                "Para la circulación",
                                "https://cima.aemps.es/cima/dochtml/p/62825/P_62825.html",
                                "https://s1.eestatic.com/2018/06/19/ciencia/salud/medicamentos-farmacias-bayer_316231470_82707549_1706x960.jpg",
                                100.0,

                                )

                        )
                        App.getDatabase().medicacionDao().save(
                            Medicacion(
                                "Omeprazol",
                                "Para la úlcera",
                                "https://cima.aemps.es/cima/dochtml/p/78891/Prospecto_78891.html",
                                "https://www.65ymas.com/uploads/s1/21/07/43/omeprazol.jpeg",
                                40.0,

                                )

                        )
                        App.getDatabase().medicacionDao().save(
                            Medicacion(
                                "Alprazolam",
                                "Para los nervios",
                                "https://medlineplus.gov/spanish/druginfo/meds/a684001-es.html",
                                "https://nomenclator.org/img/envase.900/alprazolam-cinfa-1-mg-comprimidos.jpg",
                                1.0,

                                )

                        )


                    }
                }

            }
        }
    }


}