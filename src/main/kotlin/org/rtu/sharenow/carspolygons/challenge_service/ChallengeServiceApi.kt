package org.rtu.sharenow.carspolygons.challenge_service

import mu.KLogging
import org.rtu.sharenow.carspolygons.challenge_service.ChallengeServiceProtocol.VehicleResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class ChallengeServiceApi(
    private val restTemplate: RestTemplateBuilder,
    @Value("\${challenge-service.base-url}") private val baseUrl: String
) {
    companion object : KLogging()

    fun fetchCars(carLocation: CarLocation): List<VehicleResponse>? {
        val location = asString(carLocation)
        val url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/vehicles/$location").build().toUri()

        return try {
            restTemplate.build().exchange(url, GET, null, object : ParameterizedTypeReference<List<VehicleResponse>>() {})
                .body
        } catch (e: Exception) {
            logger.error(e) { "Couldn't fetch vehicles for location $carLocation. Reason: ${e.message}" }
            null
        }
    }

    private fun asString(carLocation: CarLocation): String {
        return when (carLocation) {
            CarLocation.STUTTGART -> "Stuttgart"
            else -> error("Unmapped car location.")
        }
    }
}
