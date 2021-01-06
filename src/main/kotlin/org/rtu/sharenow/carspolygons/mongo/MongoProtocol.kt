package org.rtu.sharenow.carspolygons.mongo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.Instant.now

class MongoProtocol {
    @Document("relocation_zones")
    data class RelocationZoneDocument(
        val id: String,
        val createdAt: Instant,
        val polygon: GeoJsonPolygon?
    )
}
