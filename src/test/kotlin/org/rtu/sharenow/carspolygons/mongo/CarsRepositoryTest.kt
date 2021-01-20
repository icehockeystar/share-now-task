package org.rtu.sharenow.carspolygons.mongo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.rtu.sharenow.carspolygons.config.MongoConfig
import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.Location
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.CarDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

@DataMongoTest
@Import(MongoConfig::class)
internal class CarsRepositoryTest {

    companion object {
        val VIN = Vin("JH4DA9360PS004131")
        val LATITUDE = 38.889477
        val LONGITUDE = -77.050165
    }

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    private lateinit var carsRepository: CarsRepository

    private lateinit var cars: List<Car>

    @Test
    fun `should store new car with location`() {
        givenCars()
        givenCarsRepository()

        assertThat(mongoTemplate.findAll(CarDocument::class.java)).isEmpty()

        carsRepository.storeCarLocations(cars)

        assertThat(mongoTemplate.findAll(CarDocument::class.java))
            .hasSize(1)
            .first().isEqualTo(
                CarDocument(
                    VIN.value, GeoJsonPoint(LONGITUDE, LATITUDE)
                )
            )
    }

    @Test
    fun `should update location a an existing car`() {
        givenCars()
        givenCarsRepository()
        givenStoredCar()

        assertThat(mongoTemplate.findAll(CarDocument::class.java)).hasSize(1)

        val updatedCar = listOf(Car(vin = VIN, location = location(23.0002, 45.222)))
        carsRepository.storeCarLocations(updatedCar)

        assertThat(mongoTemplate.findAll(CarDocument::class.java)).hasSize(1)
            .first().isEqualTo(CarDocument(
                VIN.value, GeoJsonPoint(45.222, 23.0002)
            ))
    }

    private fun location(latitude: Double, longitude: Double): Location {
        return Location(latitude = BigDecimal.valueOf(latitude), longitude = BigDecimal.valueOf(longitude))
    }

    private fun givenStoredCar() {
        carsRepository.storeCarLocations(cars)
    }

    private fun givenCarsRepository() {
        this.carsRepository = CarsRepository(mongoTemplate)
    }

    private fun givenCars() {
        this.cars = listOf(
            Car(
                vin = Vin(VIN.value),
                location = location(LATITUDE, LONGITUDE)
            )
        )
    }
}
