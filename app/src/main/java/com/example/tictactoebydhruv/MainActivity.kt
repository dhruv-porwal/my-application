package com.example.tictactoebydhruv

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.tictactoebydhruv.R.layout.activity_main


class MainActivity : AppCompatActivity() {
    lateinit var buttons: Array<Array<ImageButton>>
    lateinit var textViewPlayer1: TextView
    lateinit var textViewPlayer2: TextView

    private var player1turn: Boolean = true
    private var roundcount: Int = 0
    private var player1Points: Int = 0
    private var player2Points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        textViewPlayer1 = findViewById(R.id.player1TextView)
        textViewPlayer2 = findViewById(R.id.player2TextView)

        buttons = Array(size=3) { r ->
            Array(size=3) { c ->
                initButtons(r, c)
            }
        }

        val btnReset: Button =
            findViewById(R.id.btnReset)

        btnReset.setOnClickListener {
          player1Points=0
            player2Points=0
            updateScore()
            clearBoard()
        }


    }

    private fun initButtons(r: Int, c: Int): ImageButton {

        val btn: ImageButton = findViewById(resources.getIdentifier("btn$r$c", "id", packageName))
        btn.setOnClickListener {
            onBtnClick(btn)
        }

        return btn
    }

    private fun onBtnClick(btn: ImageButton) {
        if (btn.drawable != null) return

        if (player1turn) {
            btn.setImageResource(R.drawable.cross)
        } else {
            btn.setImageResource(R.drawable.circle)
        }
        roundcount++

        if (checkForWin()) {
            if (player1turn) win(1) else win(2)

        } else if (roundcount == 9) {
            draw()

        } else {
            player1turn = !player1turn
        }
    }

    private fun checkForWin(): Boolean {
        val fields = Array(3) { r ->
            Array(3) { c ->
                getField(buttons[r][c])
            }
        }
        for (i in 0..2) {
            if (
                (fields[i][0] == fields[i][1]) &&
                (fields[i][0] == fields[i][2]) &&
                (fields[i][0] != null)


            ) return true
        }
        for (i in 0..2) {
            if (
                (fields[0][i] == fields[1][i]) &&
                (fields[0][i] == fields[2][i]) &&
                (fields[0][i] != null)


            ) return true

        }
        if (
            (fields[0][2] == fields[1][1]) &&
            (fields[0][2] == fields[2][0]) &&
            (fields[0][2] != null)


        ) return true

        if (
            (fields[0][0] == fields[1][1]) &&
            (fields[0][0] == fields[2][2]) &&
            (fields[0][0] != null)


        ) return true

        return false

    }

    private fun getField(btn: ImageButton):Char? {
        val drw: Drawable?=btn.drawable
        val drwCross:Drawable?=ResourcesCompat.getDrawable(resources, R.drawable.cross,null)
        val drwCircle:Drawable?=ResourcesCompat.getDrawable(resources, R.drawable.circle,null)


     return when(drw?.constantState){
         drwCross?.constantState->'x'
         drwCircle?.constantState->'o'
         else->null
     }
    }

    private fun win(player: Int) {
        if(player==1) player1Points++ else if(player==2) player2Points++
        Toast.makeText(applicationContext, "Player $player Won!", Toast.LENGTH_SHORT).show()


        updateScore()
        clearBoard()

    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setImageResource(0)
            }
        }
        roundcount =0
        player1turn=true

    }
    private fun updateScore(){
        textViewPlayer1.text="Player 1:$player1Points"

        textViewPlayer2.text="Player 2:$player2Points"
    }

    private fun draw() {
        Toast.makeText(applicationContext, "Match Draw!", Toast.LENGTH_SHORT).show()
        updateScore()
        clearBoard()
    }
}
