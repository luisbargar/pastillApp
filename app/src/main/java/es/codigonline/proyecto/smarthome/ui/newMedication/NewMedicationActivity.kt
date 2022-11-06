package es.codigonline.proyecto.smarthome.ui.newMedication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import es.codigonline.proyecto.smarthome.R
import es.codigonline.proyecto.smarthome.app.Constantes
import es.codigonline.proyecto.smarthome.app.Constantes.Companion.TARDE
import es.codigonline.proyecto.smarthome.app.Constantes.Companion.NOCHE
import es.codigonline.proyecto.smarthome.app.Constantes.Companion.MANANA
import es.codigonline.proyecto.smarthome.database.entities.Medicacion
import es.codigonline.proyecto.smarthome.database.relations.MedicacionCompleta
import es.codigonline.proyecto.smarthome.databinding.ActivityNewDeviceBinding

class NewMedicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewDeviceBinding
    private val viewModel: NewMedicationViewModel by viewModels()



    private lateinit var medicacionCompleta: MedicacionCompleta
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceBinding.inflate(layoutInflater)

        setContentView(binding.root)

        intent.extras?.let {
            binding.eliminar.visibility = View.VISIBLE
            medicacionCompleta = it.getParcelable(Constantes.MEDICACION)!!
            binding.tieNombre.setText(medicacionCompleta.medicacion.nombre)
            binding.tieUso.setText(medicacionCompleta.medicacion.uso)
            binding.tieImagen.setText(medicacionCompleta.medicacion.imagen)
            binding.tieUrl.setText(medicacionCompleta.medicacion.url)
            binding.tieNumero.setText(medicacionCompleta.medicacion.numero.toString())

            if (medicacionCompleta.tarde) {
                binding.tarde.isChecked = true
            }
            if (medicacionCompleta.manana) {
                binding.manana.isChecked = true
            }
            if (medicacionCompleta.noche) {
                binding.noche.isChecked = true
            }
        }

    }



    override fun onStart() {
        super.onStart()


        binding.eliminar.setOnClickListener {
            viewModel.eliminar(medicacionCompleta.medicacion)
            finish()
        }

        binding.guardar.setOnClickListener {
            val nombre = binding.tieNombre.text.toString()
            val uso = binding.tieUso.text.toString()
            val url = binding.tieUrl.text.toString()
            var imagen = binding.tieImagen.text.toString()
            val numero = binding.tieNumero.text.toString()

            if (nombre.isBlank()||uso.isBlank()|| numero.isBlank()) {
                Toast.makeText(this, getString(R.string.rellenar_campos), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imagen.isBlank()){

                imagen = "https://st.depositphotos.com/1748085/4534/v/950/depositphotos_45345337-stock-illustration-random-capsules.jpg"

            }


            val medicacion = Medicacion(
                nombre,
                uso,
                url,
                imagen,
                numero.toDouble(),

            )

            var errors = false


            if (!this::medicacionCompleta.isInitialized)
                viewModel.save(medicacion).observe(this) {
                    if (it == -1L) {
                        Toast.makeText(
                            this,
                            getString(R.string.medicacion_existe),
                            Toast.LENGTH_SHORT
                        ).show()
                        errors = true
                    }
                    if (binding.tarde.isChecked) {
                        addHorario(TARDE, it)
                    }
                    if (binding.manana.isChecked) {
                        addHorario(MANANA, it)
                    }

                    if (binding.noche.isChecked) {
                        addHorario(NOCHE, it)
                    }
                }
            else {
                medicacion.id = this.medicacionCompleta.medicacion.id
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
                    if (medicacionCompleta.tarde && !binding.tarde.isChecked) {
                        delHorario(TARDE, medicacion.id)
                    }
                    if (!medicacionCompleta.tarde && binding.tarde.isChecked) {
                        addHorario(TARDE, medicacion.id)
                    }
                    if (medicacionCompleta.manana && !binding.manana.isChecked) {
                        delHorario(MANANA, medicacion.id)
                    }
                    if (!medicacionCompleta.manana && binding.manana.isChecked) {
                        addHorario(MANANA, medicacion.id)
                    }
                    if (medicacionCompleta.noche && !binding.noche.isChecked) {
                        delHorario(NOCHE, medicacion.id)
                    }
                    if (!medicacionCompleta.noche && binding.noche.isChecked) {
                        addHorario(NOCHE, medicacion.id)
                    }
                }
            }
            if (!errors)
                finish()
        }
    }

        private fun addHorario(horarioS: Long, id: Long) {
            viewModel.addHorario(horarioS, id)
        }

        private fun delHorario(horarioS: Long, id: Long) {
            viewModel.delHorario(horarioS, id)
        }
}