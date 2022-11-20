package es.codigonline.proyecto.smarthome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.codigonline.proyecto.smarthome.databinding.FragmentMedicationsBinding

class TomaFragment : Fragment() {
    private var _binding: FragmentMedicationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMedicationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.medicaciones(TipoMedicacion.TOMAS).observe(viewLifecycleOwner) {
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