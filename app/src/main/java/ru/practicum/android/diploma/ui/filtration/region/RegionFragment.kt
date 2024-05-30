package ru.practicum.android.diploma.ui.filtration.region

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.models.Region
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
        val selectedCountryId = arguments?.getString("selectedCountryId") ?: ""

        Log.d("selectedCountry-REGION", selectedCountry)
        Log.d("selectedCountryId-REGION", selectedCountryId)

        viewModel.fetchRegions(selectedCountryId)

        _adapter = RegionAdapter(emptyList()) { region, regionId ->
            onRegionClick(selectedCountryId, selectedCountry, regionId, region)
        }

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)

        viewModel.regionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Loading -> showLoading()
                is RegionState.Content -> showContent(state.regions)
                is RegionState.NotFound -> showNotFound()
                is RegionState.ServerError -> showError(state.message)
                is RegionState.NoConnection -> showNoConnection(state.message)
            }
        }

        binding.etRegionSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty block
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                _adapter?.filterRegions(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                // empty block
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.rvSearch.isVisible = false
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
    }

    private fun showContent(regions: List<Region>) {
        if (regions.isEmpty()) {
            showNotFound()
        } else {
            binding.progressBar.isVisible = false
            binding.rvSearch.isVisible = true
            binding.ivPlaceholder.isVisible = false
            binding.tvPlaceholder.isVisible = false
            _adapter?.updateRegions(regions)
        }
    }

    private fun showNotFound() {
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.ivPlaceholder.setImageResource(R.drawable.cant_find_list)
        binding.tvPlaceholder.setText(R.string.cant_find_list)
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
    }

    private fun showError(message: Int) {
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.ivPlaceholder.setImageResource(R.drawable.server_error_cat)
        binding.tvPlaceholder.setText(R.string.search_server_error)
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
    }

    private fun showNoConnection(message: Int) {
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.ivPlaceholder.setImageResource(R.drawable.cant_find_list)
        binding.tvPlaceholder.setText(R.string.cant_find_list)
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
    }

    private fun onRegionClick(countryId: String, country: String, regionId: String, region: String) {
        val bundle = Bundle().apply {
            putString("selectedCountryId", countryId)
            putString("selectedCountry", country)
            putString("selectedRegionId", regionId)
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
