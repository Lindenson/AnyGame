package wolper.json

import org.springframework.stereotype.Component
import wolper.domain.*
import wolper.formal.helper.PoliComplexJson

@Component
class JsonHelpers {
    val desposition = PoliComplexJson.newSerializerFor(GameDisposition::class)

    val state = PoliComplexJson.newSerializerFor(GameState::class)
        .andFor(GameDisposition::class).andFor(GameStartingPoint::class)

    val move = PoliComplexJson.newSerializerFor(Move::class)

    val history = PoliComplexJson.newSerializerFor(GameHistory::class)
        .andFor(GameDisposition::class).andFor(Move::class)
}