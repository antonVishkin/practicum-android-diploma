package ru.practicum.android.diploma.ui.filtration

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
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
        val industry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(INDUSTRY, Industry::class.java)
        } else {
            arguments?.getParcelable(INDUSTRY)
        }
        if (industry != null) viewModel.setIndustry(industry)
        val area = arguments?.getString(AREA) ?: null
        if (area != null) {
            viewModel.setArea(area)
        }
        binding.checkBoxSalary.setOnClickListener {
            viewModel.setCheckbox(binding.checkBoxSalary.isChecked)
        }
        viewModel.isChanged.observe(viewLifecycleOwner){
            Log.v("FILTRATION","is button visible $it")
            showSaveButton(it)
        }
        binding.buttonRemove.setOnClickListener {
            viewModel.setEmpty()
        }
    }

    private fun showSaveButton(show:Boolean) {
        Log.v("FILTRATION","is button visible $show")
        binding.buttonSave.isVisible = show
        Log.v("FILTRATION","button visibility ${binding.buttonSave.isVisible}")

    }

    private fun render(state: FiltrationState) {
        when (state) {
            is FiltrationState.Empty -> showEmpty()
            is FiltrationState.Content -> {
                Log.v("FILTRATION", "render fragment ${state.filtration.industry}")
                showContent(
                    state.filtration.area,
                    state.filtration.industry,
                    state.filtration.salary,
                    state.filtration.onlyWithSalary
                )
            }

        }
    }

    private fun showContent(area: Area?, industry: Industry?, salary: String?, salaryEmptyNotShowing: Boolean) {
        binding.apply {
            if (area != null) {
                etAreaOfWork.setText(area.name)
                ilAreaOfWork.setEndIconDrawable(R.drawable.clean_icon)
                ilAreaOfWork.setEndIconOnClickListener {
                    areaEndIconListener()
                }
            } else {
                etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
            }
            Log.v("FILTRATION", "industry $industry")
            if (industry != null) {
                etIndustry.setText(industry.name)
                ilIndustry.setEndIconDrawable(R.drawable.clean_icon)
                ilIndustry.setEndIconOnClickListener {
                    industryEndIconListener()
                }
            } else {
                etIndustry.setText("")
                etIndustry.setOnClickListener { onIndustryClick.invoke() }
            }
            if (!salary.isNullOrEmpty()) {
                etSalary.setText(salary)
            }
            checkBoxSalary.isChecked = salaryEmptyNotShowing
            buttonSave.isVisible = false
            buttonRemove.isVisible = true
        }
    }

    private fun industryEndIconListener() {
        binding.apply {
            viewModel.setIndustry(null)
            ilIndustry.setEndIconDrawable(R.drawable.arrow_forward)
            ilIndustry.clearOnEndIconChangedListeners()
            etIndustry.setOnClickListener { onIndustryClick.invoke() }
            ilIndustry.setEndIconOnClickListener { onIndustryClick.invoke() }
        }
    }

    private fun areaEndIconListener() {
        binding.apply {
            etAreaOfWork.setText("")
            ilAreaOfWork.setEndIconDrawable(R.drawable.arrow_forward)
            ilAreaOfWork.clearOnEndIconChangedListeners()
            etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
        }
    }

    private fun showEmpty() {
        binding.apply {
            etAreaOfWork.setText("")
            etIndustry.setText("")
            etSalary.setText("")
            checkBoxSalary.isChecked = false
            buttonSave.isVisible = false
            buttonRemove.isVisible = false
            etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
            etIndustry.setOnClickListener { onIndustryClick.invoke() }
        }
    }

    private val onAreaClick: () -> Unit = {
        // findNavController().navigate()
    }

    private val onIndustryClick: () -> Unit = {
        findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
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
            val args = Bundle().putString(AREA, "")
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
        const val AREA = "area"
    }
}
