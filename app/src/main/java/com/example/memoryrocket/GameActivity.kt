package com.example.memoryrocket


import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.memoryrocket.databinding.ActivityGameBinding
import java.util.*


class Card(val memoryImageView: ImageView?)  {

    private val hideImage: Int = R.drawable.memory_hide
    private lateinit var memoryImage: Drawable

    init {
       /*memoryImageView?.setOnClickListener {
            memoryImageView.setImageDrawable(hideImage.toDrawable())
            showImage()
        }*/
    }

    fun setMemoryImage(drawable : Drawable) {
        memoryImage = drawable
    }

    private fun showImage() {
        val transitionDrawable = TransitionDrawable(
            memoryImageView?.let {
                arrayOf(
                    it.drawable,
                    memoryImage
                )
            }
        )
        memoryImageView?.setImageDrawable(transitionDrawable)
        transitionDrawable.startTransition(500)
    }

    fun hideImage() {
        memoryImageView?.setImageResource(hideImage)


    }
}

class GameState() {

}

class MemoryGame(private val context: Context, private val binding: ActivityGameBinding)   {
    private val oListCardImages: MutableList<Int> = mutableListOf(R.drawable.memory_01, R.drawable.memory_02, R.drawable.memory_03,
                                                                  R.drawable.memory_01, R.drawable.memory_02, R.drawable.memory_03)

    private val oListCards: List<Card> = listOf(Card(binding.card01),Card(binding.card02),
                                                Card(binding.card03),Card(binding.card04),
                                                Card(binding.card05),Card(binding.card06))


    private fun setupGame() {
        val random = Random()

        for(card in oListCards) {
            val index = random.nextInt(oListCardImages.size - 0) + 0
            getDrawable(context, oListCardImages[index])?.let { card.setMemoryImage(it) }
            oListCardImages.removeAt(index)
        }
    }

    fun run() {
        setupGame()
        binding.timer.start()
        for (card in oListCards) {
            //if(card.memoryImageView?.id == v?.id) {
            //    Toast.makeText(context, "ESPAÑÁ", Toast.LENGTH_LONG).show()
           // }
        }
    }


}

class GameActivity : AppCompatActivity(), OnClickListener {
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memoryGame = MemoryGame(this,binding)
        memoryGame.run()
    }

    override fun onClick(v: View?) {
        Toast.makeText(this, "${v?.id}", Toast.LENGTH_LONG).show()
    }
}