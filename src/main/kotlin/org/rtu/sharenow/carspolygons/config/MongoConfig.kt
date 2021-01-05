package org.rtu.sharenow.carspolygons.config;

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoConfig {
    @Bean
    fun collectionInitializer(mongoTemplate: MongoTemplate): CommandLineRunner =
        CommandLineRunner { createCollections(mongoTemplate) }

    private fun createCollections(mongoTemplate: MongoTemplate) {

    }


}
