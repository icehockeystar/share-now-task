package org.rtu.sharenow.carspolygons.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.CarsInPolygonResponse
import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.values.PolygonId
import org.rtu.sharenow.carspolygons.domain.services.CarsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Api(value = "/", description = "REST API for cars operations.")
@RestController
class CarsController(
    private val carsService: CarsService
) {
    @GetMapping("/api/v1/cars")
    @ApiOperation("Get cars in polygon with given id.")
    fun carsInPolygon(@RequestParam("polygonId") id: String): CarsInPolygonResponse {
        val polygonId = PolygonId(UUID.fromString(id))

        val cars = carsService.getCarsInPolygon(polygonId)

        return CarsInPolygonResponse(asCarsInPolygon(cars, polygonId))
    }

    private fun asCarsInPolygon(cars: List<Car>, polygonId: PolygonId): List<CarsInPolygonResponse.CarInPolygon> {
        return cars.map { CarsInPolygonResponse.CarInPolygon(vin = it.vin.value, polygonId = polygonId.value) }
    }
}
