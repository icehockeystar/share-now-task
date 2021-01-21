package org.rtu.sharenow.carspolygons.api

import io.swagger.annotations.Api
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.CarInPolygon
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.PolygonWithVinResponse
import org.rtu.sharenow.carspolygons.domain.model.errors.PolygonsNotFoundException
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.domain.services.CarsService
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*

@Api(value = "/", description = "REST API for polygon operations.")
@RestController
class PolygonsController(
    private val carsService: CarsService
) {
    @GetMapping("/api/v1/polygons")
    fun polygonsWithVin(@RequestParam("vin") vinParam: String): PolygonWithVinResponse {
        val vin = Vin(vinParam)

        val polygons = carsService.getPolygonsWithVin(vin) ?: throw PolygonsNotFoundException()

        return PolygonWithVinResponse(asPolygonWithVin(vin, polygons))
    }

    @ExceptionHandler(PolygonsNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun polygonsNotFoundHandler() {
    }

    private fun asPolygonWithVin(vin: Vin, polygons: List<RelocationZoneDocument>): List<CarInPolygon> {
        return polygons.map {
            CarInPolygon(vin = vin.value, polygonId = it.id)
        }
    }
}
