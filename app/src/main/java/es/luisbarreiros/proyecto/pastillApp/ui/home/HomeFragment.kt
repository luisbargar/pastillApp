package es.luisbarreiros.proyecto.pastillApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.luisbarreiros.proyecto.pastillApp.databinding.FragmentMedicationsBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentMedicationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.medicaciones(TipoMedicacion.GENERALES).observe(viewLifecycleOwner) {
            CommonFragmentImpl(
                MedicationListenerImpl(
                    requireContext(),
                    viewModel,
                    parentFragmentManager
                ), requireContext(), binding
            ).createRecyclerView(it)
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}