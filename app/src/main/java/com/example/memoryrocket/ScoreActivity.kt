package com.example.memoryrocket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryrocket.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val score = extras.getInt("score")

            binding.btnShare!!.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "He aconseguit $score punts en MemoryRocket!")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            binding.scoreView!!.text = score.toString()
        }

        binding.btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }







    }
}