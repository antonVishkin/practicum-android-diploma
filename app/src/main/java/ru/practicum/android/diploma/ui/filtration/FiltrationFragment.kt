package ru.practicum.android.diploma.ui.filtration

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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
        val industry = arguments?.getString(INDUSTRY)?:null
        val area = arguments?.getString(AREA)?:null
    }

    private fun render(state: FiltrationState) {
        when (state) {
            is FiltrationState.Empty -> showEmpty()
            is FiltrationState.Content -> showContent(
                state.area,
                state.industry,
                state.salary,
                state.salaryEmptyNotShowing
            )

        }
    }

    private fun showContent(area: Area?, industry: Industry?, salary: String?, salaryEmptyNotShowing: Boolean) {
        binding.apply {
            Log.v("FILTRATION", "show content $salary")
            if (area != null) {
                etAreaOfWork.setText(area.name)
                ilAreaOfWork.setEndIconDrawable(R.drawable.clean_icon)
                ilAreaOfWork.setEndIconOnClickListener {
                    binding.apply {
                        Log.v("FILTRATION", "Clear area")
                        etAreaOfWork.setText("")
                        ilAreaOfWork.setEndIconDrawable(R.drawable.arrow_forward)
                        ilAreaOfWork.clearOnEndIconChangedListeners()
                        etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
                    }
                }
            }
            if (industry != null) {
                etIndustry.setText(industry.name)
                ilIndustry.setEndIconDrawable(R.drawable.clean_icon)
                ilIndustry.setEndIconOnClickListener {
                    binding.apply {
                        Log.v("FILTRATION", "Clear industry")
                        etIndustry.setText("")
                        ilIndustry.setEndIconDrawable(R.drawable.arrow_forward)
                        ilIndustry.clearOnEndIconChangedListeners()
                        etIndustry.setOnClickListener { onIndustryClick.invoke() }
                    }
                }
            }
            if (!salary.isNullOrEmpty()) {
                etSalary.setText(salary)
            }
            checkBoxSalary.isChecked = salaryEmptyNotShowing
            buttonSave.isVisible = true
            buttonRemove.isVisible = true
        }
    }

    private fun showEmpty() {
        binding.apply {
            buttonSave.isVisible = false
            buttonRemove.isVisible = false
            etAreaOfWork.setOnClickListener { onAreaClick.invoke() }
            etIndustry.setOnClickListener { onIndustryClick.invoke() }
        }
    }

    private val onAreaClick: () -> Unit = {
        Log.v("FILTRATION", "Navigate to Area")
        //findNavController().navigate()
    }

    private val onIndustryClick: () -> Unit = {
        Log.v("FILTRATION", "Navigate to Industry")
        //findNavController().navigate()
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
            val args = Bundle().putString(AREA,"")
            findNavController().navigateUp()
        }

        toolbar.title = getString(R.string.title_filtration)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val INDUSTRY = "industry"
        const val AREA = "area"
    }
}
