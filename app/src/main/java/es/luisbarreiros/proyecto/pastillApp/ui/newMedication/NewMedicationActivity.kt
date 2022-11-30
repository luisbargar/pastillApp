package es.luisbarreiros.proyecto.pastillApp.ui.newMedication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.app.Constantes
import es.luisbarreiros.proyecto.pastillApp.app.Constantes.Companion.TARDE
import es.luisbarreiros.proyecto.pastillApp.app.Constantes.Companion.NOCHE
import es.luisbarreiros.proyecto.pastillApp.app.Constantes.Companion.MANANA
import es.luisbarreiros.proyecto.pastillApp.database.entities.Medicacion
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.databinding.ActivityNewMedicationBinding


class NewMedicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMedicationBinding //binding
    private val viewModel: NewMedicationViewModel by viewModels() //viewModel correspondiente
    private lateinit var medicacionCompleta: MedicacionCompleta
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMedicationBinding.inflate(layoutInflater) //inicializamos binding
        setContentView(binding.root)

        intent.extras?.let { //comprobamos si nos viene información a traves del Bundle (si estamos editando el dispositivo viene dado)
            //Si hay datos relleno toda la vista con la información correspondiente
            binding.eliminar.visibility = View.VISIBLE //habilito el botón de eliminar
            medicacionCompleta = it.getParcelable(Constantes.MEDICACION)!!
            binding.tieNombre.setText(medicacionCompleta.medicacion.nombre)
            binding.tieUso.setText(medicacionCompleta.medicacion.uso)
            binding.tieImagen.setText(medicacionCompleta.medicacion.imagen)
            binding.tieUrl.setText(medicacionCompleta.medicacion.url)
            binding.tieNumero.setText(medicacionCompleta.medicacion.numero.toString())

            if (medicacionCompleta.tarde) {binding.tarde.isChecked = true
            }
            if (medicacionCompleta.manana) {binding.manana.isChecked = true
            }
            if (medicacionCompleta.noche) {binding.noche.isChecked = true
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.eliminar.setOnClickListener { //Vinculo botón de eliminar
            viewModel.eliminar(medicacionCompleta.medicacion)
            finish()
        }

        binding.guardar.setOnClickListener { //vinculo botón de guardar, que guarda los diferentes valores introducidos.
            val nombre = binding.tieNombre.text.toString()
            val uso = binding.tieUso.text.toString()
            val url = binding.tieUrl.text.toString()
            var imagen = binding.tieImagen.text.toString()
            val numero = binding.tieNumero.text.toString()

            if (nombre.isBlank()||uso.isBlank()|| numero.isBlank()) { //Comprobamos que los campos obligatorios tengan datos
                Toast.makeText(this, getString(R.string.rellenar_campos), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imagen.isBlank()){ //En caso de que no haya url de la imagen se le asigna una random
                imagen = "https://st.depositphotos.com/1748085/4534/v/950/depositphotos_45345337-stock-illustration-random-capsules.jpg"
            }
            val medicacion = Medicacion(nombre, uso, url, imagen, numero.toDouble(),
            )
           var errors = false

            if (!this::medicacionCompleta.isInitialized) //si no recibimos un dispositivo llamamos a la funcion save.
                viewModel.save(medicacion).observe(this) {
                    if (it == -1L) {
                        Toast.makeText(
                            this,
                            getString(R.string.medicacion_existe),
                            Toast.LENGTH_SHORT
                        ).show()
                        errors = true
                    }
                    if (binding.tarde.isChecked) {addHorario(TARDE, it)
                    }
                    if (binding.manana.isChecked) {addHorario(MANANA, it)
                    }
                    if (binding.noche.isChecked) {addHorario(NOCHE, it)
                    }
                }
            else {
                medicacion.id = this.medicacionCompleta.medicacion.id //si lo hemos recibido llamamos a la función update.
                viewModel.update(medicacion).observe(this) {
                    if (!it) {
                        Toast.makeText(
                            this,
                            getString(R.string.medicacion_existe),
                            Toast.LENGTH_SHORT
                        ).show()
                        errors = true
                        return@observe
                    }
                    //comprobamos si se han añadido nuevas o quitado antiguas.
                    if (medicacionCompleta.tarde && !binding.tarde.isChecked) { delHorario(TARDE, medicacion.id)
                    }
                    if (!medicacionCompleta.tarde && binding.tarde.isChecked) { addHorario(TARDE, medicacion.id)
                    }
                    if (medicacionCompleta.manana && !binding.manana.isChecked) { delHorario(MANANA, medicacion.id)
                    }
                    if (!medicacionCompleta.manana && binding.manana.isChecked) { addHorario(MANANA, medicacion.id)
                    }
                    if (medicacionCompleta.noche && !binding.noche.isChecked) { delHorario(NOCHE, medicacion.id)
                    }
                    if (!medicacionCompleta.noche && binding.noche.isChecked) { addHorario(NOCHE, medicacion.id)
                    }
                }
            }
            if (!errors)
                finish()
        }
    }
        private fun addHorario(horarioS: Long, id: Long) { viewModel.addHorario(horarioS, id)
        }
        private fun delHorario(horarioS: Long, id: Long) { viewModel.delHorario(horarioS, id)
        }
}