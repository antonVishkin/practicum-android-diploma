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
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var _adapter: VacancyAdapter? = null
    private val viewModel by viewModel<CountryViewModel>()

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


        viewModel.fetchCountries()

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)

        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            binding.rvSearch.adapter = CountryAdapter(countries) { country, countryId ->
                onCountryClick(country, countryId)
            }
        }

        // Пример: Слушатель для нажатия на страну
        binding.rvSearch.setOnClickListener {
            val selectedCountry = "Россия"
            val selectedCountryId = "113"
            val bundle = Bundle().apply {
                putString("selectedCountry", selectedCountry)
                putString("selectedCountryId", selectedCountryId)
            }
            findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
        }
    }

    private fun onCountryClick(country: String, countryId: String) {
        // Обработка нажатия на страну
        val bundle = Bundle().apply {
            putString("selectedCountry", country)
            putString("selectedCountryId", countryId) // Передача идентификатора страны
        }
        findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
    }

    /* private fun onOtherRegionsClick() {
         // Обработка нажатия на "Другие регионы"
     }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
