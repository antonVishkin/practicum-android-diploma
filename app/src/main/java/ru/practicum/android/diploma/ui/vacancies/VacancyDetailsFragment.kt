package ru.practicum.android.diploma.ui.vacancies

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.SalaryFormat

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private var adapter: ContactsAdapter? = null
    private var paramVacancyId: String? = null
    lateinit var onItemClickDebounce: (Phone) -> Unit

    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

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

        paramVacancyId = arguments?.getString("vacancy_model") ?: ""

        viewModel.stateLiveData.observe(viewLifecycleOwner) { render(it) }

        if (paramVacancyId != null) {
            paramVacancyId?.let { viewModel.fetchVacancyDetails(it) }
        }
    }

    private fun render(state: VacancyDetailsState) {
        when (state) {
            is VacancyDetailsState.Error -> showError()
            is VacancyDetailsState.Loading -> showLoading()
            is VacancyDetailsState.Content -> {
                val vacancyDetails = state.vacancy
                toolbarSetup(vacancyDetails)
                showContent(state.vacancy, state.currencySymbol, state.isFavorite)
            }
        }
    }

    private fun showContent(vacancy: Vacancy, currencySymbol: String, isFavorite: Boolean) {
        toolbar.menu.findItem(R.id.share).isVisible = true
        toolbar.menu.findItem(R.id.favorite).isVisible = true
        toolbar.menu.findItem(R.id.filters).isVisible = false

        binding.apply {
            nsvDetailsContent.isVisible = true
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            progressBar.isVisible = false
            tvJobTitle.text = vacancy.vacancyName
            tvSalary.text = SalaryFormat.formatSalary(
                context = requireContext(),
                salaryFrom = vacancy.salary?.from,
                salaryTo = vacancy.salary?.to,
                currencySymbol = currencySymbol
            )
            tvCompanyName.text = vacancy.employment
            tvLocation.text = vacancy.city
            tvExperience.text = vacancy.experience
            tvJobDescriptionValue.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_COMPACT)
            if (vacancy.keySkills.isEmpty()) {
                tvKeySkillsLabel.isVisible = false
                tvKeySkills.isVisible = false
            } else {
                tvKeySkills.text = vacancy.keySkills.joinToString("\n• ", "• ", "")
            }
            Glide.with(binding.root)
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.vacancies_placeholder)
                .centerCrop()
                .transform(RoundedCorners(R.dimen.radius_vacancy_icon))
                .into(ivCompanyLogo)
            contactsLogicShoving(vacancy)

            if (isFavorite) {
                toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_on_icon)
            } else {
                toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_icon)
            }
        }
    }

    private fun showError() {
        binding.apply {
            progressBar.isVisible = false
            ivPlaceholder.isVisible = true
            tvPlaceholder.isVisible = true
            nsvDetailsContent.isVisible = false
        }
    }

    private fun showLoading() {
        binding.apply {
            nsvDetailsContent.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun contactsLogicShoving(vacancy: Vacancy) {
        binding.apply {
            if (vacancy.contacts?.name?.isNotEmpty() == true ||
                vacancy.contacts?.email?.isNotEmpty() == true ||
                vacancy.contacts?.phones?.toString()?.isNotEmpty() == true) {
                tvContactsLabel.isVisible = true
                showContactsName(vacancy)
                showContactsEmail(vacancy)
                showContactsPhone(vacancy)
                showContactsComment(vacancy)
            } else tvContactsLabel.isVisible = false
        }
    }

    private fun showContactsEmail(vacancy: Vacancy) {
        binding.apply {
            if (vacancy.contacts?.email?.isNotEmpty() == true) {
                tvEmail.text = vacancy.contacts.email
                tvEmail.isVisible = true
                tvEmailLabel.isVisible = true
                binding.tvEmail.setOnClickListener {
                    val v = viewModel.currentVacancy.value
                    if (viewModel.clickDebounce() && v?.contacts?.email != null)
                        viewModel.eMail(v.contacts.email)
                }
            } else {
                tvEmail.isVisible = false
                tvEmailLabel.isVisible = false
            }
        }
    }

    private fun showContactsName(vacancy: Vacancy) {
        binding.apply {
            if (vacancy.contacts?.name?.isNotEmpty() == true) {
                tvContactsPersonLabel.isVisible = true
                tvContacts.text = vacancy.contacts.name
                tvContacts.isVisible = true
            } else {
                tvContactsPersonLabel.isVisible = false
                tvContacts.isVisible = false
            }
        }
    }

    private fun showContactsPhone(vacancy: Vacancy) {
        binding.apply {
            if (vacancy.contacts?.phones?.isNotEmpty() == true) {
                rvPhones.isVisible = true
                tvTelephoneLable.isVisible = true
                if (viewModel.clickDebounce()) {
                    setPhonesAdapter(vacancy)
                }
            } else {
                rvPhones.isVisible = false
                tvTelephoneLable.isVisible = false
            }
        }
    }

    private fun showContactsComment(vacancy: Vacancy) {
        binding.apply {
            if (vacancy.comment?.isNotEmpty() == true) {
                tvCommentLabel.isVisible = true
                tvComment.text = vacancy.comment
                tvComment.isVisible = true
            } else {
                tvCommentLabel.isVisible = false
                tvComment.isVisible = false
            }
        }
    }

    private fun setPhonesAdapter(vacancy: Vacancy) {
        adapter = vacancy.contacts?.phones?.let {
            ContactsAdapter(it) { phone -> onItemClickDebounce(phone) }
        }
        onItemClickDebounce = { phone -> viewModel.phoneCall(phone) }
        binding.rvPhones.adapter = adapter
    }

    private fun toolbarSetup(vacancy: Vacancy) {
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        toolbar.title = getString(R.string.title_vacancies)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false

        toolbar.menu.findItem(R.id.favorite).setOnMenuItemClickListener {
            viewModel.addToFavorite(vacancy)
            true
        }

        toolbar.menu.findItem(R.id.share).setOnMenuItemClickListener {
            viewModel.shareApp(vacancy.alternateUrl!!)
            true
        }
    }

    override fun onStop() {
        super.onStop()
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.heart_icon)
        toolbar.navigationIcon = null
        _binding = null
    }
}
