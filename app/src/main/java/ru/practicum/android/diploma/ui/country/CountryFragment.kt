package ru.practicum.android.diploma.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var _adapter: VacancyAdapter? = null
    private val countries = listOf(
        "Россия", "США", "Китай", "Япония", "Германия",
        "Франция", "Индия", "Бразилия", "Канада"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    // !!!!!В ЭТОМ ФРАГМЕНТЕ ВРЕМЕННЫЙ КОД!!!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSearch.adapter =
            CountryAdapter(countries, { country -> onCountryClick(country) }, { onOtherRegionsClick() })

        // Пример: Слушатель для нажатия на страну
        binding.rvSearch.setOnClickListener {
            val selectedCountry = "Россия" // Здесь должен быть выбранный вариант страны
            val bundle = Bundle().apply {
                putString("selectedCountry", selectedCountry)
            }
            findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
        }
    }

    private fun onCountryClick(country: String) {
        // Обработка нажатия на страну
        val bundle = Bundle().apply {
            putString("selectedCountry", country)
        }
        findNavController().navigate(R.id.action_countryFragment_to_locationFragment, bundle)
    }

    private fun onOtherRegionsClick() {
        // Обработка нажатия на "Другие регионы"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
