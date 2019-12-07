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
        return game.setAnswer(answer)
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
}

typealias SequenceProvider = Function<MastermindGame, List<Int>>
