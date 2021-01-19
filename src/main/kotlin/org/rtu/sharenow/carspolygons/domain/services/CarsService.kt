package org.rtu.sharenow.carspolygons.domain.services

import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.PolygonId
import org.rtu.sharenow.carspolygons.mongo.CarsRepository
import org.springframework.stereotype.Service

@Service
class CarsService(
    private val carsRepository: CarsRepository
) {
    fun getCarsInPolygon(polygonId: PolygonId) {
        carsRepository.getCarsInPolygon(polygonId)
    }

    fun storeCarLocations(cars: List<Car>) {
        carsRepository.storeCarLocations(cars)
    }
}
