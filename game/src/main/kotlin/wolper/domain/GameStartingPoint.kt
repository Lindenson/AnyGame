package wolper.domain

import kotlinx.serialization.Serializable
import wolper.formal.domain.AGameStartingPoint

@Serializable
data class GameStartingPoint (override var pointSize: Int = 2,
                              override var startingPoint: List<Int>) : AGameStartingPoint<Int>()
