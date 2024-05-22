package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SalaryFormat

class VacancyViewHolder(private val binding: VacancyViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: Vacancy, onClickListener: VacancyAdapter.OnClickListener, currencySymbol: String) {
        with(binding) {
            tvVacancyName.text = buildString {
                append(vacancy.name)
                vacancy.city?.takeIf { it.isNotEmpty() }?.let {
                    append(", $it")
                }
            }
            tvVacancyCompany.text = vacancy.employerName
            tvVacancySalary.text = SalaryFormat.formatSalary(
                itemView.context,
                vacancy.salary?.from,
                vacancy.salary?.to,
                currencySymbol
            )
            Glide.with(itemView)
                .load(vacancy.urlImage)
                .placeholder(R.drawable.vacancies_placeholder)
                .centerCrop()
                .transform(RoundedCorners(R.dimen.radius_vacancy_icon))
                .into(binding.ivIconVacancy)
        }
        itemView.setOnClickListener {
            onClickListener.onClick(vacancy)
        }
    }
}
