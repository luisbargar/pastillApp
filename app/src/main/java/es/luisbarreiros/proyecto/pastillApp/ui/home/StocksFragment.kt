package es.luisbarreiros.proyecto.pastillApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.luisbarreiros.proyecto.pastillApp.databinding.FragmentMedicationsBinding


class StocksFragment : Fragment() {
    private var _binding: FragmentMedicationsBinding? = null //creamos el binding
    private val binding get() = _binding!! //funcion getter para _binding
    private val viewModel: HomeViewModel by viewModels() //creamos el viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationsBinding.inflate(inflater, container, false) //definimos el binding
        val root: View = binding.root
        viewModel.medicaciones(TipoMedicacion.STOCKS).observe(viewLifecycleOwner) { //definimos consulta para recuperar la lista de medicaciones en stock
            CommonFragmentImpl( //creamos el CommonFragmentImpl para crear el recyclerVIew en común a todos los Fragment.
                MedicationListenerImpl(
                    requireContext(),
                    viewModel,
                    parentFragmentManager
                ), requireContext(), binding
            ).createRecyclerView(it) //creamosRecyclerView
        }
        return root
    }
    override fun onDestroyView() { //destruimos el binding para que no se quede en memoria
        super.onDestroyView()
        _binding = null
    }
}