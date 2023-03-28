package wolper.formal.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

// for bulls and caws
// dispositionSize = 2
// S : Int
// current [2345, 1234] // my and his guess

// for TicTacToe
// dispositionSize = 9
// S : enum of NO, CROSS, ZERO
// current [NO, NO, CROSS, NO, NO, NO, ZERO, NO, NO]

// for SeaFight
// dispositionSize = 100
// S : enum of NO, SHOT_BY_1, SHOT_BY_2, HIT, KILLED
// current [NO, NO, SHOT_BY_1, NO, HIT, ... NO]

@Serializable
abstract class AGameDisposition<D> {
    abstract var dispositionSize: Int
    @Polymorphic abstract var disposition: List<D>
}
