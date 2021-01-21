package org.rtu.sharenow.carspolygons

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CarsPolygonsApplication

fun main(args: Array<String>) {
    runApplication<CarsPolygonsApplication>(*args)
}
