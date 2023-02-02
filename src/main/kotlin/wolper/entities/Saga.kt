package wolper.entities

import java.time.LocalDateTime

data class Saga(
    val id: Long?,
    val player1: String?,
    var request: LocalDateTime?,
    val player2: String?,
    var created: LocalDateTime?,
    var puzzle1: Int,
    var puzzle2: Int,
    var started: LocalDateTime?,
    var nextstep: Int?,
    var move1: LocalDateTime?,
    var move2: LocalDateTime?,
    var winner: Int?,
    var canceled: Boolean = false,
)