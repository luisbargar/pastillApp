package es.codigonline.proyecto.smarthome.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentManager
import es.codigonline.proyecto.smarthome.app.Constantes
import es.codigonline.proyecto.smarthome.database.relations.MedicacionCompleta
import es.codigonline.proyecto.smarthome.ui.adapters.MedicationListener
import es.codigonline.proyecto.smarthome.ui.dialogs.DeviceDialog
import es.codigonline.proyecto.smarthome.ui.newMedication.NewMedicationActivity

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

    override fun open(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    override fun addToma(id: Long) {
        viewModel.addToma(id)
    }

    override fun delToma(id: Long) {
        viewModel.delToma(id)
    }

    override fun addStock(id: Long) {
        viewModel.addStock(id)
    }

    override fun delStock(id: Long) {
        viewModel.delStock(id)
    }

    override fun details(medicacionCompleta: MedicacionCompleta) {
        val deviceFragment = DeviceDialog(medicacionCompleta)
        deviceFragment.show(fragmentManager, Constantes.MEDICACION)
    }

}