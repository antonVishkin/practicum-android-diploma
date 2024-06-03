package ru.practicum.android.diploma.ui.filtration.location

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentLocationBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.root.RootActivity

class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()

        // Получить выбранную страну из аргументов, если она есть
        val selectedCountry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(SELECTED_COUNTRY_KEY, Country::class.java)
        } else {
            arguments?.getParcelable(SELECTED_COUNTRY_KEY)
        }
        if (selectedCountry != null) {
            binding.etCountry.setText(selectedCountry.name)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }
        // Получить выбранный регион из аргументов, если он есть
        val selectedRegion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(SELECTED_REGION_KEY, Region::class.java)
        } else {
            arguments?.getParcelable(SELECTED_REGION_KEY)
        }
        if (selectedRegion != null) {
            binding.etRegion.setText(selectedRegion.name)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }

        // Обработка логики для setupRegionField
        setupCountryField()
        setupRegionField(selectedCountry, selectedRegion)
        setupSelectButton(selectedCountry, selectedRegion)
        setupClearButton()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClearButton() {
        // Слушатель для кнопки очистки в поле etCountry
        binding.countryIcon.setOnClickListener {
            binding.etCountry.setText("")
            updateClearButtonVisibility()
        }

        // Слушатель для кнопки очистки в поле etRegion
        binding.regionIcon.setOnClickListener {
            binding.etRegion.setText("")
            updateClearButtonVisibility()
        }

        // Обновление видимости кнопки очистки
        updateClearButtonVisibility()
    }

    private fun updateClearButtonVisibility() {
        val isCountryEmpty = binding.etCountry.text.isNullOrEmpty()
        val isRegionEmpty = binding.etRegion.text.isNullOrEmpty()

        binding.countryIcon.setImageResource(if (isCountryEmpty) R.drawable.arrow_forward else R.drawable.clean_icon)
        binding.regionIcon.setImageResource(if (isRegionEmpty) R.drawable.arrow_forward else R.drawable.clean_icon)
        binding.btnSelectionContainer.visibility = if (isCountryEmpty) View.GONE else View.VISIBLE

    }

    private fun setupSelectButton(country: Country?, region: Region?) {
        binding.btnSelectionContainer.setOnClickListener {
            val bundle = Bundle().apply {
                if (country != null) {
                    putParcelable(SELECTED_COUNTRY_KEY, country)
                }
                if (region != null) {
                    putParcelable(SELECTED_REGION_KEY, region)
                }
            }
            findNavController().navigate(R.id.action_locationFragment_to_filtrationFragment, bundle)
        }
    }

    private fun setupCountryField() {
        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_countryFragment)
        }
    }

    private fun setupRegionField(country: Country?, region: Region?) {
        binding.etRegion.setOnClickListener {
            val bundle = Bundle().apply {
                if (country != null) {
                    putParcelable(SELECTED_COUNTRY_KEY, country)
                }
                if (region != null) {
                    putParcelable(SELECTED_REGION_KEY, region)
                }
            }
            findNavController().navigate(R.id.action_locationFragment_to_regionFragment, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        updateClearButtonVisibility()
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

        toolbar.title = getString(R.string.pick_job_location)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SELECTED_COUNTRY_KEY = "selectedCountry"
        private const val SELECTED_REGION_KEY = "selectedRegion"
    }
}
