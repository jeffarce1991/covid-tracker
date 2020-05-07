package com.jeff.covidtracker.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryListAdapter.CountryListViewHolder
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.databinding.CountryListRowBinding
import com.jeff.covidtracker.main.detail.view.CountryDetailActivity
import com.jeff.covidtracker.main.list.view.MainActivity
import timber.log.Timber


internal class CountryListAdapter(
    private val context: Context,
    private val dataList: List<Country>
) : RecyclerView.Adapter<CountryListViewHolder>() {
    
    internal inner class CountryListViewHolder(binding: CountryListRowBinding) :
        ViewHolder(binding.root) {
        var txtTitle: TextView = binding.country
        var menu: LinearLayout = binding.menu
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CountryListViewHolder {
        val binding = DataBindingUtil.inflate<CountryListRowBinding>(LayoutInflater.from(p0.context),
            R.layout.country_list_row,
            p0,
            false)
        return CountryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.txtTitle.text = "${dataList[position].country}, ${dataList[position].iso2}"
        holder.menu.setOnClickListener {
            Timber.d("==q ${dataList[position].iso2}")

            val intent = Intent(context.applicationContext, CountryDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}