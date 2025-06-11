package com.example.lifecycledemo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycledemo.databinding.ActivityMainBinding

const val TAG = "MainActivity"
const val MESSAGE = "MESSAGE"
const val NUMBER = "NUMBER"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate вызван")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToSecondActivityButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(MESSAGE, "Привет из первого экрана!")
            intent.putExtra(NUMBER, 42)
            startActivity(intent)
        }
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
}