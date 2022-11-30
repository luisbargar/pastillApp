package es.luisbarreiros.proyecto.pastillApp.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import es.luisbarreiros.proyecto.pastillApp.app.Constantes
import es.luisbarreiros.proyecto.pastillApp.database.relations.MedicacionCompleta
import es.luisbarreiros.proyecto.pastillApp.ui.adapters.MedicationListener
import es.luisbarreiros.proyecto.pastillApp.ui.dialogs.MedicationDialog
import es.luisbarreiros.proyecto.pastillApp.ui.newMedication.NewMedicationActivity


class MedicationListenerImpl(
    val context: Context,
    val viewModel: HomeViewModel,
    private val fragmentManager: FragmentManager
) : MedicationListener {
    override fun edit(medicacionCompleta: MedicacionCompleta) {
        val intent = Intent(context, NewMedicationActivity::class.java)
        intent.putExtra(Constantes.MEDICACION, medicacionCompleta)
        context.startActivity(intent)
    }
    override fun open(url: String) { //para abrir la url
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }
    override fun addToma(id: Long) { //para añadir a toma
        viewModel.addToma(id)
    }
    override fun delToma(id: Long) { //para quitar de toma
        viewModel.delToma(id)
    }
    override fun addStock(id: Long) { //para añadir a sin stock
        viewModel.addStock(id)
    }
    override fun delStock(id: Long) { //para eliminar de stock
        viewModel.delStock(id)
    }
    override fun details(medicacionCompleta: MedicacionCompleta) {
        val medicationFragment = MedicationDialog(medicacionCompleta) //creamos un objeto del tipo MedicationDialog
        medicationFragment.show(fragmentManager, Constantes.MEDICACION) //llamamos al metodo .show, llamando al fragmentManager y le pasamos la constante tipo medicación.
    }

}