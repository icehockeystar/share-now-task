package org.rtu.sharenow.carspolygons.mongo

import mu.KLogging
import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.Location
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.CarDocument
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class CarsRepository(
    private val mongoTemplate: MongoTemplate
) {
    companion object : KLogging()

    fun getCarsInPolygon(polygon: GeoJsonPolygon): List<CarDocument> {
        return mongoTemplate.find(query(where(CarDocument::location.name).within(polygon)), CarDocument::class.java)
    }

    fun storeCarLocations(cars: List<Car>) {
        cars.forEach {
            storeCarLocation(it.vin, it.location)
        }
    }

    private fun storeCarLocation(vin: Vin, newLocation: Location) {
        val car = mongoTemplate.findAndModify(
            query(where(Car::vin.name).isEqualTo(vin.value)),
            update(Car::location.name, GeoJsonPoint(newLocation.longitude.toDouble(), newLocation.latitude.toDouble())),
            FindAndModifyOptions.options().upsert(true), CarDocument::class.java
        )

        if (car == null) {
            logger.info { "Successfully added new car with vin: $vin, location: $newLocation" }
            return
        }

        if (newLocation.longitude.toDouble() != car.location.x || newLocation.latitude.toDouble() != car.location.y) {
            logger.info { "Successfully updated car (vin: $vin) location: ${car.location} -> $newLocation" }
        }
    }
}
