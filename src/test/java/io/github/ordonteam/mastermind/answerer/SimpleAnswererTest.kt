package io.github.ordonteam.mastermind.answerer

import io.github.ordonteam.mastermind.Config
import io.github.ordonteam.mastermind.Guess
import io.github.ordonteam.mastermind.MastermindGame
import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class SimpleAnswererTest {

    @Test
    fun shouldReturnSequenceSolved() {
        val game = newGameWithGuess(listOf(0, 1, 2, 3))
        val answerer = SimpleAnswerer(SequenceProvider { listOf(0, 1, 2, 3) })

        val answer = answerer.answer(game).state.last().answer!!

        assertThat(answer.correctColor).isEqualTo(0)
        assertThat(answer.correctPlace).isEqualTo(4)
    }

    @Test
    fun shouldReturnCorrectPlaceOnly() {
        val game = newGameWithGuess(listOf(0, 0, 0, 2))
        val answerer = SimpleAnswerer(SequenceProvider { listOf(1, 1, 1, 2) })

        val answer = answerer.answer(game).state.last().answer!!

        assertThat(answer.correctColor).isEqualTo(0)
        assertThat(answer.correctPlace).isEqualTo(1)
    }

    @Test
    fun shouldReturnCorrectColorOnly() {
        val game = newGameWithGuess(listOf(0, 0, 2, 0))
        val answerer = SimpleAnswerer(SequenceProvider { listOf(1, 1, 1, 2) })

        val answer = answerer.answer(game).state.last().answer!!

        assertThat(answer.correctColor).isEqualTo(1)
        assertThat(answer.correctPlace).isEqualTo(0)
    }

    private fun newGameWithGuess(guess: List<Int>): MastermindGame {
        return MastermindGame(
            config = Config(id = "id", colors = 6, slots = 4, allowDuplicates = true),
            state = listOf(Guess(colors = guess))
        )
    }
}
