package wolper

import java.time.LocalDateTime

data class Gamer(
    val id: Long?,
    val name: String?,
    val token: String?,
    val busy: Boolean,
    var created: LocalDateTime?,
)