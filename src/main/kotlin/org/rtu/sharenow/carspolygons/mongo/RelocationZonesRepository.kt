package org.rtu.sharenow.carspolygons.mongo;

import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.stereotype.Repository;

@Repository
class RelocationZonesRepository(
    private val mongoTemplate: MongoTemplate
) {
    fun getPolygonById(polygonId: String): GeoJsonPolygon? {
        val relocationZone = mongoTemplate.findById(polygonId, RelocationZoneDocument::class.java)

        return relocationZone?.polygon
    }
}
