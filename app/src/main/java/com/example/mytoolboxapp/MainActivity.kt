package com.example.mytoolboxapp
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    companion object{
        var currentUserName: String? = null
        var currentUserLastName: String? = null
        var currentUser: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        //setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", null)
        if(!username.isNullOrEmpty()){
            currentUser = username
            currentUserName = prefs.getString("currentUserName", null)
            currentUserLastName = prefs.getString("currentUserLastName", null)

            // Si ya hay un usuario logueado, navegar a la pantalla de inicio
            navigateToHome()
        }

        val btnIniciar = findViewById<AppCompatButton>(R.id.btnOne)
        val etUser = findViewById<AppCompatEditText>(R.id.etUser)
        val etPassword = findViewById<AppCompatEditText>(R.id.etPassword)



        btnIniciar.setOnClickListener {

            if(etUser.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()){
                Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            iniciarSesion(etUser.text.toString().trim(), etPassword.text.toString().trim())
            //testConection()
        }


    }
/*
    private fun testConection(){


        db = FirebaseFirestore.getInstance()
        db.collection("test").add(mapOf("timestamp" to System.currentTimeMillis()))

    }
*/

    private fun iniciarSesion(usuario: String, password: String){

        //navigateToHome()

        db = FirebaseFirestore.getInstance()



        db.collection("users")
            .whereEqualTo("username", usuario)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }else{

                    currentUserName = documents.documents[0].getString("name")
                    currentUserLastName = documents.documents[0].getString("last_name")
                    currentUser = usuario

                    val pref = getSharedPreferences("user_session", MODE_PRIVATE)
                    val editor = pref.edit()
                        .putString("username", usuario)
                        .putString("currentUserName", currentUserName)
                        .putString("currentUserLastName", currentUserLastName)
                    Log.d("MiApp", "El valor de la variable es: " + currentUserName)

                    editor.apply()


                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    navigateToHome()

                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)

            }

    }

    private fun navigateToHome(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}