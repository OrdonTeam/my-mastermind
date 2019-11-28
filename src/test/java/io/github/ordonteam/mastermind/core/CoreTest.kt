package io.github.ordonteam.mastermind.core

import io.github.ordonteam.mastermind.answerer.SequenceVault
import io.github.ordonteam.mastermind.answerer.SimpleAnswerer
import io.github.ordonteam.mastermind.guesser.SimpleGuesser
import org.junit.Test
import kotlin.random.Random

class CoreTest {
    @Test
    fun shouldPlayGame() {
        val game = Core().playGame(SimpleGuesser(), SimpleAnswerer(SequenceVault(Random.nextLong())))
        game.state.forEach {
            println("${it.colors} ${it.answer}")
        }
    }
}