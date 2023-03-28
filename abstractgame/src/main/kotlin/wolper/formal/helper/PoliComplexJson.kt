package wolper.formal.helper

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.serializer
import wolper.formal.domain.AGameHistory
import wolper.formal.domain.AGameState
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameSaga
import wolper.formal.domain.AGameStartingPoint
import wolper.formal.domain.AMove
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass



class PoliComplexJson {

    lateinit var module: SerializersModule

    companion object {
        private val aligibleClasses: List<KClass<out Any>> = listOf(
            AGameDisposition::class,
            AGameSaga::class,
            AGameStartingPoint::class,
            AMove::class,
            AGameHistory::class,
            AGameState::class
        )


        fun <E : T, T : Any> getFather(son: KClass<E>): KClass<T> {
            val classOfFather = son.supertypes[0]?.classifier as KClass<T>
            val isOkKlass = classOfFather?.let { aligibleClasses.contains<Any>(it) } ?: false
            if (!isOkKlass) throw IllegalArgumentException()
            return classOfFather
        }


        @OptIn(InternalSerializationApi::class)
        fun <T : Any, E : T> newSerializerFor(son: KClass<E>): PoliComplexJson {
            val poliJson = PoliComplexJson()
            val classOfFather = getFather<E, T>(son)
            poliJson.module = SerializersModule { polymorphic(classOfFather, son, son.serializer()) }
            return poliJson
        }
    }


    @OptIn(InternalSerializationApi::class)
    fun <T : Any, E : T> andFor(son: KClass<E>): PoliComplexJson {
        val classOfFather = getFather<E, T>(son)
        module += SerializersModule { polymorphic(classOfFather, son, son.serializer()) }
        return this
    }


    inline infix fun <reified T> to (client: T): String {
        return Json { serializersModule = module }.encodeToString(client)
    }

    inline infix fun <reified T> from (client: String): T {
        return Json { ignoreUnknownKeys = true; serializersModule = module }.decodeFromString(client)
    }
}

