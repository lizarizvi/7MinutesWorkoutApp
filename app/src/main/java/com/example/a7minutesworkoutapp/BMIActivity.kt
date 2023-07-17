package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.a7minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bmi)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMI)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding?.toolbarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnBMI?.setOnClickListener {
            if (validateMetricUnits()){
                val heightValue: Float = binding?.etHeight?.text.toString().toFloat() / 100
                val weightValue: Float = binding?.etWeight?.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true
        if(binding?.etWeight?.text.toString().isEmpty()){
            isValid = false
        }else if (binding?.etHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun  displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight!"
            bmiDescription = "Oops! You need to take care of yourself. EAT MORE!!"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight!"
            bmiDescription = "Oops! You need to take care of yourself and eat more."
        }else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight!"
            bmiDescription = "Oops! You need to take care of yourself and eat."
        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal!"
            bmiDescription = "Congratulations! You are in good shape. Exercise regularly to maintain your shape."
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight!"
            bmiDescription = "Oops! You need to take care of yourself and workout."
        }else if (bmi.compareTo(30f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Severely obese!"
            bmiDescription = "Oops! You need to take care of yourself and workout more."
        }else{
            bmiLabel = "Very severely obese!"
            bmiDescription = "Oops! You need to take care of yourself and workout more. ACT NOW!!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMI?.text = bmiValue
        binding?.tvOverUnderWeight?.text = bmiLabel
        binding?.tvDisplay?.text = bmiDescription
    }
}