package io.github.ordonteam.mastermind.answerer

import io.github.ordonteam.mastermind.*
import kotlin.math.min

class MaliciousAnswerer : Answerer {

    override fun answer(game: MastermindGame): MastermindGame {
        val possibleSequences = possibleSequences(game.config, game.state)
        val groupedByAnswer = possibleSequences.groupBy { getStraitAnswer(it, game.state.last().colors) }
        val mostPopularAnswer = groupedByAnswer.maxBy { it.value.size }!!.key
        return game.setAnswer(mostPopularAnswer)
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

    private fun possibleSequences(config: Config, guessesWithAnswers: List<Guess>): List<List<Int>> {
        return guessesWithAnswers.fold(allSequences(config)) { sequences, guess ->
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
        val commonColor = colorsGrouped.keys.sumBy {
            Integer.min(
                colorsGrouped[it]?.size ?: 0,
                sequenceGrouped[it]?.size ?: 0
            )
        }
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