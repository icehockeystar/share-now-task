package org.rtu.sharenow.carspolygons.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.CarsInPolygonResponse
import org.rtu.sharenow.carspolygons.domain.model.entities.Car
import org.rtu.sharenow.carspolygons.domain.model.errors.CarsNotFoundException
import org.rtu.sharenow.carspolygons.domain.model.values.PolygonId
import org.rtu.sharenow.carspolygons.domain.services.CarsService
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api(value = "/", description = "Cars operations.")
@RestController
class CarsController(
    private val carsService: CarsService
) {
    @GetMapping("/api/v1/cars")
    @ApiOperation("Get cars in polygon with given id.")
    fun carsInPolygon(@RequestParam("polygonId") id: String): CarsInPolygonResponse {
        val polygonId = PolygonId(id)

        val cars = carsService.getCarsInPolygon(polygonId) ?: throw CarsNotFoundException()

        return CarsInPolygonResponse(asCarsInPolygon(cars, polygonId))
    }

    private fun asCarsInPolygon(cars: List<Car>, polygonId: PolygonId): List<CarsInPolygonResponse.CarInPolygon> {
        return cars.map { CarsInPolygonResponse.CarInPolygon(vin = it.vin.value, polygonId = polygonId.value) }
    }

    @ExceptionHandler(CarsNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun carsNotFoundHandler() {
    }
}
