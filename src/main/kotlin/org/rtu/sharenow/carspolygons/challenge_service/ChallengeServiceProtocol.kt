package org.rtu.sharenow.carspolygons.challenge_service

import java.math.BigDecimal

class ChallengeServiceProtocol {
    data class VehicleResponse(
        val vin: String,
        val positionLat: BigDecimal,
        val positionLong: BigDecimal
    )
}
