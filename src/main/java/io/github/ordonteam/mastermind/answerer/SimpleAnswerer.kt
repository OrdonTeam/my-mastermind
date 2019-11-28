package io.github.ordonteam.mastermind.answerer

import io.github.ordonteam.mastermind.Answer
import io.github.ordonteam.mastermind.Answerer
import io.github.ordonteam.mastermind.Guess
import io.github.ordonteam.mastermind.MastermindGame
import java.util.function.Function
import kotlin.math.min

class SimpleAnswerer(
    private val sequenceProvider: SequenceProvider
) : Answerer {

    override fun answer(game: MastermindGame): MastermindGame {
        val sequence = sequenceProvider.apply(game)
        val answer = getStraitAnswer(sequence, game.state.last().colors)
        return setAnswer(game, answer)
    }

    private fun getStraitAnswer(sequence: List<Int>, guess: List<Int>): Answer {
        val matchedPlace = sequence.zip(guess).count { (sequenceColor, guessColor) ->
            sequenceColor == guessColor
        }
        val sequenceGrouped = sequence.groupBy { it }
        val guessGrouped = guess.groupBy { it }
        val matchedColors = sequenceGrouped.keys.sumBy {
            min(sequenceGrouped[it]?.size ?: 0, guessGrouped[it]?.size ?: 0)
        }
        return Answer(matchedColors - matchedPlace, matchedPlace)
    }

    private fun setAnswer(game: MastermindGame, answer: Answer): MastermindGame {
        return game.copy(state = setAnswer(game.state, answer))
    }

    private fun setAnswer(state: List<Guess>, answer: Answer): List<Guess> {
        return state.dropLast(1) + state.last().copy(answer = answer)
    }
}

typealias SequenceProvider = Function<MastermindGame, List<Int>>
