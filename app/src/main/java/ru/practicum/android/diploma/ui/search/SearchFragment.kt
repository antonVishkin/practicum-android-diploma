package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var _adapter: VacancyAdapter? = null
    private var querySearchText = ""

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

        _adapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyId = vacancy.id)
            }
        })

        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.rvSearch.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = _adapter?.itemCount ?: 0
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })

        binding.etButtonSearch.doOnTextChanged { text, _, _, _ ->
            hideIconEditText(text)
            querySearchText = text.toString()
            if (binding.etButtonSearch.hasFocus()) {
                text?.let {
                    viewModel.searchDebounce(it.toString())
                    querySearchText = it.toString()
                }
            }
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> renderSearchLoading()
            is SearchState.Default -> renderSearchDefault()
            is SearchState.Empty -> renderSearchEmpty()
            is SearchState.NoConnection -> renderSearchNoConnection()
            is SearchState.ServerError -> renderSearchError()
            is SearchState.Content -> renderSearchContent(state.vacancyPage, state.currencyDictionary)
            is SearchState.NewPageLoading -> renderNewPageLoading()
        }
    }

    private fun renderNewPageLoading() {
        binding.progressBarBottom.isVisible = true
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

    private fun renderSearchContent(vacancyPage: VacancyPage, currencyDictionary: Map<String, Currency>) {
        if (vacancyPage.currPage == 0) {
            _adapter?.vacancyList?.clear()
        }
        _adapter?.vacancyList?.addAll(vacancyPage.vacancyList)
        _adapter?.currencyDictionary?.clear()
        _adapter?.currencyDictionary?.putAll(currencyDictionary)
        _adapter?.notifyDataSetChanged()
        with(binding) {
            tvButtonSearchResult.text =
                requireContext().getString(R.string.found_vacancies_count, vacancyPage.found)
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

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    private fun hideIconEditText(text: CharSequence?) {
        val editText = binding.etButtonSearch

        if (text.isNullOrEmpty()) {
            binding.etButtonSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.search_icon,
                0
            )
            editText.setOnTouchListener { _, motionEvent ->
                false
            }
        } else {
            binding.etButtonSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.clean_icon,
                0
            )
            val iconClear = editText.compoundDrawables[2]
            editText.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_UP &&
                    motionEvent.rawX >= editText.right - iconClear.bounds.width() * 2
                ) {
                    editText.isEnabled = false
                    viewModel.setStateDefault()
                }
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
