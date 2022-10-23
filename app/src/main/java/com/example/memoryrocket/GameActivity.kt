package com.example.memoryrocket


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener
import android.widget.Chronometer
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryrocket.databinding.ActivityGameBinding
import java.util.*


class Card(val memoryImageView: ImageView?, private val context: MemoryGame)  {

    private val hideImage: Int = R.drawable.memory_hide
    private var memoryImage: Int = -1
    var partnerFound = false

    init {
        memoryImageView?.setOnClickListener(context)
    }

    fun setMemoryImage(drawable : Int) {
        memoryImage = drawable
    }

    fun showImage() {
       /* val transitionDrawable = TransitionDrawable(
            memoryImageView?.let {
                arrayOf(
                    it.drawable,
                    memoryImage
                )
            }
        )
        memoryImageView?.setImageDrawable(transitionDrawable)
        transitionDrawable.startTransition(500)*/
        memoryImageView?.setImageResource(memoryImage)
    }

    fun hideImage() {
        if(!partnerFound) {
            memoryImageView?.setImageResource(hideImage)
        }
    }
}

class MemoryGame(private val context: GameActivity, private val binding: ActivityGameBinding) : OnClickListener {
    private val oListCardImages: MutableList<Int> = mutableListOf(R.drawable.memory_01, R.drawable.memory_02, R.drawable.memory_03,
                                                                  R.drawable.memory_01, R.drawable.memory_02, R.drawable.memory_03)

    private val oListCards: List<Card> = listOf(Card(binding.card01, this), Card(binding.card02, this),
                                                Card(binding.card03, this), Card(binding.card04, this),
                                                Card(binding.card05, this), Card(binding.card06, this))

    private lateinit var timer: Chronometer
    private lateinit var pauseBtn: ImageView

    private fun pauseGame() {
        pauseBtn.setImageResource(R.drawable.ic_play)
        val timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
        timer.stop();

        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Juego pausado")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Continuar",
            DialogInterface.OnClickListener {
                dialog, which -> dialog.dismiss()
                pauseBtn.setImageResource(R.drawable.ic_pause)
                timer.base = SystemClock.elapsedRealtime() + timeWhenStopped;
                timer.start();
            })
        alertDialog.show()
    }

    private fun setupGame() {

        timer = binding.timer
        pauseBtn = binding.btnPause!!
        pauseBtn.setOnClickListener {
            pauseGame()
        }

        // Asignamos de manera aleatoria los pares de imagenes a las cartas
        // Todo: Se puede automatizar para no tener que dupear los items en la lista de imagenes
        val random = Random()
        for(card in oListCards) {
            val index = random.nextInt(oListCardImages.size - 0) + 0
            card.setMemoryImage(oListCardImages[index]);
            oListCardImages.removeAt(index)
        }
    }

    fun run() {
        setupGame()
        timer.start()
    }

    private val baseScore = 100
    private fun calculateScore() : Int {

        val timeScore : Int = ((((SystemClock.elapsedRealtime() - timer.base)/1000)*200)/100).toInt()
        val movementScore = (movements*100)/100
        val bruteScore = timeScore-movementScore

        var score : Int = baseScore - bruteScore;
        if(score < 0 ) {
            score = 0
        }
        if(score > baseScore ) {
            score = baseScore
        }

        return score
    }

    private fun finishGame() {
        timer.stop()

        val score = calculateScore()
        val intent = Intent(context, ScoreActivity::class.java)
        intent.putExtra("score", score)
        context.startActivity(intent)
    }

    var movements : Int = 0
    var clearOnNext : Boolean = false
    private val currentCardsNotHidden : MutableList<Card> = mutableListOf()

    private fun gameLogic(v: View?) {
        // TODO: Hacer una clase llamada PairCard o algo parecido para gestionar de una manera mas limpiea donde se usa la lista de currentCardsNotHidden
        for (card in oListCards) {
            if(card.memoryImageView!!.id == v!!.id) {
                if(clearOnNext) {
                    currentCardsNotHidden[0].hideImage()
                    currentCardsNotHidden[0].memoryImageView!!.setOnClickListener(this)

                    currentCardsNotHidden[1].hideImage()
                    currentCardsNotHidden[1].memoryImageView!!.setOnClickListener(this)

                    currentCardsNotHidden.clear()

                    clearOnNext = false
                }

                card.showImage()
                card.memoryImageView!!.setOnClickListener(null)
                currentCardsNotHidden.add(card)

                if(currentCardsNotHidden.size == 2) {

                    if(currentCardsNotHidden[0].memoryImageView!!.drawable.constantState == currentCardsNotHidden[1].memoryImageView!!.drawable.constantState) {
                        currentCardsNotHidden[0].partnerFound = true
                        currentCardsNotHidden[1].partnerFound = true
                    }
                    clearOnNext = true
                }

                movements += 1
                binding.movements!!.text = movements.toString()

                // Si hay algun par de cartas por encontrar continuamos el juego
                for (cardToCheck in oListCards) {
                    if(!cardToCheck.partnerFound) {
                        return
                    }
                }
                finishGame()
            }
        }
    }

    override fun onClick(v: View?) {
        gameLogic(v)
    }
}

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memoryGame = MemoryGame(this, binding)
        memoryGame.run()
    }
}