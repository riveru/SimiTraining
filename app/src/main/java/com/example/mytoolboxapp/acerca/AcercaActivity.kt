package com.example.mytoolboxapp.acerca

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mytoolboxapp.R
import com.example.mytoolboxapp.R.*
import java.io.File

class AcercaActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_acerca)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView: ImageView = findViewById(R.id.imgGif)

        // Suponiendo que tienes la ruta del archivo
        val imagePath = "@drawable/doctorsimigif"
        val imageFile = File(imagePath)
        Log.e(imagePath, "IMAGE Path")
        if (imageFile.exists()) {
            val imageUri = Uri.fromFile(imageFile)
            loadImage(imageUri, imageView)


        } else {
            // Manejar el caso en que el archivo no existe
        }
    }

    private fun loadImage(imageUri: Uri, imageView: ImageView) {
        Glide.with(this)
            .load(imageUri)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(imageView)
    }

}