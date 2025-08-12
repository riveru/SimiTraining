package com.example.mytoolboxapp.countries

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.mytoolboxapp.R
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class CountryDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contry_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val countryName = intent.getStringExtra("country_name")
        val countryOfficialName = intent.getStringExtra("country_official_name")
        val countryCode = intent.getStringExtra("country_code")
        val countryImageURL = intent.getStringExtra("country_image_url")


        /**
         * Treamos los componentes de la vista
         */

        val txtCountryName : TextView = findViewById(R.id.countryName)
        val txtCountryOfficialName : TextView= findViewById(R.id.countryOfficialName)
        val imgCountryFlag : ImageView = findViewById(R.id.imgFlag)
        val webView : WebView = findViewById(R.id.webView)

        txtCountryName.text = countryName
        txtCountryOfficialName.text = countryOfficialName

        Glide.with(this)
            .load(countryImageURL)
            .into(imgCountryFlag)

//https://restcountries.com/v3.1/alpha/MX?fields=maps

        if (countryCode != null) {

            getContryByCode(countryCode, webView)
        }

    }

    private fun getContryByCode(code: String, webView: WebView) {

        val url = "https://restcountries.com/v3.1/alpha/$code?fields=maps"
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {

                if(response.isSuccessful){

                    response.body?.let { responseBody ->
                        val json = JSONObject(responseBody.string())
                        val maps = json.getJSONObject("maps")
                        val googleMapsURL = maps.getString("googleMaps")
                        Log.d("Maps", googleMapsURL)


                        runOnUiThread {
                            webView.webViewClient = WebViewClient()
                            webView.settings.javaScriptEnabled = true
                            webView.loadUrl(googleMapsURL)
                        }
                    }
                }
            }
        })
    }
}