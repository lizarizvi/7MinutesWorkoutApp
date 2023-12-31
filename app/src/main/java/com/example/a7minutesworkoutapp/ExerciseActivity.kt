package com.example.a7minutesworkoutapp

import android.app.Dialog
import android.content.Intent
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
import com.example.a7minutesworkoutapp.databinding.DialogCustomBackConfirmationBinding
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

    private var startTimerDuration: Long = 1
    private var exerciseTimerDuration: Long = 1

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
            customDialogForBackButton()
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
        restTimer = object : CountDownTimer(startTimerDuration*3000, 1000)
        { //millisInFuture=10000
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = (10 - restProgress).toInt()
                binding?.tvTimer?.text = (10-restProgress).toString() //current timer value
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Let's exercise...", Toast.LENGTH_SHORT).show()
                //exerciseView()
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

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
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*3000, 1000)
        { //millisInFuture=30000
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = (30 - exerciseProgress).toInt()
                binding?.tvExerciseTimer?.text = (30-exerciseProgress).toString() //current timer value

                try {
                    val soundURI = Uri.parse("android.resource://com.example.a7minutesworkoutapp/"
                            + R.raw.music)
                    player = MediaPlayer.create(applicationContext,soundURI)
                    player?.isLooping = false
                    player?.start()
                }catch (e : Exception){
                    e.printStackTrace()
                }

            }

            override fun onFinish() {
                //Toast.makeText(this@ExerciseActivity, "30 sec of exercise over", Toast.LENGTH_SHORT).show()

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter?.notifyDataSetChanged()

//                try {
//                    val soundURI = Uri.parse("android.resource://com.example.a7minutesworkoutapp/"
//                            + R.raw.music)
//                    player = MediaPlayer.create(applicationContext,soundURI)
//                    player?.isLooping = false
//                    player?.start()
//                }catch (e : Exception){
//                    e.printStackTrace()
//                }

                if(currentExercisePosition < exerciseList?.size!!-1){

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter?.notifyDataSetChanged()

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
                    Toast.makeText(this@ExerciseActivity, "Exercise over", Toast.LENGTH_SHORT).show()
                    speakOut("Congratulations. You have completed the workout!")

                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
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

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }
}