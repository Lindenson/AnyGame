package wolper.formal.helper

import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AMove
import wolper.formal.dto.AGameDispositionDTO
import wolper.formal.dto.AGameMoveDTO


class AMapper {
    companion object {

        inline fun <reified U : AMove<*>> toMoveDTO(id: Long?, sm: PoliComplexJson, m: U): AGameMoveDTO {
            return AGameMoveDTO(id, sm to m)
        }

        inline fun <reified T : AGameDisposition<*>> toDespositionDTO(id: Long?, sm: PoliComplexJson, m: T): AGameDispositionDTO {
            return AGameDispositionDTO(id, sm to m)
        }


        inline fun <reified U : AMove<*>> fromMoveDTO(sm: PoliComplexJson, m: AGameMoveDTO): U {
            return sm from m.move!!
        }

        inline fun <reified T : AGameDisposition<*>> fromDespositionDTO(sm: PoliComplexJson, m: AGameDispositionDTO): T {
            return sm from m.disposition!!
        }
    }

}
