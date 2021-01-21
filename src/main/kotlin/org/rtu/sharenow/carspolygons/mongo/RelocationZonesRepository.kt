package org.rtu.sharenow.carspolygons.mongo;

import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.where
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler

@Repository
class RelocationZonesRepository(
    private val mongoTemplate: MongoTemplate
) {
    fun getPolygonById(polygonId: String): GeoJsonPolygon? {
        val relocationZone = mongoTemplate.findById(polygonId, RelocationZoneDocument::class.java)

        return relocationZone?.polygon
    }

    fun getPolygonsWithCar(carLocation: GeoJsonPoint): List<RelocationZoneDocument> {
        return mongoTemplate.find(
            Query.query(where(RelocationZoneDocument::polygon).intersects(carLocation)),
            RelocationZoneDocument::class.java
        )
    }
}
