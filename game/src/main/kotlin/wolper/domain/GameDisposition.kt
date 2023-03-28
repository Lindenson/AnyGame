package wolper.domain

import kotlinx.serialization.Serializable
import wolper.formal.domain.AGameDisposition


@Serializable
data class GameDisposition (
    override var dispositionSize: Int = 2,
    override var disposition: List<Int>) : AGameDisposition<Int>()
