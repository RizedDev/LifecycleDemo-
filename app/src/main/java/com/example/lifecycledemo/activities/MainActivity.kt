package com.example.lifecycledemo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycledemo.constants.COUNTER_KEY
import com.example.lifecycledemo.constants.MESSAGE_KEY
import com.example.lifecycledemo.constants.NUMBER_KEY
import com.example.lifecycledemo.constants.TAG
import com.example.lifecycledemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate вызван")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY)
        }
        Log.d(TAG, "значение востановлено")

        binding.counterTextView.text = counter.toString()

        initButton(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart вызван")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume вызван")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy вызван")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart вызван")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(COUNTER_KEY, counter)
        Log.d(TAG, "значение созранено")
    }

    private fun initButton(activity: Context) {
        binding.apply {
            goToSecondActivityButton.setOnClickListener {
                val intent = Intent(activity, SecondActivity::class.java)
                intent.putExtra(MESSAGE_KEY, "Привет из первого экрана!")
                intent.putExtra(NUMBER_KEY, 42)
                startActivity(intent)
            }

            upCount.setOnClickListener {
                counter++
                counterTextView.text = counter.toString()
            }

            downCount.setOnClickListener {
                counter--
                counterTextView.text = counter.toString()
            }
        }
    }
}