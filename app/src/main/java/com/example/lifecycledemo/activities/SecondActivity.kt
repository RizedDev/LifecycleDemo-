package com.example.lifecycledemo.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycledemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra(MESSAGE)
        val number = intent.getIntExtra(NUMBER, 0)

        binding.receivedMessageTextView.text = "$message, $number"

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}