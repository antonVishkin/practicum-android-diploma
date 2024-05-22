package ru.practicum.android.diploma.ui.vacancies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = arguments?.getString("vacancy_model") ?: ""
        Log.d("DETAILS", "vacancy_model $vacancyId")
        viewModel.fetchVacancyDetails(vacancyId)

        viewModel.vacancyDetails.observe(viewLifecycleOwner) { vacancyDetails ->
            // Update UI with vacancy details
            binding.tvJobTitle.text = vacancyDetails.name
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle error case
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
