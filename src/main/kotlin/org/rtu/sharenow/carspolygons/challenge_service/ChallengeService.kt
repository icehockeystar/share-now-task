package org.rtu.sharenow.carspolygons.challenge_service

import mu.KLogging
import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.Location
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.domain.services.CarsService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ChallengeService(
    private val challengeServiceApi: ChallengeServiceApi,
    private val carsService: CarsService
) {
    companion object : KLogging()

    @Scheduled(fixedDelay = 10_000)
    fun fetchCars() {
        logger.info { "Fetching car positions from challenge service..." }

        val cars = asCars(challengeServiceApi.fetchCars(carLocation = CarLocation.STUTTGART))
        carsService.storeCarLocations(cars)
    }

    private fun asCars(vehicleResponses: List<ChallengeServiceProtocol.VehicleResponse>?): List<Car> {
        return vehicleResponses?.map {
            Car(
                vin = Vin(it.vin),
                location = Location(latitude = it.position.latitude, longitude = it.position.longitude)
            )
        } ?: emptyList()
    }
}

enum class CarLocation {
    STUTTGART
}
