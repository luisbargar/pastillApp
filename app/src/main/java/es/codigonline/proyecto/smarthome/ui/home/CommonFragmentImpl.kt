package es.codigonline.proyecto.smarthome.ui.home

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.codigonline.proyecto.smarthome.database.relations.MedicacionCompleta
import es.codigonline.proyecto.smarthome.databinding.FragmentDevicesBinding
import es.codigonline.proyecto.smarthome.ui.adapters.DeviceListener
import es.codigonline.proyecto.smarthome.ui.adapters.MedicacionesRecyclerViewAdapter

enum class TipoMedicacion(val tipo: Int) {
    GENERALES(1), TOMAS(2), STOCKS(3)
}

class CommonFragmentImpl(
    val deviceListener: DeviceListener,
    val context: Context,
    val binding: FragmentDevicesBinding
) {
    private lateinit var mAdapterMedicaciones: MedicacionesRecyclerViewAdapter
    fun createRecyclerView(medicaciones: List<MedicacionCompleta>) {
        mAdapterMedicaciones =
            MedicacionesRecyclerViewAdapter(
                medicaciones as MutableList<MedicacionCompleta>,
                deviceListener,
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