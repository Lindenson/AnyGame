package wolper.formal.service

import wolper.formal.domain.AGameState
import wolper.formal.domain.AGameSaga
import wolper.formal.domain.AMove


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

//will be a Delegate for AGameState
abstract class AGameRules<S, D, M, T, P>(state : AGameState<S, D, T>) {
    abstract fun validateStartingPointForPlayer(player: P) // throws
    abstract fun validateMove(player : P, move : AMove<M>) // throws
    abstract fun getEvaluation() : T
    abstract fun checkPlayersTimeOut(saga : AGameSaga, player : P) : Boolean
    abstract fun checkGameTimeOut(saga : AGameSaga) : Boolean
    abstract val numbersOfPlayersRequired : Int
    abstract val playerTimeOutSeconds : Int
    abstract val gameTimeOutSeconds : Int
}