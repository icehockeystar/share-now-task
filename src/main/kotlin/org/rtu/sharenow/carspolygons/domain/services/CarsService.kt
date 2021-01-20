package org.rtu.sharenow.carspolygons.domain.services

import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.Location
import org.rtu.sharenow.carspolygons.domain.model.values.PolygonId
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.mongo.CarsRepository
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CarsService(
    private val carsRepository: CarsRepository
) {
    fun getCarsInPolygon(polygonId: PolygonId): List<Car> {
        val carDocuments = carsRepository.getCarsInPolygon(polygonId)
        return carDocuments.map(this::asCar)
    }

    private fun asCar(it: MongoProtocol.CarDocument) = Car(
        vin = Vin(it.vin),
        location = Location(BigDecimal.valueOf(it.location.y), BigDecimal.valueOf(it.location.x))
    )

    fun storeCarLocations(cars: List<Car>) {
        carsRepository.storeCarLocations(cars)
    }
}
