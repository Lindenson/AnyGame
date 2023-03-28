package wolper.domain

import kotlinx.serialization.Serializable
import wolper.formal.domain.AGameState
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameStartingPoint

@Serializable
data class GameState (
    override var gameID: Long,
    override var startingPoint: AGameStartingPoint<Int>,
    override var disposition: AGameDisposition<Int>,
    override var currentEvaluation: Int?
) : AGameState<Int, Int, Int>()
