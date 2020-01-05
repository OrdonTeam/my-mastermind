package io.github.ordonteam.mastermind.guesser

import io.github.ordonteam.mastermind.Guess
import io.github.ordonteam.mastermind.Guesser
import io.github.ordonteam.mastermind.MastermindGame
import io.github.ordonteam.mastermind.answerer.MaliciousAnswerer
import io.github.ordonteam.mastermind.core.Core

class InteractiveGuesses : Guesser {
    override fun guess(game: MastermindGame): MastermindGame {
        game.state.lastOrNull()?.answer?.let {
            println(it)
        }
        val readLine = readLine()!!
        val split = readLine.split(" ").map { it.toInt() }
        return game.setGuess(Guess(colors = split))
    }
}

fun main() {
    Core().playGame(InteractiveGuesses(), MaliciousAnswerer())
}