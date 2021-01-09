package org.rtu.sharenow.carspolygons.mongo

import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

class MongoProtocol {
    @Document("relocation_zones")
    data class RelocationZoneDocument(
        val id: String,
        val createdAt: Instant,
        val polygon: GeoJsonPolygon?
    )
}
