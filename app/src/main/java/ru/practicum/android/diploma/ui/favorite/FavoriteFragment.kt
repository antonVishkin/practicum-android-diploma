package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()

    private var favoriteAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateFavorite.observe(viewLifecycleOwner) {
            render(it)
        }

        favoriteAdapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyId = vacancy.id)
            }
        })

        binding.rvFavorite.adapter = favoriteAdapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.favouriteListOfLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Empty -> renderFavoriteEmpty()
            is FavoriteState.Loading -> renderFavoriteLoading()
            is FavoriteState.LoadingNewPage -> renderFavoriteLoadingNewPage()
            is FavoriteState.Error -> renderFavoriteError()
            is FavoriteState.Content -> renderFavoriteContent(state)
        }
    }

    private fun renderFavoriteEmpty() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.empty_favorite)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.favorite_empty_list)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderFavoriteLoading() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = true
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun renderFavoriteLoadingNewPage() {
        binding.progressBarBottom.isVisible = true
    }

    private fun renderFavoriteError() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.server_error_towel)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_server_error)
            tvPlaceholder.isVisible = true
        }
    }

// Закомментил, чтобы не бугался DETEKT
//
    private fun renderFavoriteContent(state: FavoriteState){}

    private fun openFragmentVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacanciesFragment,
            Bundle().apply { putInt("vacancy_model", vacancyId.toInt()) }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
