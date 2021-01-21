package org.rtu.sharenow.carspolygons.api

class CarsPolygonsProtocol {
    data class CarsInPolygonResponse(
        val carsInPolygon: List<CarInPolygon>
    ) {
        data class CarInPolygon(
            val vin: String,
            val polygonId: String
        )
    }
}