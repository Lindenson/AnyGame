package wolper.formal.domain

import java.time.LocalDateTime

class AGameSaga(
    var gameID: Long?,
    var created: LocalDateTime?,
    var inviter: Long?,
    var waitlist: LongArray?,
    var contributed: LongArray?, // starting points
    var accepted: LocalDateTime?,
    var nextmove: Long?,
    var lastmove: LocalDateTime?,
    var canceled: Boolean,
    var finished: LocalDateTime?,
    var token: String?,
)
