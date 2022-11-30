package es.luisbarreiros.proyecto.pastillApp.ui.home

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.databinding.FragmentMedicationsBinding
import es.luisbarreiros.proyecto.pastillApp.ui.adapters.MedicationListener
import es.luisbarreiros.proyecto.pastillApp.ui.adapters.MedicacionesRecyclerViewAdapter


enum class TipoMedicacion(val tipo: Int) { //enumerador
    GENERALES(1), TOMAS(2), STOCKS(3)
}
class CommonFragmentImpl( //clase creada para crear el Recycler View común a todos los fragment.
    val medicationListener: MedicationListener,
    val context: Context, //para poder crear el adaptador y poder acceder a algunas funciones
    val binding: FragmentMedicationsBinding //donde se mostrará el recyclerview
) {
    private lateinit var mAdapterMedicaciones: MedicacionesRecyclerViewAdapter
    fun createRecyclerView(medicaciones: List<MedicacionCompleta>) { //funcion común a todos los fragment
        mAdapterMedicaciones =
            MedicacionesRecyclerViewAdapter(
                medicaciones as MutableList<MedicacionCompleta>,
                medicationListener,
                context
            )
        val recyclerView = binding.rvHome //creamos el adaptador del recyclerView
        recyclerView.apply { //el recyclerView será una lista vertical
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapterMedicaciones
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL)) //divisor horizontal entre cada tarjeta
        }
    }
}