package com.jeff.covidtracker.adapter

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blongho.country_data.Country
import com.jeff.covidtracker.R
import com.jeff.covidtracker.android.base.extension.substringWithDots
import com.jeff.covidtracker.database.local.SharedPreferencesMyCountry
import com.jeff.covidtracker.databinding.ItemSelectCountryBinding
import com.jeff.covidtracker.main.selector.SelectActivity
import timber.log.Timber
import java.util.*
import kotlin.Comparator


internal class SelectCountryAdapter(
    private val context: Context,
    private var dataList: List<Country>
) : RecyclerView.Adapter<SelectCountryAdapter.SelectCountryViewHolder>() {

    internal inner class SelectCountryViewHolder(binding: ItemSelectCountryBinding) :
        ViewHolder(binding.root) {
        var flag: ImageView = binding.flag
        var radioButtonItem: RadioButton = binding.itemRadioButton

        var menu: ConstraintLayout = binding.menu
        private val myCountryPrefs = SharedPreferencesMyCountry(context)
        val selected = myCountryPrefs.get()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SelectCountryViewHolder {
        val binding = DataBindingUtil.inflate<ItemSelectCountryBinding>(LayoutInflater.from(p0.context),
            R.layout.item_select_country,
            p0,
            false)
        sort()
        return SelectCountryViewHolder(binding)
    }

    override fun onBindViewHolder(holderSelect: SelectCountryViewHolder, position: Int) {
        val cases = dataList[position]
        holderSelect.radioButtonItem.text = String.substringWithDots(cases.name, 27)

        holderSelect.radioButtonItem.isChecked = holderSelect.selected.equals(cases.alpha2, true)

        holderSelect.radioButtonItem.setOnClickListener {
            Timber.d("==q ${cases.name}")

            val myCountryPrefs = SharedPreferencesMyCountry(context)
            myCountryPrefs.put(myCountryPrefs, cases.alpha2)

            val activity = context as SelectActivity
            val intent = Intent()
            intent.putExtra(SELECT_ISO2_KEY, cases.alpha2)
            intent.putExtra(SELECT_NAME_KEY, cases.name)
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }

    private fun sort() {
        Collections.sort(dataList, Comparator<Country> { obj1, obj2 ->
            // ## Ascending order
            obj1.name.compareTo(obj2.name) // To compare string values
            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
        })
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    fun update(countries: List<Country>) {
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

    companion object {

        const val SELECT_ISO2_KEY = "SELECT_ISO2_KEY"
        const val SELECT_NAME_KEY = "SELECT_NAME_KEY"

    }


}