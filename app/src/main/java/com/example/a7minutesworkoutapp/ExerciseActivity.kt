package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExcerciseBinding? = null

    private var countDownTimer: CountDownTimer? = null
    private var timeDuration: Long = 20000 //in ms
    private var pauseOffset: Long = 0 //pauseOffset=timeDuration-timeLeft

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        binding?.tvTimer?.text = "${(timeDuration / 1000).toString()}"

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()

        }
        binding?.tvTimer?.setOnClickListener {
            startTimer(pauseOffset)
        }
    }

    private fun startTimer(pauseOffsetL: Long) {
        //start the timer for 60 sec
        countDownTimer = object : CountDownTimer(timeDuration - pauseOffsetL, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timeDuration - millisUntilFinished
                binding?.tvTimer?.text = (millisUntilFinished / 1000).toString() //current timer value
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "timer finished!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}