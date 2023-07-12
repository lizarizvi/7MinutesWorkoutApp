package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Long = 0

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
            }
        }.start()
    }



    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding=null
    }
}