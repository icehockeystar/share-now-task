package org.rtu.sharenow.carspolygons.challenge_service

import java.math.BigDecimal

class ChallengeServiceProtocol {
    data class VehicleResponse(
        val vin: String,
        val position: VehiclePosition
    ) {
        data class VehiclePosition(
            val latitude: BigDecimal,
            val longitude: BigDecimal
        )
    }
}
