package com.example.a7minutesworkoutapp

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkoutapp.databinding.ActivityExcerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Long = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress: Long = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var  tts : TextToSpeech? = null

    private var player:MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        tts = TextToSpeech(this,this)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        exerciseList = Constants.defaultExerciseList()
        startTimer()

        setupExerciseStatusRecyclerView()
    }

//    private fun exerciseView(){
//        binding?.flProgressBar?.visibility = View.INVISIBLE
//        binding?.tvTitle?.text = "Arm Circles Exercise"
//        binding?.flExercise?.visibility = View.VISIBLE
//        exerciseTimer()
//    }

    private fun startTimer() {
        //start the timer
        binding?.progressBar?.progress = restProgress.toInt()
        restTimer = object : CountDownTimer(3000, 1000) { //millisInFuture=10000
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = (10 - restProgress).toInt()
                binding?.tvTimer?.text = (10-restProgress).toString() //current timer value
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Let's exercise...", Toast.LENGTH_SHORT).show()
                //exerciseView()
                currentExercisePosition++
                binding?.flProgressBar?.visibility = View.INVISIBLE
                binding?.tvTitle?.visibility = View.INVISIBLE
                binding?.tvExercise?.visibility = View.VISIBLE
                binding?.flExercise?.visibility = View.VISIBLE
                binding?.gifImageView?.visibility = View.VISIBLE
                binding?.tvUpcomingExercise?.visibility = View.INVISIBLE
                binding?.upcomingExercise?.visibility = View.INVISIBLE
                if (exerciseTimer!=null){
                    exerciseTimer?.cancel()
                    exerciseProgress = 0
                }

                binding?.gifImageView?.setImageResource(exerciseList!![currentExercisePosition].getImage())
                binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getName()

                speakOut(exerciseList!![currentExercisePosition].getName())

                exerciseTimer()
            }
        }.start()
    }

    private fun exerciseTimer() {
        //start the timer
        binding?.exerciseProgressBar?.progress = exerciseProgress.toInt()
        exerciseTimer = object : CountDownTimer(3000, 1000) { //millisInFuture=30000
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = (30 - exerciseProgress).toInt()
                binding?.tvExerciseTimer?.text = (30-exerciseProgress).toString() //current timer value
            }

            override fun onFinish() {
                //Toast.makeText(this@ExerciseActivity, "30 sec of exercise over", Toast.LENGTH_SHORT).show()

                try {
                    val soundURI = Uri.parse("android.resource://com.example.a7minutesworkoutapp/"
                            + R.raw.music)
                    player = MediaPlayer.create(applicationContext,soundURI)
                    player?.isLooping = false
                    player?.start()
                }catch (e : Exception){
                    e.printStackTrace()
                }

                if(currentExercisePosition < exerciseList?.size!!-1){
                    binding?.flProgressBar?.visibility = View.VISIBLE
                    binding?.tvTitle?.visibility = View.VISIBLE
                    binding?.tvExercise?.visibility = View.INVISIBLE
                    binding?.flExercise?.visibility = View.INVISIBLE
                    binding?.gifImageView?.visibility = View.INVISIBLE
                    binding?.upcomingExercise?.visibility = View.VISIBLE
                    binding?.tvUpcomingExercise?.visibility  = View.VISIBLE
                    if (restTimer!=null){
                        restTimer?.cancel()
                        restProgress = 0
                    }
                    binding?.tvUpcomingExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

                    Toast.makeText(this@ExerciseActivity,"Relax...",Toast.LENGTH_SHORT).show()
                    speakOut("relax")

                    startTimer()
                }else{
                    Toast.makeText(this@ExerciseActivity, "Exercise over🥳", Toast.LENGTH_SHORT).show()
                    speakOut("Exercise over")
                }
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

        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }

        if(player!=null){
            player?.stop()
        }

        binding=null
    }

    private fun speakOut(text : String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)
        }
        if (status == TextToSpeech.LANG_MISSING_DATA || status == TextToSpeech.LANG_NOT_SUPPORTED){
            Log.e("TTS","Language not supported")
        }else{
            Log.e("TTS","Initialization failed")
        }
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }
}