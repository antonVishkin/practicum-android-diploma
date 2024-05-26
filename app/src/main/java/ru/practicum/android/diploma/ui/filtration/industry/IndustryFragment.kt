package ru.practicum.android.diploma.ui.filtration.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.root.RootActivity

class IndustryFragment : Fragment() {
    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustryViewModel>()
    private val industryAdapter = IndustryAdapter { industryItem ->
        clickDebounce?.let { it(industryItem) }
        viewModel.onIndustryItemClicked(industryItem)
    }
    private var clickDebounce: ((Industry) -> Unit)? = null

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

    }

    private fun bind() {
        with(binding) {
            etSelectIndustry.doAfterTextChanged { text ->
                viewModel.filteredIndustries(text.toString())
                if (text.isNullOrEmpty()) {
                    btnClear.setImageResource(R.drawable.ic_search)
                } else {
                    btnClear.setImageResource(R.drawable.ic_close)
                }
            }
            btnClear.setOnClickListener {
                etSearch.text.clear()
            }
            rvSearchResult.layoutManager = LinearLayoutManager(requireContext())
            rvSearchResult.adapter = industryAdapter
            btnSelect.setOnClickListener {
                viewModel.saveSelectedIndustry()
                findNavController().navigateUp()
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
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

        toolbar.title = getString(R.string.title_industry)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
