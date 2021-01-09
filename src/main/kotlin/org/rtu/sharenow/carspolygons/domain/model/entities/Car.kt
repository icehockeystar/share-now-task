package org.rtu.sharenow.carspolygons.domain.model.entities

import org.rtu.sharenow.carspolygons.domain.model.values.Location
import org.rtu.sharenow.carspolygons.domain.model.values.Vin

data class Car(val vin: Vin, val location: Location)
