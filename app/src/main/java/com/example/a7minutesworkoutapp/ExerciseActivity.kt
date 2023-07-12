package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Long = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()

        }
        startTimer()
    }

//    private fun exerciseView(){
//        binding?.flProgressBar?.visibility = View.INVISIBLE
//        binding?.tvTitle?.text = "Arm Circles Exercise"
//        binding?.flExercise?.visibility = View.VISIBLE
//        exerciseTimer()
//    }

    private fun startTimer() {
        //start the timer
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = (10 - restProgress).toInt()
                binding?.tvTimer?.text = (10-restProgress).toString() //current timer value
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Let's start the exercise", Toast.LENGTH_SHORT).show()
                //exerciseView()
                binding?.flProgressBar?.visibility = View.INVISIBLE
                binding?.tvTitle?.text = "Arm Circles Exercise"
                binding?.flExercise?.visibility = View.VISIBLE
                exerciseTimer()
            }
        }.start()
    }

    private fun exerciseTimer() {
        //start the timer
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = (30 - exerciseProgress).toInt()
                binding?.tvExerciseTimer?.text = (30-exerciseProgress).toString() //current timer value
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "30 sec of exercise over🥳", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding=null
    }
}