#Carspolygons
## Run application locally


## Build docker image
In order to build a docker image run:
1. `./gradlew build`
2. `docker build -t app/carspolygons:v0.1.0`
## Run docker image
In order to run the application in docker execute:
`docker run app/carspolygons:v0.1.0 -p 8080:8080`
