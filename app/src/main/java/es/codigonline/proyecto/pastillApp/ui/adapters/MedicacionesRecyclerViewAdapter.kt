package es.luisbarreiros.proyecto.pastillApp.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.databinding.MedicationBinding

class MedicacionesRecyclerViewAdapter(
    val list: List<MedicacionCompleta>,
    private val listener: MedicationListener,
    val context: Context
) :
    RecyclerView.Adapter<MedicacionesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder private constructor(
        private val binding: MedicationBinding,
        private val listener: MedicationListener,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun rellenarDatos(data: MedicacionCompleta) {
            binding.root.setOnClickListener {
                listener.details(data)
            }
            binding.nombre.text = data.medicacion.nombre
            binding.numero.text = context.getString(R.string.show_numero, data.medicacion.numero)
            binding.URL.text = context.getString(R.string.externo)
            binding.URL.setTextColor(Color.BLUE)
            binding.URL.setOnClickListener {
                listener.open(data.medicacion.url)
            }
            binding.edit.setOnClickListener {
                listener.edit(data)
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



            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide
                .with(context)
                .load(data.medicacion.imagen)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(binding.imagen)

            binding.like.setOnClickListener {
                if (data.toma) {
                    listener.delToma(data.medicacion.id)
                    binding.like.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    binding.like.setIconTintResource(R.color.green)
                    listener.addToma(data.medicacion.id)
                }
            }
            binding.stock.setOnClickListener {
                if (data.stock) {
                    listener.delStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    listener.addStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.red)
                }
            }
        }

        companion object {
            fun newInstance(
                parent: ViewGroup, listener: MedicationListener, context: Context
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MedicationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener, context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.newInstance(parent, listener, context)

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.rellenarDatos(list[position])

    override fun getItemCount() = list.size



}

interface MedicationListener {
    fun open(url: String)
    fun addToma(id: Long)
    fun delToma(id: Long)
    fun addStock(id: Long)
    fun delStock(id: Long)
    fun details(medicacionCompleta: MedicacionCompleta)
    fun edit(medicacionCompleta: MedicacionCompleta)
}