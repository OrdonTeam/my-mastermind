package io.github.ordonteam.mastermind.guesser

import io.github.ordonteam.mastermind.*
import java.lang.Integer.min

class SimpleGuesser : Guesser {

    override fun guess(game: MastermindGame): MastermindGame {
        return game.copy(state = game.state + Guess(possibleSequences(game).random(), null))
    }

    fun possibleSequences(game: MastermindGame): List<List<Int>> {
        return game.state.fold(allSequences(game.config)) { sequences, guess ->
            sequences.filter { sequence -> isPossible(sequence, guess.colors, guess.answer) }
        }
    }

    private fun isPossible(sequence: List<Int>, colors: List<Int>, answer: Answer?): Boolean {
        if (answer == null) {
            return true
        }
        val commonPlace = sequence.zip(colors).count { it.first == it.second }
        if (commonPlace != answer.correctPlace) {
            return false
        }
        val colorsGrouped = colors.groupBy { it }
        val sequenceGrouped = sequence.groupBy { it }
        val commonColor = colorsGrouped.keys.sumBy { min(colorsGrouped[it]?.size ?: 0, sequenceGrouped[it]?.size ?: 0) }
        return commonColor >= answer.correctColor + answer.correctPlace
    }

    private fun allSequences(config: Config): List<List<Int>> {
        return extendPrefixes(listOf(emptyList()), config.colors, config.slots)
    }

    private fun extendPrefixes(prefixes: List<List<Int>>, colors: Int, slots: Int): List<List<Int>> {
        return if (prefixes.first().size == slots) {
            prefixes
        } else {
            val extendedPrefixes = prefixes.flatMap { prefix ->
                (0 until colors).map { color -> prefix + color }
            }
            extendPrefixes(extendedPrefixes, colors, slots)
        }
    }
}