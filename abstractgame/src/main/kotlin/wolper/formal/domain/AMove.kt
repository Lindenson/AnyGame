package wolper.formal.domain

import kotlinx.serialization.Serializable


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player


// for bulls and caws
// moveSize = 1
// S : Int - just guess for a number

// for TicTacToe
// moveSize = 1
// S : Int - number of cell

// for SeaFight
// moveSize = 1
// S : Int - number of cell

@Serializable
abstract class AMove<M> {
    abstract val moveSize: Int
    abstract val move: List<M>
}