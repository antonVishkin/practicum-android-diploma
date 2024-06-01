package ru.practicum.android.diploma.ui.filtration.industry

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryViewBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(private val onClickListener: (Industry) -> Unit) : RecyclerView.Adapter<IndustryViewHolder>() {
    var industries = mutableListOf<Industry>()
    var selectedIndustry: Industry? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IndustryViewBinding.inflate(layoutInflater, parent, false)
        return IndustryViewHolder(binding)
    }

    override fun getItemCount() = industries.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val clickListener = View.OnClickListener() {
            industries[position].isSelected = !industries[position].isSelected
            selectedIndustry = industries[position]
            industries.forEach {
                it.isSelected = it == industries[position]
                Log.d("Adapter","${it.name} ${it.isSelected}")
            }
            onClickListener(industries[position])
            notifyDataSetChanged()
        }
        if (selectedIndustry != null && industries[position].id == selectedIndustry!!.id) {
            industries[position].isSelected = true
        }

        holder.bind(industries[position])
        holder.checkBox.setOnClickListener(clickListener)
        holder.itemView.setOnClickListener(clickListener)
    }

    fun logIndustriesState() {
        industries.forEach {
            if (it.isSelected){
                Log.v("IndustrySelectedState","SELECTED INDUSTRY")
            }
            Log.d("IndustryState", "Industry: ${it.name}, isSelected: ${it.isSelected}")
        }
    }
}
