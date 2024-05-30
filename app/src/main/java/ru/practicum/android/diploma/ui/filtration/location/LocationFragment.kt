package ru.practicum.android.diploma.ui.filtration.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentLocationBinding
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
        val selectedCountry = arguments?.getString(SELECTED_COUNTRY_KEY)
        if (!selectedCountry.isNullOrEmpty()) {
            binding.etCountry.setText(selectedCountry)
            binding.etCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clean_icon, 0)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }

        // Получить выбранный регион из аргументов, если он есть
        val selectedRegion = arguments?.getString(SELECTED_REGION_KEY)
        if (!selectedRegion.isNullOrEmpty()) {
            binding.etRegion.setText(selectedRegion)
            binding.etRegion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clean_icon, 0)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }

        // Получить выбранные ID страны и региона
        val selectedCountryId = arguments?.getString(SELECTED_COUNTRY_ID_KEY)
        val selectedRegionId = arguments?.getString(SELECTED_REGION_ID_KEY)

//        Log.d(SELECTED_COUNTRY_ID_KEY, selectedCountryId.toString())
//        Log.d(SELECTED_COUNTRY_KEY, selectedCountry.toString())
//        Log.d(SELECTED_REGION_ID_KEY, selectedRegionId.toString())
//        Log.d(SELECTED_REGION_KEY, selectedRegion.toString())

        setupCountryField()
        setupRegionField(selectedCountry.toString(), selectedCountryId.toString())
        setupSelectButton()
        setupClearButton()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClearButton() { // Слушатель для кнопки очистки
        binding.etCountry.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP &&
                event.rawX >= binding.etCountry.right - binding.etCountry.compoundDrawables[2].bounds.width()
            ) {
                binding.etCountry.setText("")
                binding.etCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_forward, 0)
                binding.btnSelectionContainer.visibility =
                    if (binding.etRegion.text!!.isEmpty()) View.GONE else View.VISIBLE
                true
            } else {
                false
            }
        }

    }

    private fun setupSelectButton() {
        binding.btnSelectionContainer.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_filtrationFragment)
        }
    }

    private fun setupCountryField() {
        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_countryFragment)
        }
    }

    private fun setupRegionField(country: String, countryId: String) {
        binding.etRegion.setOnClickListener {
            val bundle = Bundle().apply {
                putString(SELECTED_COUNTRY_KEY, country)
                putString(SELECTED_COUNTRY_ID_KEY, countryId)
            }
            findNavController().navigate(R.id.action_locationFragment_to_regionFragment, bundle)
        }
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
        private const val SELECTED_COUNTRY_ID_KEY = "selectedCountryId"
        private const val SELECTED_COUNTRY_KEY = "selectedCountry"
        private const val SELECTED_REGION_ID_KEY = "selectedRegionId"
        private const val SELECTED_REGION_KEY = "selectedRegion"
    }
}
