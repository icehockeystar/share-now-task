package org.rtu.sharenow.carspolygons.config;

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KLogging
import org.geojson.GeoJsonObject
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

@Configuration
class MongoConfig {
    companion object : KLogging()

    @Bean
    fun collectionInitializer(mongoTemplate: MongoTemplate): CommandLineRunner =
        CommandLineRunner { createCollections(mongoTemplate) }

    fun createCollections(mongoTemplate: MongoTemplate) {
        importRelocationZones(mongoTemplate)

        if (!mongoTemplate.collectionExists(RelocationZoneDocument::class.java)) {
            mongoTemplate.createCollection(RelocationZoneDocument::class.java)

        }
    }

    fun importRelocationZones(mongoTemplate: MongoTemplate) {
        this::class.java.getResource("/data/polygons.json").openStream().use {
            val dumpedPolygons = jacksonObjectMapper().readValue<List<DumpedPolygon>>(it)
            logger.info { dumpedPolygons }
        }


        //mongoTemplate.insert(RelocationZoneDocument("test", now(), null))
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class DumpedPolygon(
    @JsonProperty("_id")
    val id: String,
    @JsonProperty("geometry")
    val geometry: GeoJsonObject
)
