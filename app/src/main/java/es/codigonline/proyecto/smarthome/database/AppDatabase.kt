package es.codigonline.proyecto.smarthome.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import es.codigonline.proyecto.smarthome.app.App
import es.codigonline.proyecto.smarthome.database.converters.DateConverter
import es.codigonline.proyecto.smarthome.database.daos.*
import es.codigonline.proyecto.smarthome.database.entities.*
import kotlinx.coroutines.*

@Database(
    entities = [Usuario::class, Medicacion::class,  Toma::class, Stock::class, HorarioS::class, Horario::class],
    version = 1
)

@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicacionDao(): MedicacionDao
    abstract fun stockDao(): StockDao
    abstract fun tomaDao(): TomaDao
    abstract fun horaDao(): HoraDao
    abstract fun horarioDao(): HorarioDao

    companion object { //static
        private lateinit var db: AppDatabase

        fun initDB(context: Context): AppDatabase {
            if (!this::db.isInitialized) { //Singleton
                db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
                    .addCallback(callback)
                    .build()
            }
            return db
        }

        private val callback: Callback = object : Callback() { //creamos un objeto de la clase
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    //INSERCION DE DATOS
                    withContext(Dispatchers.IO) {
                        App.getDatabase().usuarioDao().save(Usuario("luis", "123456"))
                        App.getDatabase().horaDao().insertAll(
                            HorarioS("Tarde"),
                            HorarioS("Manana"),
                            HorarioS("Noche"),
                        )


                        App.getDatabase().medicacionDao().save(
                            Medicacion(
                                "Ibuprofeno",
                                "para el dolor de cabeza",
                                "https://www.amazon.es/TP-Link-Tapo-P100-Inteligente-Inalámbrico/dp/B08GDD17BS/ref=sr_1_5?__mk_es_ES=ÅMÅŽÕÑ&crid=3MWQ9LMCVQ6SF&keywords=enchufe+wifi&qid=1661089108&sprefix=enchufe+wifi%2Caps%2C90&sr=8-5",
                                "https://m.media-amazon.com/images/I/61NfVfnla8L._AC_SL1000_.jpg",
                                1.5,


                            )
                        )

                    }
                }

            }
        }
    }


}