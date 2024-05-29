package ru.practicum.android.diploma.ui.filtration.region

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.ui.root.RootActivity

class RegionFragment : Fragment() {
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegionViewModel>()
    private var _adapter: RegionAdapter? = null

    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()
        val selectedCountry = arguments?.getString("selectedCountry") ?: ""
        Log.d("RegionFragment", "Selected country: $selectedCountry")
        viewModel.fetchRegions(selectedCountry)

        _adapter = RegionAdapter(emptyList()) { region ->
            onRegionClick(region)
        }

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)

        viewModel.regions.observe(viewLifecycleOwner) { regions ->
            binding.rvSearch.adapter = RegionAdapter(regions) { region ->
                onRegionClick(region)
            }
        }

        binding.rvSearch.setOnClickListener {
            val selectedRegion = "Москва"
            val bundle = Bundle().apply {
                putString("selectedRegion", selectedRegion)
            }
            findNavController().navigate(R.id.action_regionFragment_to_locationFragment, bundle)
        }

    }

    private fun onRegionClick(region: String) {
        // Обработка нажатия на регион
        val bundle = Bundle().apply {
            putString("selectedRegion", region)
        }
        findNavController().navigate(R.id.action_regionFragment_to_locationFragment, bundle)
    }

    override fun onStop() {
        super.onStop()
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    private fun toolbarSetup() {
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        toolbar.title = getString(R.string.pick_regions)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
