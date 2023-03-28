package wolper.domain


import kotlinx.serialization.Serializable
import wolper.formal.domain.AGameHistory
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AMove

@Serializable
data class GameHistory (override val gameID: Long,
                        override val dispositions: List<out AGameDisposition<Int>>,
                        override val moves: List<out AMove<Int>>) : AGameHistory<Int, Int>()



