package ru.practicum.android.diploma.ui.filtration.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var _adapter: VacancyAdapter? = null
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

        // Пример: Слушатель для нажатия на страну
        binding.rvSearch.setOnClickListener {
            val selectedCountry = "Россия" // Здесь должен быть выбранный вариант страны
            val bundle = Bundle().apply {
                putString("selectedCountry", selectedCountry)
            }
            findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showPlaceholder()
            } else {
                hidePlaceholder()
            }
        }
    }

    private fun showPlaceholder() {
        binding.placeholderContainer.visibility = View.VISIBLE
        binding.rvSearch.visibility = View.GONE
    }

    private fun hidePlaceholder() {
        binding.placeholderContainer.visibility = View.GONE
        binding.rvSearch.visibility = View.VISIBLE
    }

    private fun onCountryClick(country: String) {
        // Обработка нажатия на страну
        val bundle = Bundle().apply {
            putString("selectedCountry", country)
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
}
