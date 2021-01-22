package org.rtu.sharenow.carspolygons.api

import io.swagger.annotations.Api
import org.geojson.GeoJsonObject
import org.geojson.LngLatAlt
import org.geojson.Polygon
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.CarInPolygon
import org.rtu.sharenow.carspolygons.api.CarsPolygonsProtocol.PolygonWithVinResponse
import org.rtu.sharenow.carspolygons.domain.model.errors.PolygonsNotFoundException
import org.rtu.sharenow.carspolygons.domain.model.values.Vin
import org.rtu.sharenow.carspolygons.domain.services.CarsService
import org.rtu.sharenow.carspolygons.mongo.MongoProtocol.RelocationZoneDocument
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api(value = "/", description = "Polygon operations.")
@RestController
class PolygonsController(
    private val carsService: CarsService
) {
    @GetMapping("/api/v1/polygons/car/{vin}")
    fun polygonsWithVin(@PathVariable("vin") vinParam: String): PolygonWithVinResponse {
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
            CarInPolygon(vin = vin.value, polygon = asPolygon(it))
        }
    }

    private fun asPolygon(relocationZoneDocument: RelocationZoneDocument): GeoJsonObject =
        Polygon(relocationZoneDocument.polygon.points.map { LngLatAlt(it.x, it.y) })
}
