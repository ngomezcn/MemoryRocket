package com.example.memoryrocket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memoryrocket.databinding.ActivityGameBinding
import com.example.memoryrocket.databinding.ActivityMenuBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Temporal per mostrar el fluix de treball
        binding.root.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }

        binding.timer.start()
    }
}