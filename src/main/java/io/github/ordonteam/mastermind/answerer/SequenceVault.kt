package io.github.ordonteam.mastermind.answerer

import io.github.ordonteam.mastermind.Config
import io.github.ordonteam.mastermind.MastermindGame
import kotlin.random.Random

class SequenceVault(private val seed: Long) : SequenceProvider {

    override fun apply(game: MastermindGame): List<Int> {
        return if (game.config.allowDuplicates) {
            sequenceWithDuplicates(game.config)
        } else {
            sequenceWithoutDuplicates(game.config)
        }
    }

    private fun sequenceWithDuplicates(config: Config): List<Int> {
        val random = Random(seed + config.id.hashCode())
        return (0 until config.slots).map {
            random.nextInt(config.colors)
        }
    }

    private fun sequenceWithoutDuplicates(config: Config): List<Int> {
        val random = Random(seed + config.id.hashCode())
        return (0 until config.colors)
            .shuffled(random)
            .take(config.slots)
    }
}