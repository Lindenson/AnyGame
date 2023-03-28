package wolper.formal.service

import wolper.formal.domain.AGameStartingPoint
import wolper.formal.domain.AMove


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

interface AGameService<S, D, M, P> {
    fun createGame(player : P) : Long // gameID
    fun invitePlayer(id : Long, player : P)
    fun joinGame(id : Long, player : P)
    fun refuseGame(id : Long, player : P)
    fun setStartingPoint(id : Long, player : P, start : AGameStartingPoint<S>)
    fun tableIsReady(id : Long) : Boolean
    fun cancelGame(id : Long, player : P)
    fun getDispositionForPlayer(id : Long, player : P) : D
    fun makeMove(id : Long, move : M, player: P) : D
    fun isMyTurn(id : Long, player: P) : Boolean
    fun whosTurn(id : Long, ) : P
    fun getEvaluation(id : Long, ) : D
    fun getHistoricMoves(id : Long, player: P) : List<AMove<M>>
}



