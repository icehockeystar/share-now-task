package org.rtu.sharenow.carspolygons.api

import java.util.*

class CarsPolygonsProtocol {
    data class CarsInPolygonResponse(
        val carsInPolygon: List<CarInPolygon>
    ) {
        data class CarInPolygon(
            val vin: String,
            val polygonId: UUID
        )
    }
}