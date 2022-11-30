package es.luisbarreiros.proyecto.pastillApp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.databinding.FullMedicationBinding
import es.luisbarreiros.proyecto.pastillApp.ui.home.HomeViewModel

class MedicationDialog(val data: MedicacionCompleta) : DialogFragment() { //creamos clase que hereda de DialogFragment

    lateinit var binding: FullMedicationBinding //recibimos una medicación completa que es la que se  mostrará en pantalla
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog { //sobreescribimos método On CreateDialog, encargado d mostrarlo por pantalla
        return activity?.let { //es obligatorio que tenga una actividad vinculada, ya que se abre encima de una actividad. Lo comprobamos.
            //construimos el diálogo
            val builder = AlertDialog.Builder(it) //vinculado al fragment activity
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.full_medication, null)
            binding = FullMedicationBinding.bind(view)
            binding.nombre.text = data.medicacion.nombre
            binding.uso.text = data.medicacion.uso
            binding.numero.text = getString(R.string.show_numero, data.medicacion.numero)
            binding.url.text = getString(R.string.externo)
            binding.url.setTextColor(Color.BLUE)
            binding.url.setOnClickListener {
            }

            val circularProgressDrawable = CircularProgressDrawable(requireContext())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide
                .with(requireContext())
                .load(data.medicacion.imagen)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(binding.imagen)
            //comprobamos si tenemos horarios
            if (!data.tarde) {binding.tarde.visibility = View.GONE
            }
            if (!data.manana) {binding.manana.visibility = View.GONE
            }
            if (!data.noche) {binding.noche.visibility = View.GONE
            }
            //comprobamos si tenemos medicación en la medicación que toma elusuario
            if (data.toma) {binding.toma.setIconTintResource(R.color.green)
            } else {binding.toma.setIconTintResource(R.color.md_theme_light_surface)
            }
            //comprobamos si la medicación esta en la lista de sin stock
            if (data.stock) {binding.stock.setIconTintResource(R.color.red)
            } else { binding.stock.setIconTintResource(R.color.md_theme_light_surface)
            }
            //creamos los clicks correspondientes para toma
            binding.toma.setOnClickListener {
                if (data.toma) {
                    homeViewModel.delToma(data.medicacion.id)
                    binding.toma.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    binding.toma.setIconTintResource(R.color.green)
                    homeViewModel.addToma(data.medicacion.id)
                }
            }
            //creamos los clicks correspondientes para stock
            binding.stock.setOnClickListener {
                if (data.stock) {
                    homeViewModel.delStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    homeViewModel.addStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.red)
                }
            }

            builder.setView(view) //ponemos la vista correspondiente al builder
            builder.create() //aqui se mostrará el dialogo
        } ?: throw IllegalStateException("Activity cannot be null") //si no tenemos actividad saldría esto.
    }
}