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
    val list: List<MedicacionCompleta>, //recibimos lista dispositivos
    private val listener: MedicationListener,// recibo listener, conunto de funciones de la interface MedicationListener
    val context: Context //recibo el contexto
) :
    RecyclerView.Adapter<MedicacionesRecyclerViewAdapter.ViewHolder>() { //Le pasamos el VIewHolder, clase que crea las tarjetas de las medicaciones

    class ViewHolder private constructor( //creamos una clase con un binding,  recibiendo un listener y un contexto
        private val binding: MedicationBinding,
        private val listener: MedicationListener,
        private val context: Context //el contexto lo ponemos aqui por el GLide, para mejorar el funcionamiento
    ) : RecyclerView.ViewHolder(binding.root) {
        fun rellenarDatos(data: MedicacionCompleta) { //recibimos una MedicacionCOmpleta
            binding.root.setOnClickListener {//Cuando hacemos click en la tarjeta de la medicación vemos los detalles.
                listener.details(data)
            } //después ponemos toda la información que queremos que aparezca en los detalles
            binding.nombre.text = data.medicacion.nombre
            binding.numero.text = context.getString(R.string.show_numero, data.medicacion.numero)
            binding.URL.text = context.getString(R.string.externo)
            binding.URL.setTextColor(Color.GREEN)
            binding.URL.setOnClickListener { //abrirá el enlace del prospecto
                listener.open(data.medicacion.url)
            }
            binding.edit.setOnClickListener { //botón de editar información
                listener.edit(data)
            }
            //definimos el color de los iconos cuando están marcados/desmarcados
            if (data.toma) { binding.toma.setIconTintResource(R.color.green)
            } else { binding.toma.setIconTintResource(R.color.md_theme_light_surface)
            }
            if (data.stock) { binding.stock.setIconTintResource(R.color.red)
            } else { binding.stock.setIconTintResource(R.color.md_theme_light_surface)
            }
            val circularProgressDrawable = CircularProgressDrawable(context) //animación circular cuando carga la imagen.
            circularProgressDrawable.strokeWidth = 6f
            circularProgressDrawable.centerRadius = 32f
            circularProgressDrawable.start()
            Glide //creamos el Glide
                .with(context)
                .load(data.medicacion.imagen)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(binding.imagen)

            binding.toma.setOnClickListener { //para las tomas. Si está activada o no cambian los colores.
                if (data.toma) {
                    listener.delToma(data.medicacion.id)
                    binding.toma.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    binding.toma.setIconTintResource(R.color.green)
                    listener.addToma(data.medicacion.id)
                }
            }
            binding.stock.setOnClickListener { //para el stock. Si está activado o no cambian los colores.
                if (data.stock) {
                    listener.delStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.md_theme_light_surface)
                } else {
                    listener.addStock(data.medicacion.id)
                    binding.stock.setIconTintResource(R.color.red)
                }
            }
        }
        companion object { //dentro del viewHolder.
            fun newInstance( //creamos binding, le pasamos el listener y el contexto.
                parent: ViewGroup, listener: MedicationListener, context: Context
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MedicationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener, context)
            }
        }
    }
    override fun getItemCount() = list.size
    //devuelve el tamaño total de la lista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.newInstance(parent, listener, context)

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =  viewHolder.rellenarDatos(list[position])
    //Lo que se pasa cuando queremos vincular datos
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