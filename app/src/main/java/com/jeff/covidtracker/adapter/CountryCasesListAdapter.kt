package com.jeff.covidtracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blongho.country_data.World
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.covidtracker.R
import com.jeff.covidtracker.android.base.extension.substringWithDots
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.databinding.ItemCountryListBinding
import com.jeff.covidtracker.main.detail.view.CountryDetailActivity
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.*


internal class CountryCasesListAdapter(
    private val context: Context,
    private var dataList: List<Cases>
) : RecyclerView.Adapter<CountryCasesListAdapter.CountryCasesListViewHolder>() {


    internal inner class CountryCasesListViewHolder(binding: ItemCountryListBinding) :
        ViewHolder(binding.root) {
        var txtTitle: TextView = binding.country
        var confirmedCases: TextView = binding.confirmedCases
        var confirmedCasesIcon: TextView = binding.confirmedCasesIcon
        var menu: ConstraintLayout = binding.menu
        var flag: ImageView = binding.flag
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CountryCasesListViewHolder {
        val binding = DataBindingUtil.inflate<ItemCountryListBinding>(LayoutInflater.from(p0.context),
            R.layout.item_country_list,
            p0,
            false)
        sort()
        return CountryCasesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryCasesListViewHolder, position: Int) {
        val cases = dataList[position]
        holder.confirmedCasesIcon.text = String.format("\uD83D\uDCBC")
        holder.confirmedCases.text = String.format("%,d", cases.totalCases!!.totalConfirmed)
        holder.txtTitle.text = String.substringWithDots(cases.country, 27)
        World.init(context.applicationContext)
        val flag = World.getFlagOf(dataList[position].countryCode)
        holder.flag.setImageResource(flag)
        holder.menu.setOnClickListener {
                Timber.d("==q ${dataList[position].country}")
                val selectedCountry = dataList[position]
                val intent = CountryDetailActivity.getStartIntent(context,
                    selectedCountry.country,
                    selectedCountry.countryCode,
                    "")
                context.startActivity(intent)
        }
    }

    fun sort() {
        Collections.sort(dataList, Comparator<Cases> { obj1, obj2 ->
            // ## Ascending order
            obj2.totalCases!!.totalConfirmed.toInt()
                .compareTo(obj1.totalCases!!.totalConfirmed.toInt()) // To compare string values
            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
        })
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    fun update(countries: List<Cases>) {
        this.dataList = countries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    
    fun clear() {
        this.dataList = emptyList()
        notifyDataSetChanged()
    }


}