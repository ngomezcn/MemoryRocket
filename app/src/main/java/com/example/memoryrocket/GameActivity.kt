package com.example.memoryrocket

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryrocket.databinding.ActivityGameBinding
import kotlinx.coroutines.*
import kotlin.system.*


class Card(private val imgView : ImageView?, private val imgDrawable: Int, val gameActivity: GameActivity)  {

    private val hideImg: Int = R.drawable.memory_hide

    init {
        imgView?.setOnClickListener {
            showImage()
        }

    }

    private fun showImage() {
        imgView?.setImageResource(imgDrawable)
    }

    private fun hideImage() {
        imgView?.setImageResource(hideImg)
    }

    fun toggleCard() {
        showImage()

        hideImg()
    }
}

class CardPair(val image : Int, val card1: Card, val card2: Card) {


}

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.timer.start()

        // Temporal per mostrar el fluix de treball
        //val cardImageList : List<Int> = listOf(R.drawable.memory_01, R.drawable.memory_02, R.drawable.memory_03)

        Card(binding.card01, R.drawable.memory_01)



    }


    fun run_async() {
        this@GameActivity.runOnUiThread {

        }
    }


}