package com.example.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityMainBinding? =  null

    private var  tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this,this)

        //val flStartButton: FrameLayout = findViewById(R.id.flStart)
        binding?.flStart?.setOnClickListener{
            //Toast.makeText(this@MainActivity,"Let's start",Toast.LENGTH_SHORT).show()

            speakOut("Let's start the exercises")

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
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

}