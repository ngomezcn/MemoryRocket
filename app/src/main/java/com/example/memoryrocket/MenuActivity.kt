package com.example.memoryrocket

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryrocket.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }


        val spinner = binding.btnDifficultySelector
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.phoneTypes, R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }
}