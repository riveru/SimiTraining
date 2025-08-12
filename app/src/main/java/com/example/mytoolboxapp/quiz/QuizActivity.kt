package com.example.mytoolboxapp.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.mytoolboxapp.Question
import com.example.mytoolboxapp.R


class QuizActivity : AppCompatActivity() {


    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var btnSubmit: Button
    private lateinit var tvScore: TextView

    private lateinit var animation_view: LottieAnimationView
    private lateinit var animation_view2: LottieAnimationView




    private val questions = listOf(
        Question("Cuál es la capital de francia", listOf("Paris","Roma","Berlin","Praga"),0),
        Question("Cuánto es 2+2", listOf("3","4","5","5"),1),
        Question("Cuál es el planeta mas cercano al sol", listOf("Tierra","Venus","Marte","Mercurio"),1)
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //val actionbar = supportActionBar

        //actionbar!!.setDisplayHomeAsUpEnabled(true)


        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvScore = findViewById(R.id.tvScore)
        animation_view = findViewById(R.id.animation_view)
        animation_view2 = findViewById(R.id.animation_view2)

        showQuestion()

        btnSubmit.setOnClickListener {

            val selectedOptionIndex = rgOptions.indexOfChild(findViewById(rgOptions.checkedRadioButtonId))
            if (selectedOptionIndex == questions[currentQuestionIndex].correctAnswer){
                score++
                Toast.makeText(getApplicationContext(), score.toString(), Toast.LENGTH_SHORT).show()
            }
            currentQuestionIndex++

            if (currentQuestionIndex < questions.size){
                showQuestion()
            }else {
                showScore()
            }
        }

    }
/*
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
*/
    private fun showScore() {
       tvQuestion.visibility = View.GONE
        rgOptions.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        tvScore.text = "Tu puntaje:" + score + "/"+questions.size
        Toast.makeText(getApplicationContext(), score.toString(), Toast.LENGTH_SHORT).show()
        tvScore.visibility = View.VISIBLE

        if(score==3){
            animation_view.visibility =  View.VISIBLE
        }else{
            animation_view2.visibility =  View.VISIBLE
        }

    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        tvQuestion.text = question.question
        rgOptions.clearCheck()
        for( i in 0 until  rgOptions.childCount){
            (rgOptions.getChildAt(i) as RadioButton).text = question.options[i]

        }
    }


}