package wolper.formal.domain

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable


//S – staring point DTO
//D – disposition DTO
//M – move DTO
//T – evaluation DTO
//P - player

@Serializable
abstract class AGameHistory<D, M> {
    abstract val gameID: Long
    @Polymorphic abstract val dispositions: List<AGameDisposition<D>>
    @Polymorphic abstract val moves: List<AMove<M>>
}