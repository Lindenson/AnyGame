package wolper.domain

import kotlinx.serialization.Serializable
import wolper.formal.domain.AMove

@Serializable
data class Move (override val moveSize: Int = 1, override val move: List<Int>) : AMove<Int>()


