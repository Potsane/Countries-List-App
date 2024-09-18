package com.example.myapplication.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.Country
import com.example.myapplication.databinding.ItemCountryCardBinding
import com.example.myapplication.presentation.ui.listener.CountryCardClickListener
import com.example.myapplication.BR
import com.example.myapplication.presentation.ui.diffutil.CountryListDiffUtil

class CountryListAdapter(
    private val countries : MutableList<Country>,
    private val clickListener: CountryCardClickListener
)  : RecyclerView.Adapter<CountryListAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCountryCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_country_card,
            parent,
            false
        )
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Country = countries[position]
        holder.bind(item)
    }

    override fun getItemCount() = countries.size

    fun updateItems(updatedList: List<Country>) {
        val diffUtil = CountryListDiffUtil(countries, updatedList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        countries.clear()
        countries.addAll(updatedList)
        diffResults.dispatchUpdatesTo(this)
    }

    fun clearItems() = countries.clear()

    inner class ViewHolder(
        val view: View,
        private val binding: ItemCountryCardBinding
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Country) {
            binding.setVariable(BR.country, item)
            binding.setVariable(BR.clickListener, clickListener)
            binding.executePendingBindings()
        }
    }
}