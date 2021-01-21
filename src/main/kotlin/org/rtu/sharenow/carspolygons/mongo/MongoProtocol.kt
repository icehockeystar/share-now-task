package org.rtu.sharenow.carspolygons.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

class MongoProtocol {
    @Document("relocation_zones")
    data class RelocationZoneDocument(
        @Id
        val id: String,
        val createdAt: Instant,
        val polygon: GeoJsonPolygon?
    )

    @Document("cars")
    data class CarDocument(
        @Id
        val vin: String,
        val location: GeoJsonPoint
    )
}
