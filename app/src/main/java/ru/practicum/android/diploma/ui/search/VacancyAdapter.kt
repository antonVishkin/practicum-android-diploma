package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(

    private val clickListener: VacancyClick
) : RecyclerView.Adapter<VacancyViewHolder>() {

    private var _items: List<Vacancy> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(_items[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(_items[position])

        }
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    fun interface VacancyClick {
        fun onClick(vacancy: Vacancy)
    }

    /* fun setVacancies(newVacancies: List<Vacancy>) {
         _items = newVacancies
         notifyDataSetChanged()
     }*/
}

