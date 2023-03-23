package wolper.dao

import jakarta.validation.constraints.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import wolper.Gamer


@Service
@Qualifier("validService")
class GamerDAOValidation(@Qualifier("bareService") private var gamerService: GamerDAO) : GamerDAO {

    override fun getGamer(player: String): Mono<Gamer> {
        return try {
            gamerService.getGamer(player)
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }

    override fun createGamer(player : String): Mono<Gamer> {
        return try {
            gamerService.createGamer(player)
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }

    override fun deleteGamer(player: String): Mono<Long> {
        return try {
            gamerService.deleteGamer(player)
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }

    override fun getGamers(): Mono<List<String>> {
        return try {
            gamerService.getGamers()
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }
}