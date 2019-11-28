package io.github.ordonteam.mastermind

data class MastermindGame(
    val config: Config,
    val state: List<Guess> = emptyList()
)

data class Config(
    val id: String,
    val colors: Int,
    val slots: Int,
    val allowDuplicates: Boolean
)

data class Guess(
    val colors: List<Int>,
    val answer: Answer? = null
)

data class Answer(
    val correctColor: Int,
    val correctPlace: Int
)

interface Guesser {
    fun guess(game: MastermindGame): MastermindGame
}

interface Answerer {
    fun answer(game: MastermindGame): MastermindGame
}