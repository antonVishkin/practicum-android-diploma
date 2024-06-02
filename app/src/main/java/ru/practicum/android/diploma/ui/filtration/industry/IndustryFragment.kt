package ru.practicum.android.diploma.ui.filtration.industry

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.root.RootActivity

class IndustryFragment : Fragment() {
    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustryViewModel>()
    private var industriesList: List<Industry>? = null
    private var selectedIndustry: Industry? = null

    private val adapter = IndustryAdapter { industry ->
        selectedIndustry = industry
        hideKeyboard()
        viewModel.saveSelectIndustry()
    }

    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        viewModel.stateIndustry.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustry.adapter = adapter

        viewModel.searchIndustries()

        val industry = getIndustry()
        Log.d(SELECTED_INDUSTRY_KEY, "Фрагмент отрасли при загрузке $industry")

        binding.etSelectIndustry.addTextChangedListener(textWatcherListener())

        binding.buttonSelectIndustry.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(SELECTED_INDUSTRY_KEY, selectedIndustry)
                Log.d(SELECTED_INDUSTRY_KEY, "Фрагмент отрасли $selectedIndustry")
            }
            findNavController().navigate(R.id.action_industryFragment_to_filtrationFragment, bundle)
        }

        binding.ivClear.setOnClickListener {
            with(binding) {
                etSelectIndustry.setText("")
                ivClear.setImageResource(R.drawable.search_icon)
            }
            adapter.industries.clear()
            adapter.selectedIndustry = null
            adapter.notifyDataSetChanged()
        }
    }

    private fun getIndustry(): Industry? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(SELECTED_INDUSTRY_KEY, Industry::class.java)
    } else {
        arguments?.getParcelable(SELECTED_INDUSTRY_KEY)
    }

    private fun selectIndustry(industry: Industry?) {
        if (industry != null) {
            industriesList?.forEach {
                it.isSelected = it.id == industry.id
            }
            adapter.selectedIndustry = industry
            adapter.notifyDataSetChanged()
            adapter.logIndustriesState()
        }
    }

    private fun render(state: IndustryState) {
        when (state) {
            is IndustryState.Loading -> renderLoading()
            is IndustryState.NotFound -> renderNotFound()
            is IndustryState.Selected -> renderSelected()
            is IndustryState.ServerError -> renderServerError()
            is IndustryState.NoConnection -> renderNoConnection()
            is IndustryState.Content -> {
                renderContent(state)
                industriesList = state.industries
                updateAdapterData(state.industries)
                selectIndustry(getIndustry())
            }
        }
    }

    private fun updateAdapterData(industries: List<Industry>) {
        adapter.industries.clear()
        adapter.industries = industries.toMutableList()
        adapter.notifyDataSetChanged()
        adapter.logIndustriesState()
    }

    private fun renderLoading() {
        with(binding) {
            progressBar.isVisible = true
        }
    }

    private fun renderNotFound() {
        with(binding) {
            rvIndustry.isVisible = false
            progressBar.isVisible = false
            buttonSelectIndustry.isVisible = false
            ivPlaceholder.isVisible = true
            ivPlaceholder.setImageResource(R.drawable.nothing_found_cat)
            tvPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.industry_not_found)
        }
    }

    private fun renderSelected() {
        with(binding) {
            buttonSelectIndustry.isVisible = true
            progressBar.isVisible = false
        }
    }

    private fun renderContent(state: IndustryState) {
        with(binding) {
            rvIndustry.isVisible = true
            progressBar.isVisible = false
            buttonSelectIndustry.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun renderServerError() {
        with(binding) {
            rvIndustry.isVisible = false
            progressBar.isVisible = false
            ivPlaceholder.isVisible = true
            ivPlaceholder.setImageResource(R.drawable.server_error_cat)
            tvPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_server_error)
            buttonSelectIndustry.isVisible = false
        }
    }

    private fun renderNoConnection() {
        with(binding) {
            rvIndustry.isVisible = false
            progressBar.isVisible = false
            ivPlaceholder.isVisible = true
            ivPlaceholder.setImageResource(R.drawable.no_internet_scull)
            tvPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_no_connection)
            buttonSelectIndustry.isVisible = false
        }
    }

    private fun textWatcherListener() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!binding.etSelectIndustry.text.toString().isNullOrEmpty()) {
                binding.ivClear.setImageResource(R.drawable.clean_icon)
                viewModel.searchDebounce(s.toString())
            } else {
                binding.ivClear.setImageResource(R.drawable.search_icon)
                hideKeyboard()
                viewModel.searchIndustries()
            }
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etSelectIndustry.windowToken, 0)
        binding.etSelectIndustry.isEnabled = true
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

        toolbar.title = getString(R.string.title_industry)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SELECTED_INDUSTRY_KEY = "selectedIndustry"
    }
}
