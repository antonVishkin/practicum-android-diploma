package ru.practicum.android.diploma.ui.vacancies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails

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
        viewModel.stateLiveData.observe(viewLifecycleOwner){ render(it)}
        Log.d("DETAILS", "vacancy_model $vacancyId")
        viewModel.fetchVacancyDetails(vacancyId)


    }

    private fun render(state: VacancyState) {
        when(state){
            is VacancyState.Loading -> showLoading()
            is VacancyState.Error -> showError()
            is VacancyState.Content -> showContent(state.vacancyDetails)
        }
    }

    private fun showContent(vacancyDetails: VacancyDetails) {
        binding.apply {
            binding.tvJobTitle.text = vacancyDetails.name
            binding.tvSalary.text = vacancyDetails.salary.toString()
            binding.tvCompanyName.text = vacancyDetails.employerName
            binding.tvLocation.text = vacancyDetails.areaName
            binding.tvExperience.text = vacancyDetails.experienceName
            binding.tvJobDescriptionValue.text = vacancyDetails.description
            binding.tvKeySkills.text =vacancyDetails.keySkills.toString()
            binding.tvContacts.text = vacancyDetails.contactPerson
            binding.tvEmail.text = vacancyDetails.email
            binding.tvTelephone.text = vacancyDetails.phones.toString()
        }
    }

    private fun showError() {
        TODO("Not yet implemented")
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
