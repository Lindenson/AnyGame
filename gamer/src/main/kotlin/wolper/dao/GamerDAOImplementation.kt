package wolper.dao



import jakarta.validation.constraints.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono
import wolper.Gamer
import java.util.UUID


@Service
@Qualifier("bareService")
@Validated
class GamerDAOImplementation(
    private val r2temlate: R2dbcEntityTemplate,
) : GamerDAO {


    override fun getGamer(@NotNull player : String) : Mono<Gamer> {
        return r2temlate.selectOne<Gamer>(
                query(where("name").`is`(player)),
                Gamer::class.java
            )
    }

    override fun createGamer(@NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player : String) : Mono<Gamer> {
        val newGamer = Gamer(null, player, UUID.randomUUID().toString(), false, null)
        return r2temlate.insert(newGamer)
    }

    override fun deleteGamer(@NotNull player : String) : Mono<Long> {
        return r2temlate.delete(
            query(where("name").`is`(player)),
            Gamer::class.java)
    }

    override fun getFreeGamers(): Mono<List<String>> {
        return r2temlate.databaseClient.sql("""
                SELECT * from gamer A WHERE not busy 
        """.trimIndent()).fetch().all()
            .map { it["name"] as String }
            .collectList()
        }
}