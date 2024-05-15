package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewHolder (private val binding: VacancyViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Vacancy) {
        val vacancyName = "${item.name}, ${item.city ?: "Город не указан"}"
        binding.tvVacancyName.text = vacancyName
        binding.tvVacancyCompany.text = item.employerName
        binding.tvVacancySalary.text = item.salary
        Glide.with(itemView)
            .load(item.id)
            .placeholder(R.drawable.vacancies_placeholder)
            .into(binding.ivIconVacancy)
    }

}
