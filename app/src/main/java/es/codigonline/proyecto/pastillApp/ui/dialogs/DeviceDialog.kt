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

class DeviceDialog(val data: MedicacionCompleta) : DialogFragment() {

    lateinit var binding: FullMedicationBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
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

            if (!data.tarde) {
                binding.tarde.visibility = View.GONE
            }
            if (!data.manana) {
                binding.manana.visibility = View.GONE
            }
            if (!data.noche) {
                binding.noche.visibility = View.GONE
            }

            if (data.toma) {
                binding.like.setIconTintResource(R.color.green)

            } else {
                binding.like.setIconTintResource(R.color.md_theme_light_surface)
            }
            if (data.stock) {
                binding.stock.setIconTintResource(R.color.red)
            } else {
                binding.stock.setIconTintResource(R.color.md_theme_light_surface)
            }

            binding.like.setOnClickListener {
                if (data.toma) {
                    homeViewModel.delToma(data.medicacion.id)
                    binding.like.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    binding.like.setIconTintResource(R.color.green)
                    homeViewModel.addToma(data.medicacion.id)
                }
            }
            binding.stock.setOnClickListener {
                if (data.stock) {
                    homeViewModel.delStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    homeViewModel.addStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.red)
                }
            }





            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}