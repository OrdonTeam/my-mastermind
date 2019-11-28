package io.github.ordonteam.mastermind.guesser

import io.github.ordonteam.mastermind.Answer
import io.github.ordonteam.mastermind.Config
import io.github.ordonteam.mastermind.Guess
import io.github.ordonteam.mastermind.MastermindGame
import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class SimpleGuesserTest {

    @Test
    fun shouldReturnAllSequences() {
        val possibleSequences = SimpleGuesser().possibleSequences(newGame())
        assertThat(possibleSequences).containsExactly(listOf(0, 0), listOf(0, 1), listOf(1, 0), listOf(1, 1))
    }

    @Test
    fun shouldRestrictByPosition() {
        val possibleSequences = SimpleGuesser().possibleSequences(newGame(Guess(listOf(0, 0), Answer(0, 1))))
        assertThat(possibleSequences).doesNotContain(listOf(1, 1))
    }

    @Test
    fun shouldRestrictByColor() {
        val possibleSequences = SimpleGuesser().possibleSequences(newGame(Guess(listOf(0, 1), Answer(2, 0))))
        assertThat(possibleSequences).doesNotContain(listOf(0, 0), listOf(1, 1))
    }

    @Test
    fun shouldRemovePreviousGuess() {
        val possibleSequences = SimpleGuesser().possibleSequences(newGame(Guess(listOf(0, 1), Answer(2, 0))))
        assertThat(possibleSequences).doesNotContain(listOf(0, 1))
    }

    @Test
    fun shouldReturnOnlyPossibleGuess() {
        val game = SimpleGuesser().guess(newGame(Guess(listOf(0, 1), Answer(2, 0))))
        assertThat(game.state.last().colors).isEqualTo(listOf(1, 0))
    }

    private fun newGame(vararg guesses: Guess): MastermindGame {
        return MastermindGame(
            Config(id = "id", colors = 2, slots = 2, allowDuplicates = true),
            guesses.toList()
        )
    }
}