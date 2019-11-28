package io.github.ordonteam.mastermind.core

import io.github.ordonteam.mastermind.Answerer
import io.github.ordonteam.mastermind.Config
import io.github.ordonteam.mastermind.Guesser
import io.github.ordonteam.mastermind.MastermindGame
import java.util.*

class Core {
    fun playGame(guesser: Guesser, answerer: Answerer): MastermindGame {
        var game = MastermindGame(
            config = Config(
                id = UUID.randomUUID().toString(),
                colors = 6,
                slots = 4,
                allowDuplicates = true
            )
        )
        while (game.state.lastOrNull()?.answer?.correctPlace != game.config.slots) {
            game = answerer.answer(guesser.guess(game))
        }
        return game
    }
}