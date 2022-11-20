package es.luisbarreiros.proyecto.pastillApp.ui.home

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.databinding.FragmentMedicationsBinding
import es.luisbarreiros.proyecto.pastillApp.ui.adapters.MedicationListener
import es.luisbarreiros.proyecto.pastillApp.ui.adapters.MedicacionesRecyclerViewAdapter

enum class TipoMedicacion(val tipo: Int) {
    GENERALES(1), TOMAS(2), STOCKS(3)
}

class CommonFragmentImpl(
    val medicationListener: MedicationListener,
    val context: Context,
    val binding: FragmentMedicationsBinding
) {
    private lateinit var mAdapterMedicaciones: MedicacionesRecyclerViewAdapter
    fun createRecyclerView(medicaciones: List<MedicacionCompleta>) {
        mAdapterMedicaciones =
            MedicacionesRecyclerViewAdapter(
                medicaciones as MutableList<MedicacionCompleta>,
                medicationListener,
                context
            )
        val recyclerView = binding.rvHome
        recyclerView.apply {
            //EL RECYCLER VIEW VA A SER UNA LISTA VERTICAL
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapterMedicaciones
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }
}