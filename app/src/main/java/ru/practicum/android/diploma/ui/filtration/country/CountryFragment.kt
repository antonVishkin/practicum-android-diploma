package ru.practicum.android.diploma.ui.filtration.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.root.RootActivity

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var _adapter: CountryAdapter? = null
    private val viewModel by viewModel<CountryViewModel>()
    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()
        viewModel.fetchCountries()

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)

        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            binding.rvSearch.adapter = CountryAdapter(countries) { country ->
                onCountryClick(country)
            }
        }
        viewModel.countryState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Loading -> showLoading()
                is CountryState.Content -> showContent(state.countries)
                is CountryState.NotFound -> showNotFound()
                is CountryState.ServerError -> showError(state.message)
                is CountryState.NoConnection -> showNoConnection(state.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.rvSearch.isVisible = false
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
    }

    private fun showContent(countries: List<Country>) {
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = true
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
        _adapter?.updateCountries(countries)
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

    private fun onCountryClick(country: Country) {
        val bundle = Bundle().apply {
            putParcelable(SELECTED_COUNTRY_LABEL, Country(country.id, country.name, listOf()))
        }
        findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
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

        toolbar.title = getString(R.string.pick_country)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SELECTED_COUNTRY_LABEL = "selectedCountry"
    }
}
