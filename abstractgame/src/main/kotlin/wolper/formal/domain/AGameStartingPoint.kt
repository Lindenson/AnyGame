package wolper.formal.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

// for bulls and caws
// pointSize = 2
// S : Int
// initially [3574, 2145] where numbers are my and his puzzles

// for TicTacToe
// pointSize = 9
// S : enum of NO, CROSS, ZERO
// initially [NO, NO, NO, NO, NO, NO, NO, NO, NO]

// for SeaFight
// pointSize = 100
// S : enum of NO, SHIP1, SHIP2 where 1 and 2 are Player's number
// initially [NO, SHIP1, NO, SHIP2, .... NO]

@Serializable
abstract class AGameStartingPoint<S> {
    abstract var pointSize: Int
    @Polymorphic abstract var startingPoint: List<S>
}