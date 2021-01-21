package org.rtu.sharenow.carspolygons.mongo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.rtu.sharenow.carspolygons.config.MongoConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

@DataMongoTest
@Import(MongoConfig::class)
internal class RelocationZonesRepositoryTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    private lateinit var relocationZonesRepository: RelocationZonesRepository

    @Test
    fun `should get polygon by id`() {
        givenRelocationZonesRepository()

        val polygon = relocationZonesRepository.getPolygonById("58a58bf685979b5415f3a39a")

        assertThat(polygon).isEqualTo(
            GeoJsonPolygon(
                listOf(
                    Point(9.137248, 48.790411),
                    Point(9.137248, 48.790263),
                    Point(9.136950, 48.790263),
                    Point(9.137248, 48.790411)
                )
            )
        )
    }

    @Test
    fun `should not get polygon by id`() {
        givenRelocationZonesRepository()

        val polygon = relocationZonesRepository.getPolygonById("unknown")

        assertThat(polygon).isNull()
    }

    private fun givenRelocationZonesRepository() {
        relocationZonesRepository = RelocationZonesRepository(mongoTemplate)
    }
}