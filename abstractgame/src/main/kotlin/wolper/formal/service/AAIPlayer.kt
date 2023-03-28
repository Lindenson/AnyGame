package wolper.formal.service

import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameStartingPoint


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

interface AAIPlayer<S, D, M, P> {
    fun   makeMove(disposition: List<AGameDisposition<D>>, startingPoint: AGameStartingPoint<S>, player: P) : M
}