package wolper.formal.domain


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

abstract class AGameState<S, D, T> {
    abstract var gameID: Long
    abstract var startingPoint: AGameStartingPoint<S>
    abstract var disposition: AGameDisposition<D>
    abstract var currentEvaluation: T?
}

