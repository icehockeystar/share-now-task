package org.rtu.sharenow.carspolygons.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KLogging
import org.geojson.GeoJsonObject
import org.geojson.LngLatAlt
import org.geojson.Polygon
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.CarDocument
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType.GEO_2DSPHERE
import org.springframework.data.mongodb.core.index.GeospatialIndex
import java.time.Instant.now

@Configuration
class MongoConfig {
    companion object : KLogging()

    @Bean
    fun collectionInitializer(mongoTemplate: MongoTemplate): CommandLineRunner =
        CommandLineRunner { createCollections(mongoTemplate) }

    fun createCollections(mongoTemplate: MongoTemplate) {
        if (!mongoTemplate.collectionExists(RelocationZoneDocument::class.java)) {
            mongoTemplate.createCollection(RelocationZoneDocument::class.java)
            importRelocationZones(mongoTemplate)

            val geometryIndex = GeospatialIndex(DumpedRelocationZone::geometry.name).typed(GEO_2DSPHERE)
            mongoTemplate.indexOps(RelocationZoneDocument::class.java).ensureIndex(geometryIndex)
        }

        if (!mongoTemplate.collectionExists(CarDocument::class.java)) {
            mongoTemplate.createCollection(CarDocument::class.java)
            val geometryIndex = GeospatialIndex(CarDocument::location.name).typed(GEO_2DSPHERE)
            mongoTemplate.indexOps(CarDocument::class.java).ensureIndex(geometryIndex)
        }

        logger.info { "Collections have been successfully initialised." }
    }

    fun importRelocationZones(mongoTemplate: MongoTemplate) {
        this::class.java.getResource("/data/polygons.json").openStream().use { inputStream ->
            val dumpedPolygons = jacksonObjectMapper().readValue<List<DumpedRelocationZone>>(inputStream)

            dumpedPolygons.forEach {
                mongoTemplate.insert(asRelocationZoneDocument(it))
            }
        }
    }

    private fun asRelocationZoneDocument(it: DumpedRelocationZone): RelocationZoneDocument {
        val dumpedPolygon = it.geometry as Polygon
        val exteriorRing = dumpedPolygon.exteriorRing

        return RelocationZoneDocument(it.id, now(), asGeoJsonPolygon(exteriorRing))
    }

    private fun asGeoJsonPolygon(exteriorRing: List<LngLatAlt>): GeoJsonPolygon {
        val points = exteriorRing.map { Point(it.longitude, it.latitude) }
        return GeoJsonPolygon(points)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class DumpedRelocationZone(
    @JsonProperty("_id")
    val id: String,
    @JsonProperty("geometry")
    val geometry: GeoJsonObject
)
