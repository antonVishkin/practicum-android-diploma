package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import androidx.navigation.fragment.findNavController
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var _adapter: VacancyAdapter? = null
    private val vacancyList: ArrayList<Vacancy> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateSearch.observe(viewLifecycleOwner) {
            render(it)
        }

        _adapter = VacancyAdapter(vacancyList, object : VacancyAdapter.OnClickListener {
            override fun onClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyId = vacancy.id)
            }
        })
        binding.rvSearch.adapter = _adapter

        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        viewModel.search("test")
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> renderSearchLoading()
            is SearchState.Default -> renderSearchDefault()
            is SearchState.Empty -> renderSearchEmpty()
            is SearchState.NoConnection -> renderSearchNoConnection()
            is SearchState.Error -> renderSearchError()
            is SearchState.Content -> renderSearchContent()
        }
    }

    private fun renderSearchLoading() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = true
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun renderSearchDefault() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.search_man)
            ivPlaceholder.isVisible = true
            tvPlaceholder.isVisible = false
            etButtonSearch.setText("")
            hideKeyboard()
        }
    }

    private fun renderSearchEmpty() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.nothing_found_cat)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_vacancy_list_not_found)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderSearchError() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.server_error_towel)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_server_error)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderSearchNoConnection() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.no_internet_scull)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_no_connection)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderSearchContent() {
        with(binding) {
            tvButtonSearchResult.text =
                requireContext().getString(R.string.found_vacancies_count, vacancyList.size)
            tvButtonSearchResult.isVisible = true
            rvSearch.isVisible = true
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun openFragmentVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacanciesFragment,
            Bundle().apply { putInt("vacancy_model", vacancyId.toInt()) }
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etButtonSearch.windowToken, 0)
        binding.etButtonSearch.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
