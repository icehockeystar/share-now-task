package org.rtu.sharenow.carspolygons.api

import org.geojson.GeoJsonObject

class CarsPolygonsProtocol {
    data class CarsInPolygonResponse(
        val carsInPolygon: List<CarInPolygon>
    ) {
        data class CarInPolygon(
            val vin: String,
            val polygonId: String
        )
    }

    data class PolygonWithVinResponse(
        val polygonsWithVin: List<CarInPolygon>
    )

    data class CarInPolygon(
        val vin: String,
        val polygon: GeoJsonObject
    )
}
