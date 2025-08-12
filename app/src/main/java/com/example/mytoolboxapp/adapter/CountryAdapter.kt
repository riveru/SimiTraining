package com.example.mytoolboxapp.adapter

import com.example.mytoolboxapp.models.Country
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytoolboxapp.R

class CountryAdapter(private val countriesList: List<Country>) :

    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var onItemClickListener: ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //val cuntryView = LayoutInflater.from(parent.context)
          //  .inflate(R.layout.country_card_view, parent, false)
        val cuntryView = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_card_view, parent, false)
        return CountryViewHolder(cuntryView)
    }

    fun setOnItemClickListener(listener: (Country) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countriesList[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int = countriesList.size


    inner class CountryViewHolder(contryView: View) : RecyclerView.ViewHolder (contryView){

        /*
            private val txtCountryName = contryView.findViewById<TextView>(R.id.txtCountryName)
            private val txtOfficialName = contryView.findViewById<TextView>(R.id.txtOfficialName)
            private val imgFlag = contryView.findViewById<ImageView>(R.id.imgFlag)
        */

        private val txtCountryName : TextView = contryView.findViewById(R.id.txtCountryName)
        private val txtOfficialName : TextView = contryView.findViewById(R.id.txtOfficialName)
        private val imgFlag : ImageView = contryView.findViewById(R.id.imgFlag)


        fun bind(country: Country){
            txtCountryName.text = country.name
            txtOfficialName.text = country.officialName

            Glide.with(itemView.context)
                .load(country.imageURL)
                .into(imgFlag)

            itemView.setOnClickListener{
                onItemClickListener?.invoke(country)
            }

        }

    }

}