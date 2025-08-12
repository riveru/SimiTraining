package com.example.mytoolboxapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mytoolboxapp.R
import com.example.mytoolboxapp.about.AcercaDeFragment
import com.example.mytoolboxapp.about.NosotrosFragment
import com.example.mytoolboxapp.about.TiendaFragment


class BasseActivity : AppCompatActivity(){

        private lateinit var bottomNavigationView: BottomNavigationView
        private lateinit var drawerLayout: DrawerLayout

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_base)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Configurar toolbar con estilo más moderno
            setupToolbar()

            bottomNavigationView = findViewById(R.id.bottomNavigationView)


            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_acerca_de -> {
                        loadFragment(AcercaDeFragment())

                        true
                    }
                    R.id.nav_nosotros -> {
                        loadFragment(NosotrosFragment())
                        true
                    }
                    R.id.nav_tienda -> {
                        loadFragment(TiendaFragment())
                        true
                    }
                    else -> false
                }
            }

            // Cargar fragment por defecto al iniciar
            loadFragment(AcercaDeFragment())
            bottomNavigationView.selectedItemId = R.id.nav_acerca_de


        }

        private fun setupToolbar() {
            findViewById<Toolbar>(R.id.toolbar).apply {
                setSupportActionBar(this)
                // Aquí puedes agregar más configuraciones del toolbar si necesitas
                // title = "Mi App"
                // subtitle = "Subtítulo"
                setBackgroundColor(resources.getColor(android.R.color.white, theme))
                supportActionBar?.title = "Fragments"
                supportActionBar?.subtitle = "Ejemplo del uso de fragments"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

            }
        }

        private fun loadFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
        }

        override fun onSupportNavigateUp(): Boolean {
            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()

            return true
        }
    }
