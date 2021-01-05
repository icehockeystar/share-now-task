package org.rtu.sharenow.carspolygons.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Api(value = "/", description = "REST API for cars operations.")
@RestController
class CarsController {
    @GetMapping("/api/v1/cars")
    @ApiOperation("Get cars in polygon with given id.")
    @ResponseStatus(OK)
    fun carsInPolygon(@RequestParam("polygonId") polygonId: String) {
    }
}
