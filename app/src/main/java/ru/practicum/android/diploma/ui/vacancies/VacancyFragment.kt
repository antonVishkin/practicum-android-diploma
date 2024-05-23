package ru.practicum.android.diploma.ui.vacancies

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.SalaryFormat

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
        binding.toolbar.setTitle(R.string.title_vacancies)
        val navController = (activity as RootActivity).navController
        binding.toolbar.setupWithNavController(navController!!, AppBarConfiguration(navController.graph))
        val vacancyId = arguments?.getString("vacancy_model") ?: ""
        viewModel.stateLiveData.observe(viewLifecycleOwner) { render(it) }
        Log.d("DETAILS", "vacancy_model $vacancyId")
        viewModel.fetchVacancyDetails(vacancyId)
        binding.btnFavorite.setOnClickListener {
            viewModel.addToFavorite()
        }
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Loading -> showLoading()
            is VacancyState.Error -> showError()
            is VacancyState.Content -> showContent(state.vacancyDetails, state.currencySymbol, state.isFavorite)
        }
    }

    private fun showContent(vacancyDetails: VacancyDetails, currencySymbol: String, isFavorite: Boolean) {
        binding.apply {
            btnFavorite.isVisible = true
            btnShare.isVisible = true
            nsvDetailsContent.isVisible = true
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            progressBar.isVisible = false
            tvJobTitle.text = vacancyDetails.name
            tvSalary.text = SalaryFormat.formatSalary(
                context = requireContext(),
                salaryFrom = vacancyDetails.salary?.from,
                salaryTo = vacancyDetails.salary?.to,
                currencySymbol = currencySymbol
            )
            tvCompanyName.text = vacancyDetails.employerName
            tvLocation.text = vacancyDetails.areaName
            tvExperience.text = vacancyDetails.experienceName
            tvJobDescriptionValue.text = Html.fromHtml(vacancyDetails.description, Html.FROM_HTML_MODE_COMPACT)
            if (vacancyDetails.keySkills.isEmpty()) {
                tvKeySkillsLabel.isVisible = false
                tvKeySkills.isVisible = false
            } else {
                tvKeySkills.text = vacancyDetails.keySkills.joinToString("\n• ", "• ", "")
            }
            Glide.with(binding.root)
                .load(vacancyDetails.logoUrl)
                .placeholder(R.drawable.vacancies_placeholder)
                .centerCrop()
                .transform(RoundedCorners(R.dimen.radius_vacancy_icon))
                .into(ivCompanyLogo)
            contactsLogicShoving(vacancyDetails)
            if (isFavorite) {
                btnFavorite.setImageResource(R.drawable.heart_on_icon)
            } else {
                btnFavorite.setImageResource(R.drawable.heart_icon)
            }
        }

    }

    private fun showError() {
        binding.progressBar.isVisible = false
        binding.ivPlaceholder.isVisible = true
        binding.tvPlaceholder.isVisible = true
        binding.btnFavorite.isVisible = false
        binding.btnShare.isVisible = false
        binding.nsvDetailsContent.isVisible = false
    }

    private fun showLoading() {
        binding.nsvDetailsContent.isVisible = false
        binding.btnFavorite.isVisible = false
        binding.btnShare.isVisible = false
        binding.ivPlaceholder.isVisible = false
        binding.tvPlaceholder.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun contactsLogicShoving(vacancyDetails: VacancyDetails) {
        binding.apply {
            if (vacancyDetails.contactPerson == null &&
                vacancyDetails.email == null &&
                vacancyDetails.phones.isNullOrEmpty()
            ) {
                tvContactsLabel.isVisible = false
            }
            if (vacancyDetails.contactPerson != null) {
                tvContacts.text = vacancyDetails.contactPerson
            } else {
                tvContactsPersonLabel.isVisible = false
                tvContacts.isVisible = false
            }
            if (vacancyDetails.email != null) {
                tvEmail.text = vacancyDetails.email
            } else {
                tvEmailLabel.isVisible = false
                tvEmail.isVisible = false
            }
            if (vacancyDetails.phones.isNullOrEmpty()) {
                tvTelephoneLable.isVisible = false
                tvTelephone.isVisible = false
            } else {
                tvTelephone.text = vacancyDetails.phones.joinToString(",", "", "")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
