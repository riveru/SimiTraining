package com.example.mytoolboxapp

import okio.Options

data class Question(

    val question: String, //Pregunta
    val options: List<String>,//4 opciones
    val correctAnswer: Int, //0..3

)
