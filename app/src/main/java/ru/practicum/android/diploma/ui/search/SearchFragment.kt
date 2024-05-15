package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var _vacancyAdapter: VacancyAdapter? = null
    private val vacancyAdapter get() = _vacancyAdapter!!
    private var isClickAllowed = true


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

        _vacancyAdapter = VacancyAdapter(
            clickListener = {
                if (isClickAllowed) {
                    clickAdapting(it)
                }
            }
        )

        binding.rvSearch.adapter = vacancyAdapter

        // Загрузка данных и установка их в адаптер
//        loadDataAndSetAdapter()
    }

    /*private fun loadDataAndSetAdapter() {
        // Здесь загрузите данные для адаптера, например, из базы данных или с сервера
        val vacancies = loadVacancies()

        // Проверка наличия данных
        if (vacancies.isNotEmpty()) {
            // Если данные есть, установите их в адаптер и покажите RecyclerView
            vacancyAdapter.setVacancies(vacancies)
            binding.rvSearch.visibility = View.VISIBLE
        } else {
            // Если данных нет, скройте RecyclerView
            binding.rvSearch.visibility = View.GONE
        }
    }

    private fun loadVacancies(): List<Vacancy> {
        val vacancies = mutableListOf<Vacancy>()

        // Добавим несколько тестовых вакансий в список
        vacancies.add(Vacancy("1", "Software Engineer", "50000", "New York", "Tech Company"))
        vacancies.add(Vacancy("2", "Product Manager", "60000", "San Francisco", "Startup"))
        vacancies.add(Vacancy("3", "Data Scientist", "70000", "Chicago", "Big Corporation"))

        // Вернем список вакансий
        return vacancies
    }*/

    override fun onResume() {
        super.onResume()
        isClickAllowed = true
    }

    private val vacancyClickDebounce by lazy {
        debounce<Vacancy>(
            DEBOUNCE_DELAY_MS,
            coroutineScope = CoroutineScope(Dispatchers.Main),
            useLastParam = true
        ) { clickedVacancy ->
            val bundle = Bundle().apply {
                putString("id", clickedVacancy.id)
                putString("name", clickedVacancy.name)
                putString("salary", clickedVacancy.salary)
                putString("city", clickedVacancy.city)
                putString("employerName", clickedVacancy.employerName)
            }
            val navController = findNavController()
            navController.navigate(R.id.action_searchFragment_to_vacanciesFragment, bundle)
        }
    }

    private fun clickAdapting(vacancy: Vacancy) {
        if (isClickAllowed) {
            vacancyClickDebounce(vacancy)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val DEBOUNCE_DELAY_MS = 300L
    }
}
