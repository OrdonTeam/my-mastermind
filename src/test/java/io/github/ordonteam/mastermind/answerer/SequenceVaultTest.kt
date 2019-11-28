package io.github.ordonteam.mastermind.answerer

import io.github.ordonteam.mastermind.Config
import io.github.ordonteam.mastermind.MastermindGame
import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import kotlin.random.Random

class SequenceVaultTest {

    @Test
    fun shouldReturnRandomSequenceWithDuplicates() {
        val vault = SequenceVault(0L)
        val config = Config(id = "id", colors = 6, slots = 4, allowDuplicates = true)
        assertThat(vault.apply(newGame(config))).isEqualTo(listOf(1, 0, 0, 2))
    }

    @Test
    fun shouldReturnConsistentSequencesWithDuplicates() {
        val vault = SequenceVault(Random.nextLong())
        val config = Config(id = UUID.randomUUID().toString(), colors = 10000, slots = 10000, allowDuplicates = true)
        assertThat(vault.apply(newGame(config))).isEqualTo(vault.apply(newGame(config)))
    }

    @Test
    fun shouldReturnConsistentSequencesWithoutDuplicates() {
        val vault = SequenceVault(Random.nextLong())
        val config = Config(id = UUID.randomUUID().toString(), colors = 10000, slots = 10000, allowDuplicates = false)
        assertThat(vault.apply(newGame(config))).isEqualTo(vault.apply(newGame(config)))
    }

    @Test
    fun shouldHonorDuplicateRestriction() {
        val vault = SequenceVault(Random.nextLong())
        val config = Config(id = UUID.randomUUID().toString(), colors = 10000, slots = 10000, allowDuplicates = false)
        assertThat(vault.apply(newGame(config))).doesNotHaveDuplicates()
    }

    private fun newGame(config: Config): MastermindGame {
        return MastermindGame(config, emptyList())
    }
}
