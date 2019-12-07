package io.github.ordonteam.mastermind.core

import io.github.ordonteam.mastermind.answerer.SequenceVault
import io.github.ordonteam.mastermind.answerer.SimpleAnswerer
import io.github.ordonteam.mastermind.guesser.SimpleGuesser
import org.junit.Test
import kotlin.random.Random

class DuelTest {

    @Test
    fun duelSimpleGuesserVsSimpleAnswerer() {
        val scores = Core().playManyGames(SimpleGuesser(), SimpleAnswerer(SequenceVault(Random.nextLong())))
        println("${scores.sumBy { it.state.size }} / ${scores.size}")
    }
}