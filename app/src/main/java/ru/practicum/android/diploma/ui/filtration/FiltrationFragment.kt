package ru.practicum.android.diploma.ui.filtration

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.filtration.industry.IndustryFragment
import ru.practicum.android.diploma.ui.root.RootActivity

class FiltrationFragment : Fragment() {
    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FiltrationViewModel>()
    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.getFiltrationFromPrefs()
        var industry = getIndustry()
        Log.d(IndustryFragment.SELECTED_INDUSTRY_KEY, "Фрагмент фильтрации $industry")
        if (industry != null) viewModel.setIndustry(industry)
        val country = getCountry()
        val region = getRegion()
        if (country != null) {
            val resultCountry = if (region != null) {
                Country(country.id, country.name, listOf(region))
            } else {
                country
            }
            viewModel.setArea(resultCountry)
        }
        binding.checkBoxSalary.setOnClickListener {
            viewModel.setCheckbox(binding.checkBoxSalary.isChecked)
        }
        viewModel.isChanged.observe(viewLifecycleOwner) {
            showSaveButton(it)
        }
        binding.buttonRemove.setOnClickListener {
            viewModel.setEmpty()
        }
        binding.buttonSave.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.etSalary.setOnKeyListener(onKeyListener())
        binding.etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
        binding.etIndustry.setOnClickListener { onIndustryClick.invoke() }

    }

    private fun getRegion(): Region? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(SELECTED_REGION_KEY, Region::class.java)
    } else {
        arguments?.getParcelable(SELECTED_REGION_KEY)
    }

    private fun getCountry(): Country? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(SELECTED_COUNTRY_KEY, Country::class.java)
    } else {
        arguments?.getParcelable(SELECTED_COUNTRY_KEY)
    }

    private fun getIndustry(): Industry? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(SELECTED_INDUSTRY_KEY, Industry::class.java)
    } else {
        arguments?.getParcelable(SELECTED_INDUSTRY_KEY)
    }

    private fun onKeyListener(): View.OnKeyListener? {
        return View.OnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.etSalary.windowToken, 0)
                viewModel.setSalary(binding.etSalary.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun showSaveButton(show: Boolean) {
        binding.buttonSave.isVisible = show
    }

    private fun render(state: FiltrationState) {
        when (state) {
            is FiltrationState.Empty -> showEmpty()
            is FiltrationState.Content -> {
                showContent(
                    state.filtration.area,
                    state.filtration.industry,
                    state.filtration.salary,
                    state.filtration.onlyWithSalary
                )
            }

        }
    }

    private fun showContent(area: Country?, industry: Industry?, salary: String?, salaryEmptyNotShowing: Boolean) {
        binding.apply {
            showArea(area)
            showIndustry(industry)
            if (!salary.isNullOrEmpty()) {
                etSalary.setText(salary)
            }
            checkBoxSalary.isChecked = salaryEmptyNotShowing
            buttonRemove.isVisible = true
        }
    }

    private fun showIndustry(industry: Industry?) {
        binding.apply {
            if (industry != null) {
                etIndustry.setText(industry.name)
                ilIndustry.setEndIconDrawable(R.drawable.clean_icon)
                ilIndustry.setEndIconOnClickListener {
                    viewModel.setIndustry(null)
                    industryEndIconListener()
                }
            }
        }
    }

    private fun showArea(area: Country?) {
        binding.apply {
            if (area != null) {
                var text = if (area.regions != null && area.regions.isNotEmpty()) {
                    area.name + ", " + area.regions[0].name
                } else {
                    area.name
                }
                etAreaOfWork.setText(text)
                ilAreaOfWork.setEndIconDrawable(R.drawable.clean_icon)
                ilAreaOfWork.setEndIconOnClickListener {
                    viewModel.setArea(null)
                    areaEndIconListener()
                }
            }
        }
    }

    private fun industryEndIconListener() {
        binding.apply {
            etIndustry.setText("")
            ilIndustry.clearOnEndIconChangedListeners()
            ilIndustry.setEndIconDrawable(R.drawable.arrow_forward)
            etIndustry.setOnClickListener { onIndustryClick.invoke() }
            ilIndustry.setEndIconOnClickListener { onIndustryClick.invoke() }
        }
    }

    private fun areaEndIconListener() {
        binding.apply {
            etAreaOfWork.setText("")
            ilAreaOfWork.clearOnEndIconChangedListeners()
            ilAreaOfWork.setEndIconDrawable(R.drawable.arrow_forward)
            etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
            ilAreaOfWork.setEndIconOnClickListener { onAreaClick.invoke() }
        }
    }

    private fun showEmpty() {
        binding.apply {
            areaEndIconListener()
            industryEndIconListener()
            etSalary.setText("")
            checkBoxSalary.isChecked = false
            buttonSave.isVisible = false
            buttonRemove.isVisible = false
        }
    }

    private val onAreaClick: () -> Unit = {
        val country = viewModel.getCountry()
        val args = Bundle()
        args.apply {
            if (country != null) {
                args.putParcelable(SELECTED_COUNTRY_KEY, country)
                if (country.regions.isNotEmpty()) {
                    args.putParcelable(SELECTED_REGION_KEY, country.regions[0])
                }
            }
        }
        findNavController().navigate(R.id.action_filtrationFragment_to_locationFragment, args)
    }

    private val onIndustryClick: () -> Unit = {
        val bundle = Bundle().apply {
            val selectedIndustry = viewModel.getIndustry()
            putParcelable(IndustryFragment.SELECTED_INDUSTRY_KEY, selectedIndustry)
            Log.d(IndustryFragment.SELECTED_INDUSTRY_KEY, "Фрагмент отрасли $selectedIndustry")
        }
        findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment, bundle)
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
        toolbar.title = getString(R.string.title_filtration)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onPause() {
        viewModel.setSalary(binding.etSalary.text.toString())
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val INDUSTRY = "industry"
        private const val SELECTED_COUNTRY_KEY = "selectedCountry"
        private const val SELECTED_REGION_KEY = "selectedRegion"
        private const val SELECTED_INDUSTRY_KEY = "selectedIndustry"
    }
}
