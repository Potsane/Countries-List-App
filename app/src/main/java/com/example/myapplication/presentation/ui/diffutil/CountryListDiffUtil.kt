package com.example.myapplication.presentation.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.remote.model.Country

class CountryListDiffUtil(
    private val currentItems: List<Country>,
    private val updatedItems: List<Country>
) : DiffUtil.Callback() {

    override fun getOldListSize() = currentItems.size

    override fun getNewListSize() = updatedItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return currentItems[oldItemPosition].commonName == updatedItems[newItemPosition].commonName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return currentItems[oldItemPosition] == updatedItems[newItemPosition]
    }
}