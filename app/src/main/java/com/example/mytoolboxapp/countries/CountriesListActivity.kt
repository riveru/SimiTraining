package com.example.mytoolboxapp.countries

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytoolboxapp.R
import com.example.mytoolboxapp.adapter.CountryAdapter
import com.example.mytoolboxapp.models.Country
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException


class CountriesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_countries_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.paises_de_america)
        toolbar.setBackgroundColor(getColor(R.color.black))


        getCountriesFromAmerica()
    }


    private fun getCountriesFromAmerica(){
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url("https://restcountries.com/v3.1/region/Americas?fields=name,flags,cca2")
            .build()




        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = JSONArray(responseBody.string())
                    Log.d("Countries", json.toString())

                    val countriesList = mutableListOf<Country>()

                    for(i in 0 until json.length()){

                        val countryJson = json.getJSONObject(i)

                        val name = countryJson.getJSONObject("name").getString("common")
                        val officialName = countryJson.getJSONObject("name").getString("official")
                        val code = countryJson.getString("cca2")
                        val imageURL = countryJson.getJSONObject("flags").getString("png")
                        val country = Country(name, officialName, code, imageURL)

                        countriesList.add(country)
                    }

                    runOnUiThread{

                        val rvCountries = findViewById<RecyclerView>(R.id.rvCountries)

                        rvCountries.layoutManager = LinearLayoutManager(this@CountriesListActivity)

                        val adapter = CountryAdapter(countriesList)
                        rvCountries.adapter = adapter

                        adapter.setOnItemClickListener { country ->

                            val intent = Intent(this@CountriesListActivity, CountryDetailsActivity::class.java)
                            intent.putExtra("country_name", country.name)
                            intent.putExtra("country_official_name", country.officialName)
                            intent.putExtra("country_image_url", country.imageURL)
                            intent.putExtra("country_code", country.code)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}