package com.example.lifecycledemo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycledemo.constants.MESSAGE_KEY
import com.example.lifecycledemo.constants.NUMBER_KEY
import com.example.lifecycledemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra(MESSAGE_KEY)
        val number = intent.getIntExtra(NUMBER_KEY, 0)

        binding.receivedMessageTextView.text = "$message, $number"

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}