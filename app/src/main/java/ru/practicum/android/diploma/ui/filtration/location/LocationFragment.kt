package ru.practicum.android.diploma.ui.filtration.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    // !!!!!В ЭТОМ ФРАГМЕНТЕ ВРЕМЕННЫЙ КОД!!!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Получить выбранную страну из аргументов, если она есть
        val selectedCountry = arguments?.getString("selectedCountry")
        if (!selectedCountry.isNullOrEmpty()) {
            binding.etCountry.setText(selectedCountry)
            binding.etCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clean_icon, 0)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }

        val selectedCountryId = arguments?.getString("selectedCountryId")

        // Получить выбранный регион из аргументов, если он есть
        val selectedRegion = arguments?.getString("selectedRegion")
        if (!selectedRegion.isNullOrEmpty()) {
            binding.etRegion.setText(selectedRegion)
            binding.etRegion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clean_icon, 0)
            binding.btnSelectionContainer.visibility = View.VISIBLE
        }
        setupCountryField()
        setupRegionField(countryId = selectedCountryId.toString())
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
                binding.btnSelectionContainer.visibility = View.GONE
                true
            } else {
                false
            }
        }
    }

    private fun setupCountryField() {
        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_countryFragment)
        }
    }

    private fun setupRegionField(countryId: String) {
        binding.etRegion.setOnClickListener {
            val bundle = Bundle().apply {
                putString("selectedCountryId", countryId)
            }

            findNavController().navigate(R.id.action_locationFragment_to_regionFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
